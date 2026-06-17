package com.market.mapper;

import com.market.entity.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    OrderItem selectById(Long id);
    List<OrderItem> selectByOrderId(Long orderId);
    List<OrderItem> selectAll();
    int insert(OrderItem item);
    int insertBatch(@Param("list") List<OrderItem> list);
    int update(OrderItem item);
    int deleteById(Long id);
    int deleteByOrderId(Long orderId);
}
