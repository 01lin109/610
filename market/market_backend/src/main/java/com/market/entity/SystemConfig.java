package com.market.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SystemConfig {
    private Long id;
    private String configKey;
    private String configValue;
    private String configType;
    private String configName;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}