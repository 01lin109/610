package com.market.mapper;

import com.market.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {
    Banner selectById(Long id);
    List<Banner> selectList(@Param("keyword") String keyword, @Param("status") Integer status);
    List<Banner> selectAll();
    int insert(Banner banner);
    int update(Banner banner);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    List<Banner> selectActive();
}
