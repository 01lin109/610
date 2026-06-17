package com.market.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Advertisement {
    private Long id;
    private String adName;
    private Integer adType;
    private Integer adPosition;
    private String imageUrl;
    private String linkUrl;
    private Integer sort;
    private Integer status;
    private Integer clickCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}