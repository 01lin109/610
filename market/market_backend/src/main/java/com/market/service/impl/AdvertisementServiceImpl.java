package com.market.service.impl;

import com.market.entity.Advertisement;
import com.market.mapper.AdvertisementMapper;
import com.market.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementMapper mapper;
    @Override public Advertisement getById(Long id) { return mapper.selectById(id); }
    @Override public List<Advertisement> list(Advertisement q) { return mapper.selectAll(); }
    @Override public int save(Advertisement a) { return mapper.insert(a); }
    @Override public int update(Advertisement a) { return mapper.update(a); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return mapper.deleteBatch(ids); }
    @Override public List<Advertisement> selectList(String keyword, Integer status) { return mapper.selectList(keyword, status); }
    @Override public List<Advertisement> selectAll() { return mapper.selectAll(); }
    @Override public int updateStatus(Long id, Integer status) { return mapper.updateStatus(id, status); }
    @Override public int addClickCount(Long id) { return mapper.addClickCount(id); }
    @Override public List<Advertisement> selectByPosition(Integer position) { return mapper.selectByPosition(position); }
}
