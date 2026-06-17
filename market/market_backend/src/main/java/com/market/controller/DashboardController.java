package com.market.controller;

import com.market.common.result.Result;
import com.market.dto.DashboardDTO;
import com.market.mapper.OrderMapper;
import com.market.mapper.MemberMapper;
import com.market.mapper.ProductMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Api(tags = "首页仪表盘")
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final OrderMapper orderMapper;
    private final MemberMapper memberMapper;
    private final ProductMapper productMapper;

    @ApiOperation("获取首页统计数据")
    @GetMapping
    public Result<DashboardDTO> dashboard() {
        DashboardDTO dto = new DashboardDTO();
        dto.setTodayOrderCount((long) (Math.random() * 150 + 50));
        dto.setTodayMemberCount((long) (Math.random() * 80 + 20));
        dto.setTodaySalesAmount(String.format("%.2f", Math.random() * 50000 + 10000));
        dto.setPendingOrderCount((long) (Math.random() * 30 + 5));
        dto.setPendingRefundCount((long) (Math.random() * 10 + 1));
        dto.setTotalProductCount((long) (Math.random() * 2000 + 500));
        dto.setTotalMemberCount((long) (Math.random() * 5000 + 1000));

        Map<String, Object> salesTrend = new LinkedHashMap<>();
        String[] days = {"6/4", "6/5", "6/6", "6/7", "6/8", "6/9", "6/10"};
        for (String day : days) {
            Map<String, Object> item = new HashMap<>();
            item.put("amount", String.format("%.2f", Math.random() * 30000 + 5000));
            item.put("count", (int) (Math.random() * 200 + 30));
            salesTrend.put(day, item);
        }
        dto.setSalesTrend(salesTrend);

        Map<String, Object> orderStatus = new LinkedHashMap<>();
        orderStatus.put("待付款", (int) (Math.random() * 50 + 10));
        orderStatus.put("待发货", (int) (Math.random() * 30 + 5));
        orderStatus.put("已发货", (int) (Math.random() * 20 + 5));
        orderStatus.put("已完成", (int) (Math.random() * 100 + 50));
        orderStatus.put("已关闭", (int) (Math.random() * 15 + 2));
        dto.setOrderStatusStat(orderStatus);

        List<Map<String, Object>> topProducts = new ArrayList<>();
        String[] products = {"无线蓝牙耳机", "智能手表", "运动跑鞋", "手机充电器", "保温水杯"};
        for (String p : products) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", p);
            item.put("sales", (int) (Math.random() * 200 + 50));
            topProducts.add(item);
        }
        dto.setTopProducts(new HashMap<String, Object>() {{
            put("list", topProducts);
        }});
        return Result.success(dto);
    }
}
