package com.market.service;

import com.market.entity.Banner;
import java.util.List;

public interface BannerService extends BaseService<Banner, Long> {
    List<Banner> selectList(String keyword, Integer status);
    List<Banner> selectAll();
    int updateStatus(Long id, Integer status);
    List<Banner> selectActive();
}
