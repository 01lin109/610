package com.market.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private Long id;
    private String transactionNo;
    private Long orderId;
    private String orderNo;
    private Long memberId;
    private String memberName;
    private BigDecimal amount;
    private Integer transactionType;
    private Integer payType;
    private String payNo;
    private Integer status;
    private String remark;
    private LocalDateTime transactionTime;
    private LocalDateTime createTime;
}