package com.market.service;

import com.market.entity.Advertisement;
import java.util.List;

public interface AdvertisementService extends BaseService<Advertisement, Long> {
    List<Advertisement> selectList(String keyword, Integer status);
    List<Advertisement> selectAll();
    int updateStatus(Long id, Integer status);
    int addClickCount(Long id);
    List<Advertisement> selectByPosition(Integer position);
}
