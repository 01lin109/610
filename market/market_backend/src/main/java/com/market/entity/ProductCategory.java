package com.market.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProductCategory {
    private Long id;
    private String categoryName;
    private Long parentId;
    private Integer level;
    private Integer sort;
    private String icon;
    private Integer status;
    private Integer isShow;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}
