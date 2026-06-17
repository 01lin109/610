package com.market.service;

import com.market.entity.SysDept;
import java.util.List;

public interface SysDeptService extends BaseService<SysDept, Long> {
    List<SysDept> selectList(String keyword, Integer status);
    List<SysDept> selectAll();
    List<SysDept> selectByParentId(Long parentId);
}
