package com.market.service;

import com.market.entity.Article;
import java.util.List;

public interface ArticleService extends BaseService<Article, Long> {
    List<Article> selectList(String keyword, Long categoryId, Integer status);
    List<Article> selectAll();
    int updateStatus(Long id, Integer status);
    int addViewCount(Long id);
    int updateTop(Long id, Integer isTop);
}
