package com.market.common.result;

import lombok.Data;

@Data
public class PageResult<T> {
    private Long total;
    private Integer pageNum;
    private Integer pageSize;
    private T list;

    public static <T> PageResult<T> of(Long total, T list) {
        PageResult<T> result = new PageResult<>();
        result.setTotal(total);
        result.setList(list);
        return result;
    }
}
