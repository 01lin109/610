package com.market.service.impl;

import com.market.entity.Promotion;
import com.market.mapper.PromotionMapper;
import com.market.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionMapper mapper;
    @Override public Promotion getById(Long id) { return mapper.selectById(id); }
    @Override public List<Promotion> list(Promotion q) { return mapper.selectAll(); }
    @Override public int save(Promotion p) { return mapper.insert(p); }
    @Override public int update(Promotion p) { return mapper.update(p); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return mapper.deleteBatch(ids); }
    @Override public List<Promotion> selectList(String keyword, Integer status) { return mapper.selectList(keyword, status); }
    @Override public List<Promotion> selectAll() { return mapper.selectAll(); }
    @Override public int updateStatus(Long id, Integer status) { return mapper.updateStatus(id, status); }
    @Override public List<Promotion> selectActive() { return mapper.selectActive(); }
}
