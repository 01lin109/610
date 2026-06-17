package com.market.service.impl;

import com.market.common.exception.BusinessException;
import com.market.common.utils.JwtTokenUtil;
import com.market.entity.SysUser;
import com.market.mapper.SysUserMapper;
import com.market.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {
    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override public SysUser getById(Long id) { return sysUserMapper.selectById(id); }
    @Override public List<SysUser> list(SysUser query) { return sysUserMapper.selectAll(); }
    @Override public int save(SysUser user) { return sysUserMapper.insert(user); }
    @Override public int update(SysUser user) { return sysUserMapper.update(user); }
    @Override public int deleteById(Long id) { return sysUserMapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return sysUserMapper.deleteBatch(ids); }
    @Override public SysUser getByUsername(String username) { return sysUserMapper.selectByUsername(username); }
    @Override public List<SysUser> selectList(String keyword, Long deptId, Integer status) { return sysUserMapper.selectList(keyword, deptId, status); }
    @Override public List<SysUser> selectAll() { return sysUserMapper.selectAll(); }
    @Override public int updateStatus(Long id, Integer status) { return sysUserMapper.updateStatus(id, status); }
    @Override public int updatePassword(Long id, String password) { return sysUserMapper.updatePassword(id, passwordEncoder.encode(password)); }
    @Override public String login(String username, String password) {
        SysUser user = sysUserMapper.selectByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) throw new BusinessException("用户名或密码错误");
        return JwtTokenUtil.generateToken(user.getId(), user.getUsername());
    }
}
