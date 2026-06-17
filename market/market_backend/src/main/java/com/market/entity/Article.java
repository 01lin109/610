package com.market.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Article {
    private Long id;
    private String title;
    private Long categoryId;
    private String categoryName;
    private String summary;
    private String content;
    private String coverImage;
    private String author;
    private Integer isTop;
    private Integer status;
    private Integer viewCount;
    private LocalDateTime publishTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}