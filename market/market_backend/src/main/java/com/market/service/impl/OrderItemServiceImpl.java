package com.market.service.impl;

import com.market.entity.OrderItem;
import com.market.mapper.OrderItemMapper;
import com.market.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemMapper mapper;
    @Override public OrderItem getById(Long id) { return mapper.selectById(id); }
    @Override public List<OrderItem> list(OrderItem q) { return mapper.selectAll(); }
    @Override public int save(OrderItem item) { return mapper.insert(item); }
    @Override public int update(OrderItem item) { return mapper.update(item); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return 0; }
    @Override public List<OrderItem> selectByOrderId(Long orderId) { return mapper.selectByOrderId(orderId); }
    @Override public int insertBatch(List<OrderItem> items) { return mapper.insertBatch(items); }
    @Override public int deleteByOrderId(Long orderId) { return mapper.deleteByOrderId(orderId); }
}
