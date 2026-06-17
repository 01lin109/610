package com.market.mapper;

import com.market.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    SysUser selectById(Long id);
    SysUser selectByUsername(String username);
    List<SysUser> selectList(@Param("keyword") String keyword, @Param("deptId") Long deptId, @Param("status") Integer status);
    List<SysUser> selectAll();
    int insert(SysUser user);
    int update(SysUser user);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int updatePassword(@Param("id") Long id, @Param("password") String password);
}
