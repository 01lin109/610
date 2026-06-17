package com.market.mapper;

import com.market.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    Article selectById(Long id);
    List<Article> selectList(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, @Param("status") Integer status);
    List<Article> selectAll();
    int insert(Article article);
    int update(Article article);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int addViewCount(Long id);
    int updateTop(@Param("id") Long id, @Param("isTop") Integer isTop);
}
