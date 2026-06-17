package com.market.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MemberLevel {
    private Long id;
    private String levelName;
    private Integer minPoints;
    private Integer maxPoints;
    private BigDecimal discount;
    private String remark;
    private LocalDateTime createTime;
}
