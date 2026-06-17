package com.market.service.impl;

import com.market.entity.Article;
import com.market.mapper.ArticleMapper;
import com.market.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper mapper;
    @Override public Article getById(Long id) { return mapper.selectById(id); }
    @Override public List<Article> list(Article q) { return mapper.selectAll(); }
    @Override public int save(Article a) { return mapper.insert(a); }
    @Override public int update(Article a) { return mapper.update(a); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return mapper.deleteBatch(ids); }
    @Override public List<Article> selectList(String keyword, Long categoryId, Integer status) { return mapper.selectList(keyword, categoryId, status); }
    @Override public List<Article> selectAll() { return mapper.selectAll(); }
    @Override public int updateStatus(Long id, Integer status) { return mapper.updateStatus(id, status); }
    @Override public int addViewCount(Long id) { return mapper.addViewCount(id); }
    @Override public int updateTop(Long id, Integer isTop) { return mapper.updateTop(id, isTop); }
}
