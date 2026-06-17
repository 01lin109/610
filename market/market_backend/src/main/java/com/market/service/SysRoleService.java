package com.market.service;

import com.market.entity.SysRole;
import java.util.List;

public interface SysRoleService extends BaseService<SysRole, Long> {
    SysRole getByRoleKey(String roleKey);
    List<SysRole> selectList(String keyword, Integer status);
    List<SysRole> selectAll();
    List<SysRole> selectRolesByUserId(Long userId);
}
