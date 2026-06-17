package com.market.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Refund {
    private Long id;
    private String refundNo;
    private Long orderId;
    private String orderNo;
    private Long memberId;
    private String memberName;
    private BigDecimal refundAmount;
    private String reason;
    private String evidence;
    private Integer status;
    private String handleNote;
    private Long handlerId;
    private LocalDateTime handleTime;
    private LocalDateTime applyTime;
    private LocalDateTime finishTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}