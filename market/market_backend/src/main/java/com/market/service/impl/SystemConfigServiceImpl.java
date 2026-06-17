package com.market.service.impl;

import com.market.entity.SystemConfig;
import com.market.mapper.SystemConfigMapper;
import com.market.service.SystemConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemConfigServiceImpl implements SystemConfigService {
    private final SystemConfigMapper mapper;
    @Override public SystemConfig getById(Long id) { return mapper.selectById(id); }
    @Override public List<SystemConfig> list(SystemConfig q) { return mapper.selectAll(); }
    @Override public int save(SystemConfig c) { return mapper.insert(c); }
    @Override public int update(SystemConfig c) { return mapper.update(c); }
    @Override public int deleteById(Long id) { return mapper.deleteById(id); }
    @Override public int deleteBatch(List<Long> ids) { return 0; }
    @Override public SystemConfig getByKey(String configKey) { return mapper.selectByKey(configKey); }
    @Override public List<SystemConfig> selectByType(String configType) { return mapper.selectByType(configType); }
    @Override public List<SystemConfig> selectAll() { return mapper.selectAll(); }
    @Override public int updateByKey(String configKey, String configValue) { return mapper.updateByKey(configKey, configValue); }
}
