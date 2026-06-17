package com.market.mapper;

import com.market.entity.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeMapper {
    Notice selectById(Long id);
    List<Notice> selectList(@Param("keyword") String keyword, @Param("noticeType") Integer noticeType, @Param("status") Integer status);
    List<Notice> selectAll();
    int insert(Notice notice);
    int update(Notice notice);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int updateTop(@Param("id") Long id, @Param("isTop") Integer isTop);
    List<Notice> selectTop(@Param("limit") Integer limit);
}
