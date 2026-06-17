package com.market.service;

import com.market.entity.Product;
import java.util.List;

public interface ProductService extends BaseService<Product, Long> {
    List<Product> selectList(String keyword, Long categoryId, Integer status);
    List<Product> selectAll();
    int updateStatus(Long id, Integer status);
    int updateStock(Long id, Integer stock);
    List<Product> selectTopSales(Integer limit);
    Long selectTotalCount();
}
