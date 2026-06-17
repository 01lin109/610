package com.market.service.impl;

import com.market.entity.SysDept;
import com.market.mapper.SysDeptMapper;
import com.market.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl implements SysDeptService {
    private final SysDeptMapper mapper;
    @Override public SysDept getById(Long id) { return mapper.selectById(id); }
    @Override public List<SysDept> list(SysDept q) { return mapper.selectAll(); }
    @Override public int save(SysDept d) { return mapper.insert(d); }
    @Override public int update(SysDept d) { return mapper.update(d); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return mapper.deleteBatch(ids); }
    @Override public List<SysDept> selectList(String keyword, Integer status) { return mapper.selectList(keyword, status); }
    @Override public List<SysDept> selectAll() { return mapper.selectAll(); }
    @Override public List<SysDept> selectByParentId(Long parentId) { return mapper.selectByParentId(parentId); }
}
