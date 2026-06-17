package com.market.service;

import com.market.entity.OrderItem;
import java.util.List;

public interface OrderItemService extends BaseService<OrderItem, Long> {
    List<OrderItem> selectByOrderId(Long orderId);
    int insertBatch(List<OrderItem> items);
    int deleteByOrderId(Long orderId);
}
