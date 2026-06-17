package com.market.service;

import com.market.common.result.PageResult;
import java.util.List;

public interface BaseService<T, ID> {
    T getById(ID id);
    List<T> list(T query);
    int save(T entity);
    int update(T entity);
    int deleteById(ID id);
    int deleteBatch(List<ID> ids);
}
