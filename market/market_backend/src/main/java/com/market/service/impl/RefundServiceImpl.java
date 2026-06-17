package com.market.service.impl;

import com.market.entity.Refund;
import com.market.mapper.RefundMapper;
import com.market.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {
    private final RefundMapper mapper;
    @Override public Refund getById(Long id) { return mapper.selectById(id); }
    @Override public List<Refund> list(Refund q) { return mapper.selectAll(); }
    @Override public int save(Refund r) { return mapper.insert(r); }
    @Override public int update(Refund r) { return mapper.update(r); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return 0; }
    @Override public List<Refund> selectList(String refundNo, Integer status, String startTime, String endTime) { return mapper.selectList(refundNo, status, startTime, endTime); }
    @Override public List<Refund> selectAll() { return mapper.selectAll(); }
    @Override public int updateStatus(Long id, Integer status) { return mapper.updateStatus(id, status); }
    @Override public int handleRefund(Long id, Integer status, String handleNote, Long handlerId) { return mapper.handleRefund(id, status, handleNote, handlerId); }
    @Override public int countByStatus(Integer status) { return mapper.countByStatus(status); }
}
