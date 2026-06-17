package com.market.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private String orderNo;
    private Long memberId;
    private String memberName;
    private BigDecimal totalAmount;
    private BigDecimal freight;
    private BigDecimal discountAmount;
    private BigDecimal payAmount;
    private Integer payType;
    private LocalDateTime payTime;
    private String deliveryNo;
    private String deliveryCompany;
    private LocalDateTime deliveryTime;
    private Integer status;
//    private String deliveryCompany;
//    private LocalDateTime deliveryTime;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}
