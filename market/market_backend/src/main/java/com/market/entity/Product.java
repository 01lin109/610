package com.market.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private String productName;
    private String productCode;
    private Long categoryId;
    private String categoryName;
    private String brand;
    private BigDecimal price;
    private BigDecimal costPrice;
    private Integer stock;
    private Integer sales;
    private String unit;
    private String mainImage;
    private String images;
    private String description;
    private String specs;
    private Integer sort;
    private Integer status;
    private Integer isNew;
    private Integer isHot;
    private Integer isRecommend;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}
