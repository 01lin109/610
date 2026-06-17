package com.market.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.market.common.result.PageResult;
import com.market.common.result.Result;
import com.market.entity.*;
import com.market.mapper.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "订单管理")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @ApiOperation("订单列表(分页)")
    @GetMapping("/list")
    public Result<PageResult<List<Order>>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> list = orderMapper.selectList(orderNo, status, startTime, endTime);
        PageInfo<Order> info = new PageInfo<>(list);
        return Result.success(PageResult.of(info.getTotal(), list));
    }

    @ApiOperation("订单详情")
    @GetMapping("/{id}")
    public Result<Order> detail(@PathVariable Long id) {
        Order order = orderMapper.selectById(id);
        if (order != null) {
            List<OrderItem> items = orderItemMapper.selectByOrderId(id);
            order.setRemark(order.getRemark());
        }
        return Result.success(order);
    }

    @ApiOperation("订单发货")
    @PutMapping("/deliver/{id}")
    public Result<Void> deliver(@PathVariable Long id,
                                 @RequestParam String deliveryNo,
                                 @RequestParam String deliveryCompany) {
        orderMapper.updateDelivery(id, deliveryNo, deliveryCompany, LocalDateTime.now(), 2);
        return Result.success();
    }

    @ApiOperation("关闭订单")
    @PutMapping("/close/{id}")
    public Result<Void> close(@PathVariable Long id) {
        orderMapper.updateStatus(id, 5);
        return Result.success();
    }

    @ApiOperation("删除订单")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        orderMapper.deleteById(id);
        return Result.success();
    }

    @ApiOperation("更新订单状态")
    @PutMapping("/{id}/status/{status}")
    public Result<Void> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        if (status == 2) {
            // 发货需要额外信息，由发货接口处理
            return Result.error("请使用发货接口");
        }
        orderMapper.updateStatus(id, status);
        return Result.success();
    }

    @ApiOperation("获取订单明细")
    @GetMapping("/items/{orderId}")
    public Result<List<OrderItem>> items(@PathVariable Long orderId) {
        return Result.success(orderItemMapper.selectByOrderId(orderId));
    }
}