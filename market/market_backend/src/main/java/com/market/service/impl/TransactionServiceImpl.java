package com.market.service.impl;

import com.market.entity.Transaction;
import com.market.mapper.TransactionMapper;
import com.market.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper mapper;
    @Override public Transaction getById(Long id) { return mapper.selectById(id); }
    @Override public List<Transaction> list(Transaction q) { return mapper.selectAll(); }
    @Override public int save(Transaction t) { return mapper.insert(t); }
    @Override public int update(Transaction t) { return mapper.update(t); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return 0; }
    @Override public List<Transaction> selectList(String transactionNo, Integer transactionType, Integer status, String startTime, String endTime) { return mapper.selectList(transactionNo, transactionType, status, startTime, endTime); }
    @Override public List<Transaction> selectAll() { return mapper.selectAll(); }
}
