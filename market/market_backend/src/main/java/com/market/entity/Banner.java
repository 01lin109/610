package com.market.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Banner {
    private Long id;
    private String bannerName;
    private String imageUrl;
    private String linkUrl;
    private Integer sort;
    private Integer status;
    private String remark;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}