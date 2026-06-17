package com.market.dto;

import lombok.Data;
import java.util.List;

@Data
public class PageQueryDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String keyword;
    private String startTime;
    private String endTime;
    private Integer status;
    private Long categoryId;
    private String sortField;
    private String sortOrder;
}