package com.market.mapper;

import com.market.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {
    SysRole selectById(Long id);
    SysRole selectByRoleKey(String roleKey);
    List<SysRole> selectList(@Param("keyword") String keyword, @Param("status") Integer status);
    List<SysRole> selectAll();
    int insert(SysRole role);
    int update(SysRole role);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
    List<SysRole> selectRolesByUserId(Long userId);
}
