package com.market.controller;

import com.market.common.result.Result;
import com.market.entity.Banner;
import com.market.entity.Advertisement;
import com.market.service.BannerService;
import com.market.service.AdvertisementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "运营管理")
@RestController
@RequestMapping("/operation")
@RequiredArgsConstructor
public class OperationController {

    private final BannerService bannerService;
    private final AdvertisementService advertisementService;

    @ApiOperation("轮播图列表")
    @GetMapping("/banners")
    public Result<?> bannerList(@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer status) {
        return Result.success(bannerService.selectList(keyword, status));
    }

    @ApiOperation("轮播图详情")
    @GetMapping("/banners/{id}")
    public Result<?> bannerDetail(@PathVariable Long id) {
        return Result.success(bannerService.getById(id));
    }

    @ApiOperation("新增轮播图")
    @PostMapping("/banners")
    public Result<?> addBanner(@RequestBody Banner banner) {
        return Result.success(bannerService.save(banner));
    }

    @ApiOperation("编辑轮播图")
    @PutMapping("/banners")
    public Result<?> updateBanner(@RequestBody Banner banner) {
        return Result.success(bannerService.update(banner));
    }

    @ApiOperation("删除轮播图")
    @DeleteMapping("/banners/{id}")
    public Result<?> deleteBanner(@PathVariable Long id) {
        return Result.success(bannerService.deleteById(id));
    }

    @ApiOperation("轮播图状态变更")
    @PutMapping("/banners/{id}/status/{status}")
    public Result<?> updateBannerStatus(@PathVariable Long id, @PathVariable Integer status) {
        return Result.success(bannerService.updateStatus(id, status));
    }

    @ApiOperation("广告列表")
    @GetMapping("/ads")
    public Result<?> adList(@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer status) {
        return Result.success(advertisementService.selectList(keyword, status));
    }

    @ApiOperation("广告详情")
    @GetMapping("/ads/{id}")
    public Result<?> adDetail(@PathVariable Long id) {
        return Result.success(advertisementService.getById(id));
    }

    @ApiOperation("新增广告")
    @PostMapping("/ads")
    public Result<?> addAd(@RequestBody Advertisement ad) {
        return Result.success(advertisementService.save(ad));
    }

    @ApiOperation("编辑广告")
    @PutMapping("/ads")
    public Result<?> updateAd(@RequestBody Advertisement ad) {
        return Result.success(advertisementService.update(ad));
    }

    @ApiOperation("删除广告")
    @DeleteMapping("/ads/{id}")
    public Result<?> deleteAd(@PathVariable Long id) {
        return Result.success(advertisementService.deleteById(id));
    }

    @ApiOperation("广告状态变更")
    @PutMapping("/ads/{id}/status/{status}")
    public Result<?> updateAdStatus(@PathVariable Long id, @PathVariable Integer status) {
        return Result.success(advertisementService.updateStatus(id, status));
    }
}