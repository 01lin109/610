package com.market.service;

import com.market.entity.Promotion;
import java.util.List;

public interface PromotionService extends BaseService<Promotion, Long> {
    List<Promotion> selectList(String keyword, Integer status);
    List<Promotion> selectAll();
    int updateStatus(Long id, Integer status);
    List<Promotion> selectActive();
}
