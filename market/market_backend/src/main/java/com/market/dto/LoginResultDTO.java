package com.market.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LoginResultDTO {
    private String token;
    private Long userId;
    private String username;
    private String nickname;
    private String avatar;
    private LocalDateTime expireTime;
}