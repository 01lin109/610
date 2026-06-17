package com.market.mapper;

import com.market.entity.SystemConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemConfigMapper {
    SystemConfig selectById(Long id);
    SystemConfig selectByKey(String configKey);
    List<SystemConfig> selectByType(String configType);
    List<SystemConfig> selectAll();
    int insert(SystemConfig config);
    int update(SystemConfig config);
    int updateByKey(@Param("configKey") String configKey, @Param("configValue") String configValue);
    int deleteById(Long id);
}
