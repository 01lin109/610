package com.market.mapper;

import com.market.entity.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper {
    SysDept selectById(Long id);
    List<SysDept> selectList(@Param("keyword") String keyword, @Param("status") Integer status);
    List<SysDept> selectAll();
    List<SysDept> selectByParentId(Long parentId);
    int insert(SysDept dept);
    int update(SysDept dept);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
}
