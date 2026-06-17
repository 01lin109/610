package com.market.mapper;

import com.market.entity.Advertisement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdvertisementMapper {
    Advertisement selectById(Long id);
    List<Advertisement> selectList(@Param("keyword") String keyword, @Param("status") Integer status);
    List<Advertisement> selectAll();
    int insert(Advertisement ad);
    int update(Advertisement ad);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int addClickCount(Long id);
    List<Advertisement> selectByPosition(@Param("position") Integer position);
}
