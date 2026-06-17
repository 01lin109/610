package com.market.mapper;

import com.market.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryMapper {
    ProductCategory selectById(Long id);
    List<ProductCategory> selectAll();
    List<ProductCategory> selectByParentId(Long parentId);
    int insert(ProductCategory category);
    int update(ProductCategory category);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
}
