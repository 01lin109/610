package com.market.service;

import com.market.entity.SysUser;
import java.util.List;

public interface SysUserService extends BaseService<SysUser, Long> {
    SysUser getByUsername(String username);
    List<SysUser> selectList(String keyword, Long deptId, Integer status);
    List<SysUser> selectAll();
    int updateStatus(Long id, Integer status);
    int updatePassword(Long id, String password);
    String login(String username, String password);
}
