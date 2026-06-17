package com.market.mapper;

import com.market.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    Product selectById(Long id);
    List<Product> selectList(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, @Param("status") Integer status);
    List<Product> selectAll();
    int insert(Product product);
    int update(Product product);
    int deleteById(Long id);
    int deleteBatch(@Param("ids") List<Long> ids);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int updateStock(@Param("id") Long id, @Param("stock") Integer stock);
    int addSalesCount(@Param("id") Long id, @Param("count") Integer count);
    List<Product> selectTopSales(@Param("limit") Integer limit);
    Long selectTotalCount();
}
