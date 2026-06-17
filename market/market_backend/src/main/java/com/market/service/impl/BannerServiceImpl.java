package com.market.service.impl;

import com.market.entity.Banner;
import com.market.mapper.BannerMapper;
import com.market.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerMapper mapper;
    @Override public Banner getById(Long id) { return mapper.selectById(id); }
    @Override public List<Banner> list(Banner q) { return mapper.selectAll(); }
    @Override public int save(Banner b) { return mapper.insert(b); }
    @Override public int update(Banner b) { return mapper.update(b); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return mapper.deleteBatch(ids); }
    @Override public List<Banner> selectList(String keyword, Integer status) { return mapper.selectList(keyword, status); }
    @Override public List<Banner> selectAll() { return mapper.selectAll(); }
    @Override public int updateStatus(Long id, Integer status) { return mapper.updateStatus(id, status); }
    @Override public List<Banner> selectActive() { return mapper.selectActive(); }
}
