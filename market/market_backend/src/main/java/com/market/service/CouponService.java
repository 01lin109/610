package com.market.service;

import com.market.entity.Coupon;
import java.util.List;

public interface CouponService extends BaseService<Coupon, Long> {
    List<Coupon> selectList(String keyword, Integer status);
    List<Coupon> selectAll();
    int updateStatus(Long id, Integer status);
    List<Coupon> selectAvailable();
}
