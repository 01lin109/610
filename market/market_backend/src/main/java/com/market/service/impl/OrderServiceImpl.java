package com.market.service.impl;

import com.market.entity.Order;
import com.market.entity.OrderItem;
import com.market.mapper.OrderMapper;
import com.market.mapper.OrderItemMapper;
import com.market.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public Order getById(Long id) { return orderMapper.selectById(id); }
    @Override
    public List<Order> list(Order query) { return orderMapper.selectAll(); }
    @Override
    public int save(Order order) { return orderMapper.insert(order); }
    @Override
    public int update(Order order) { return orderMapper.update(order); }
    @Override
    public int deleteById(Long id) { return orderMapper.deleteById(id); }
    @Override
    public int deleteBatch(List<Long> ids) { return orderMapper.deleteBatch(ids); }
    @Override
    public List<Order> selectList(String orderNo, Integer status, String startTime, String endTime) { return orderMapper.selectList(orderNo, status, startTime, endTime); }
    @Override
    public List<Order> selectAll() { return orderMapper.selectAll(); }
    @Override
    public int deliverOrder(Long id, String deliveryNo, String deliveryCompany) { return orderMapper.updateDelivery(id, deliveryNo, deliveryCompany, LocalDateTime.now(), 2); }
    @Override
    public int closeOrder(Long id) { return orderMapper.updateStatus(id, 5); }
    @Override
    public int countByStatus(Integer status) { return orderMapper.countByStatus(status); }
    @Override
    public List<OrderItem> getOrderItems(Long orderId) { return orderItemMapper.selectByOrderId(orderId); }
}
