package com.market.mapper;

import com.market.entity.Promotion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PromotionMapper {
    Promotion selectById(Long id);
    List<Promotion> selectList(@Param("keyword") String keyword, @Param("status") Integer status);
    List<Promotion> selectAll();
    int insert(Promotion promotion);
    int update(Promotion promotion);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    List<Promotion> selectActive();
}
