package com.market.service;

import com.market.entity.Order;
import com.market.entity.OrderItem;
import java.util.List;

public interface OrderService extends BaseService<Order, Long> {
    List<Order> selectList(String orderNo, Integer status, String startTime, String endTime);
    List<Order> selectAll();
    int deliverOrder(Long id, String deliveryNo, String deliveryCompany);
    int closeOrder(Long id);
    int countByStatus(Integer status);
    List<OrderItem> getOrderItems(Long orderId);
}
