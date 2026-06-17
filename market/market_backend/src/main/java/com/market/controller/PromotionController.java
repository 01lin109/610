package com.market.controller;

import com.market.common.result.Result;
import com.market.entity.Coupon;
import com.market.entity.Promotion;
import com.market.service.CouponService;
import com.market.service.PromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "促销管理")
@RestController
@RequestMapping("/promotion")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;
    private final CouponService couponService;

    @ApiOperation("活动列表")
    @GetMapping("/promotions")
    public Result<?> promotionList(@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer status) {
        return Result.success(promotionService.selectList(keyword, status));
    }

    @ApiOperation("活动详情")
    @GetMapping("/promotions/{id}")
    public Result<?> promotionDetail(@PathVariable Long id) {
        return Result.success(promotionService.getById(id));
    }

    @ApiOperation("新增活动")
    @PostMapping("/promotions")
    public Result<?> addPromotion(@RequestBody Promotion promotion) {
        return Result.success(promotionService.save(promotion));
    }

    @ApiOperation("编辑活动")
    @PutMapping("/promotions")
    public Result<?> updatePromotion(@RequestBody Promotion promotion) {
        return Result.success(promotionService.update(promotion));
    }

    @ApiOperation("删除活动")
    @DeleteMapping("/promotions/{id}")
    public Result<?> deletePromotion(@PathVariable Long id) {
        return Result.success(promotionService.deleteById(id));
    }

    @ApiOperation("活动状态变更")
    @PutMapping("/promotions/{id}/status/{status}")
    public Result<?> updatePromotionStatus(@PathVariable Long id, @PathVariable Integer status) {
        return Result.success(promotionService.updateStatus(id, status));
    }

    @ApiOperation("优惠券列表")
    @GetMapping("/coupons")
    public Result<?> couponList(@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer status) {
        return Result.success(couponService.selectList(keyword, status));
    }

    @ApiOperation("优惠券详情")
    @GetMapping("/coupons/{id}")
    public Result<?> couponDetail(@PathVariable Long id) {
        return Result.success(couponService.getById(id));
    }

    @ApiOperation("新增优惠券")
    @PostMapping("/coupons")
    public Result<?> addCoupon(@RequestBody Coupon coupon) {
        return Result.success(couponService.save(coupon));
    }

    @ApiOperation("编辑优惠券")
    @PutMapping("/coupons")
    public Result<?> updateCoupon(@RequestBody Coupon coupon) {
        return Result.success(couponService.update(coupon));
    }

    @ApiOperation("删除优惠券")
    @DeleteMapping("/coupons/{id}")
    public Result<?> deleteCoupon(@PathVariable Long id) {
        return Result.success(couponService.deleteById(id));
    }

    @ApiOperation("优惠券状态变更")
    @PutMapping("/coupons/{id}/status/{status}")
    public Result<?> updateCouponStatus(@PathVariable Long id, @PathVariable Integer status) {
        return Result.success(couponService.updateStatus(id, status));
    }
}