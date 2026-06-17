package com.market.mapper;

import com.market.entity.Transaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionMapper {
    Transaction selectById(Long id);
    List<Transaction> selectList(@Param("transactionNo") String transactionNo, @Param("transactionType") Integer transactionType,
                                 @Param("status") Integer status, @Param("startTime") String startTime, @Param("endTime") String endTime);
    List<Transaction> selectAll();
    int insert(Transaction transaction);
    int update(Transaction transaction);
    int deleteById(Long id);
}
