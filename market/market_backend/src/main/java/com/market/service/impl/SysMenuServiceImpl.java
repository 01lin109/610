package com.market.service.impl;

import com.market.entity.SysMenu;
import com.market.mapper.SysMenuMapper;
import com.market.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements SysMenuService {
    private final SysMenuMapper mapper;
    @Override public SysMenu getById(Long id) { return mapper.selectById(id); }
    @Override public List<SysMenu> list(SysMenu q) { return mapper.selectAll(); }
    @Override public int save(SysMenu m) { return mapper.insert(m); }
    @Override public int update(SysMenu m) { return mapper.update(m); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return mapper.deleteBatch(ids); }
    @Override public List<SysMenu> selectList(String keyword, Integer status) { return mapper.selectList(keyword, status); }
    @Override public List<SysMenu> selectAll() { return mapper.selectAll(); }
    @Override public List<SysMenu> selectByParentId(Long parentId) { return mapper.selectByParentId(parentId); }
    @Override public List<SysMenu> selectMenusByUserId(Long userId) { return mapper.selectMenusByUserId(userId); }
    @Override public List<SysMenu> selectMenusByRoleId(Long roleId) { return mapper.selectMenusByRoleId(roleId); }
    @Override public List<String> selectPermsByUserId(Long userId) { return mapper.selectPermsByUserId(userId); }
}
