package com.market.controller;

import com.market.common.result.Result;
import com.market.entity.SystemConfig;
import com.market.service.SystemConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "系统设置")
@RestController
@RequestMapping("/setting")
@RequiredArgsConstructor
public class SettingController {

    private final SystemConfigService systemConfigService;

    @ApiOperation("获取所有设置")
    @GetMapping("/configs")
    public Result<?> configList() {
        return Result.success(systemConfigService.selectAll());
    }

    @ApiOperation("按类型获取设置")
    @GetMapping("/configs/type/{type}")
    public Result<?> configByType(@PathVariable String type) {
        return Result.success(systemConfigService.selectByType(type));
    }

    @ApiOperation("新增配置")
    @PostMapping("/configs")
    public Result<?> addConfig(@RequestBody SystemConfig config) {
        return Result.success(systemConfigService.save(config));
    }

    @ApiOperation("编辑配置")
    @PutMapping("/configs")
    public Result<?> updateConfig(@RequestBody SystemConfig config) {
        return Result.success(systemConfigService.update(config));
    }

    @ApiOperation("删除配置")
    @DeleteMapping("/configs/{id}")
    public Result<?> deleteConfig(@PathVariable Long id) {
        return Result.success(systemConfigService.deleteById(id));
    }

    @ApiOperation("批量更新配置")
    @PutMapping("/configs/batch")
    public Result<?> updateConfigBatch(@RequestBody List<SystemConfig> configs) {
        for (SystemConfig config : configs) {
            systemConfigService.updateByKey(config.getConfigKey(), config.getConfigValue());
        }
        return Result.success();
    }
}