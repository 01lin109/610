package com.market.service.impl;

import com.market.entity.Coupon;
import com.market.mapper.CouponMapper;
import com.market.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponMapper mapper;
    @Override public Coupon getById(Long id) { return mapper.selectById(id); }
    @Override public List<Coupon> list(Coupon q) { return mapper.selectAll(); }
    @Override public int save(Coupon c) { return mapper.insert(c); }
    @Override public int update(Coupon c) { return mapper.update(c); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return mapper.deleteBatch(ids); }
    @Override public List<Coupon> selectList(String keyword, Integer status) { return mapper.selectList(keyword, status); }
    @Override public List<Coupon> selectAll() { return mapper.selectAll(); }
    @Override public int updateStatus(Long id, Integer status) { return mapper.updateStatus(id, status); }
    @Override public List<Coupon> selectAvailable() { return mapper.selectAvailable(); }
}
