package com.market.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SysMenu {
    private Long id;
    private String menuName;
    private Long parentId;
    private Integer orderNum;
    private String path;
    private String component;
    private String query;
    private String menuType;
    private String visible;
    private String perms;
    private String icon;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}
