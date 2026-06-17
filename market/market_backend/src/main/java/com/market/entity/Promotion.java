package com.market.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Promotion {
    private Long id;
    private String promotionName;
    private Integer promotionType;
    private String rule;
    private BigDecimal discountValue;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer status;
    private String productIds;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
