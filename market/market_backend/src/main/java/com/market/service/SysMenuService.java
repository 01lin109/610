package com.market.service;

import com.market.entity.SysMenu;
import java.util.List;

public interface SysMenuService extends BaseService<SysMenu, Long> {
    List<SysMenu> selectList(String keyword, Integer status);
    List<SysMenu> selectAll();
    List<SysMenu> selectByParentId(Long parentId);
    List<SysMenu> selectMenusByUserId(Long userId);
    List<SysMenu> selectMenusByRoleId(Long roleId);
    List<String> selectPermsByUserId(Long userId);
}
