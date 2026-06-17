package com.market.service;

import com.market.entity.ProductCategory;
import java.util.List;

public interface ProductCategoryService extends BaseService<ProductCategory, Long> {
    List<ProductCategory> selectAll();
    List<ProductCategory> selectByParentId(Long parentId);
}
