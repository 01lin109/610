package com.market.service.impl;

import com.market.entity.ProductCategory;
import com.market.mapper.ProductCategoryMapper;
import com.market.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryMapper mapper;
    @Override public ProductCategory getById(Long id) { return mapper.selectById(id); }
    @Override public List<ProductCategory> list(ProductCategory q) { return mapper.selectAll(); }
    @Override public int save(ProductCategory c) { return mapper.insert(c); }
    @Override public int update(ProductCategory c) { return mapper.update(c); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return mapper.deleteBatch(ids); }
    @Override public List<ProductCategory> selectAll() { return mapper.selectAll(); }
    @Override public List<ProductCategory> selectByParentId(Long parentId) { return mapper.selectByParentId(parentId); }
}
