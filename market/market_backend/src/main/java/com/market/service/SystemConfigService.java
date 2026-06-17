package com.market.service;

import com.market.entity.SystemConfig;
import java.util.List;

public interface SystemConfigService extends BaseService<SystemConfig, Long> {
    SystemConfig getByKey(String configKey);
    List<SystemConfig> selectByType(String configType);
    List<SystemConfig> selectAll();
    int updateByKey(String configKey, String configValue);
}
