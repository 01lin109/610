package com.market.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SysDept {
    private Long id;
    private Long parentId;
    private String deptName;
    private Integer orderNum;
    private String leader;
    private String phone;
    private String email;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer deleted;
}
