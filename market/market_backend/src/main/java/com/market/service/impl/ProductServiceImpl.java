package com.market.service.impl;

import com.market.entity.Product;
import com.market.mapper.ProductMapper;
import com.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;

    @Override
    public Product getById(Long id) { return productMapper.selectById(id); }
    @Override
    public List<Product> list(Product query) { return productMapper.selectAll(); }
    @Override
    public int save(Product product) { return productMapper.insert(product); }
    @Override
    public int update(Product product) { return productMapper.update(product); }
    @Override
    public int deleteById(Long id) { return productMapper.deleteById(id); }
    @Override
    public int deleteBatch(List<Long> ids) { return productMapper.deleteBatch(ids); }
    @Override
    public List<Product> selectList(String keyword, Long categoryId, Integer status) { return productMapper.selectList(keyword, categoryId, status); }
    @Override
    public List<Product> selectAll() { return productMapper.selectAll(); }
    @Override
    public int updateStatus(Long id, Integer status) { return productMapper.updateStatus(id, status); }
    @Override
    public int updateStock(Long id, Integer stock) { return productMapper.updateStock(id, stock); }
    @Override
    public List<Product> selectTopSales(Integer limit) { return productMapper.selectTopSales(limit); }
    @Override
    public Long selectTotalCount() { return productMapper.selectTotalCount(); }
}
