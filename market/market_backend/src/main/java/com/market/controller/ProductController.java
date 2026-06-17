package com.market.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.market.common.result.PageResult;
import com.market.common.result.Result;
import com.market.entity.Product;
import com.market.entity.ProductCategory;
import com.market.mapper.ProductCategoryMapper;
import com.market.mapper.ProductMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "商品管理")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductMapper productMapper;
    private final ProductCategoryMapper categoryMapper;

    @ApiOperation("商品列表(分页)")
    @GetMapping("/list")
    public Result<PageResult<List<Product>>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> list = productMapper.selectList(keyword, categoryId, status);
        PageInfo<Product> info = new PageInfo<>(list);
        return Result.success(PageResult.of(info.getTotal(), list));
    }

    @ApiOperation("商品详情")
    @GetMapping("/{id}")
    public Result<Product> detail(@PathVariable Long id) {
        return Result.success(productMapper.selectById(id));
    }

    @ApiOperation("新增商品")
    @PostMapping
    public Result<Void> create(@RequestBody Product product) {
        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());
        product.setSales(0);
        if (product.getStatus() == null) product.setStatus(1);
        productMapper.insert(product);
        return Result.success();
    }

    @ApiOperation("更新商品")
    @PutMapping
    public Result<Void> update(@RequestBody Product product) {
        product.setUpdateTime(LocalDateTime.now());
        productMapper.update(product);
        return Result.success();
    }

    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        productMapper.deleteById(id);
        return Result.success();
    }

    @ApiOperation("批量删除")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        productMapper.deleteBatch(ids);
        return Result.success();
    }

    @ApiOperation("上架/下架")
    @PutMapping("/{id}/status/{status}")
    public Result<Void> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        productMapper.updateStatus(id, status);
        return Result.success();
    }

    @ApiOperation("商品分类列表")
    @GetMapping("/categories")
    public Result<List<ProductCategory>> categoryList() {
        return Result.success(categoryMapper.selectAll());
    }

    @ApiOperation("商品分类详情")
    @GetMapping("/categories/{id}")
    public Result<ProductCategory> categoryDetail(@PathVariable Long id) {
        return Result.success(categoryMapper.selectById(id));
    }

    @ApiOperation("新增分类")
    @PostMapping("/categories")
    public Result<Void> createCategory(@RequestBody ProductCategory category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.insert(category);
        return Result.success();
    }

    @ApiOperation("更新分类")
    @PutMapping("/categories")
    public Result<Void> updateCategory(@RequestBody ProductCategory category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
        return Result.success();
    }

    @ApiOperation("删除分类")
    @DeleteMapping("/categories/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        categoryMapper.deleteById(id);
        return Result.success();
    }
}