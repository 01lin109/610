package com.market.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Notice {
    private Long id;
    private String title;
    private String content;
    private Integer noticeType;
    private Integer status;
    private Integer isTop;
    private Long publisherId;
    private String publisherName;
    private LocalDateTime publishTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}