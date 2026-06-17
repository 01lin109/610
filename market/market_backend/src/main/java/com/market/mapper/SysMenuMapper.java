package com.market.mapper;

import com.market.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper {
    SysMenu selectById(Long id);
    List<SysMenu> selectList(@Param("keyword") String keyword, @Param("status") Integer status);
    List<SysMenu> selectAll();
    List<SysMenu> selectByParentId(Long parentId);
    int insert(SysMenu menu);
    int update(SysMenu menu);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
    List<SysMenu> selectMenusByUserId(Long userId);
    List<SysMenu> selectMenusByRoleId(Long roleId);
    List<String> selectPermsByUserId(Long userId);
}
