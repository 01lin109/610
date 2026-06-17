package com.market.service;

import com.market.entity.Refund;
import java.util.List;

public interface RefundService extends BaseService<Refund, Long> {
    List<Refund> selectList(String refundNo, Integer status, String startTime, String endTime);
    List<Refund> selectAll();
    int updateStatus(Long id, Integer status);
    int handleRefund(Long id, Integer status, String handleNote, Long handlerId);
    int countByStatus(Integer status);
}
