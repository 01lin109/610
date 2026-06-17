package com.market.service;

import com.market.entity.Transaction;
import java.util.List;

public interface TransactionService extends BaseService<Transaction, Long> {
    List<Transaction> selectList(String transactionNo, Integer transactionType, Integer status, String startTime, String endTime);
    List<Transaction> selectAll();
}
