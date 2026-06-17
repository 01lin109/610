package com.market.mapper;

import com.market.entity.Coupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponMapper {
    Coupon selectById(Long id);
    List<Coupon> selectList(@Param("keyword") String keyword, @Param("status") Integer status);
    List<Coupon> selectAll();
    int insert(Coupon coupon);
    int update(Coupon coupon);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int addReceivedCount(@Param("id") Long id);
    int addUsedCount(@Param("id") Long id);
    List<Coupon> selectAvailable();
}
