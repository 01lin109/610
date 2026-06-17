package com.market.controller;

import com.market.common.result.Result;
import com.market.dto.DashboardDTO;
import com.market.dto.SaleStatDTO;
import com.market.entity.Order;
import com.market.entity.Product;
import com.market.service.OrderService;
import com.market.service.ProductService;
import com.market.service.MemberService;
import com.market.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Api(tags = "统计报表")
@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final OrderService orderService;
    private final ProductService productService;
    private final MemberService memberService;
    private final TransactionService transactionService;

    @ApiOperation("销售统计概览")
    @GetMapping("/sales/overview")
    public Result<?> salesOverview() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalProducts", productService.selectTotalCount());
        data.put("totalMembers", memberService.selectTotalCount());
        data.put("pendingOrders", orderService.countByStatus(0));
        data.put("deliveredOrders", orderService.countByStatus(2));
        data.put("todayOrders", orderService.countByStatus(-1));
        return Result.success(data);
    }

    @ApiOperation("订单统计")
    @GetMapping("/orders")
    public Result<?> orderStatistics(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        List<Order> orders = orderService.selectAll();
        Map<String, Object> stats = new HashMap<>();
        Map<Integer, Long> statusCount = new HashMap<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Order o : orders) {
            statusCount.merge(o.getStatus(), 1L, Long::sum);
            if (o.getPayAmount() != null) totalAmount = totalAmount.add(o.getPayAmount());
        }
        stats.put("statusCount", statusCount);
        stats.put("totalOrders", orders.size());
        stats.put("totalAmount", totalAmount);
        return Result.success(stats);
    }

    @ApiOperation("商品销量排行")
    @GetMapping("/products/rank")
    public Result<?> productRank(@RequestParam(defaultValue = "10") Integer limit) {
        return Result.success(productService.selectTopSales(limit));
    }

    @ApiOperation("每日销售趋势")
    @GetMapping("/sales/daily")
    public Result<?> dailySales(@RequestParam(defaultValue = "7") Integer days) {
        List<Order> orders = orderService.selectAll();
        Map<String, BigDecimal> dailyMap = new LinkedHashMap<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = days - 1; i >= 0; i--) {
            dailyMap.put(LocalDate.now().minusDays(i).format(fmt), BigDecimal.ZERO);
        }
        for (Order o : orders) {
            if (o.getCreateTime() != null) {
                String day = o.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (dailyMap.containsKey(day) && o.getPayAmount() != null) {
                    dailyMap.merge(day, o.getPayAmount(), BigDecimal::add);
                }
            }
        }
        return Result.success(dailyMap);
    }
}