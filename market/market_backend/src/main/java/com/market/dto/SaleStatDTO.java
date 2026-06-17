package com.market.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SaleStatDTO {
    private LocalDateTime statDate;
    private BigDecimal totalAmount;
    private Integer orderCount;
    private Integer memberCount;
    private BigDecimal averageAmount;
    private BigDecimal refundAmount;
    private Integer refundCount;
}