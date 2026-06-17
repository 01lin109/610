package com.market.service.impl;

import com.market.entity.SysRole;
import com.market.mapper.SysRoleMapper;
import com.market.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {
    private final SysRoleMapper mapper;
    @Override public SysRole getById(Long id) { return mapper.selectById(id); }
    @Override public List<SysRole> list(SysRole q) { return mapper.selectAll(); }
    @Override public int save(SysRole r) { return mapper.insert(r); }
    @Override public int update(SysRole r) { return mapper.update(r); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return mapper.deleteBatch(ids); }
    @Override public SysRole getByRoleKey(String roleKey) { return mapper.selectByRoleKey(roleKey); }
    @Override public List<SysRole> selectList(String keyword, Integer status) { return mapper.selectList(keyword, status); }
    @Override public List<SysRole> selectAll() { return mapper.selectAll(); }
    @Override public List<SysRole> selectRolesByUserId(Long userId) { return mapper.selectRolesByUserId(userId); }
}
