package com.market.controller;

import com.market.common.result.Result;
import com.market.common.utils.JwtTokenUtil;
import com.market.dto.LoginDTO;
import com.market.dto.LoginResultDTO;
import com.market.entity.SysMenu;
import com.market.entity.SysUser;
import com.market.mapper.SysMenuMapper;
import com.market.mapper.SysUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "认证管理")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SysUserMapper sysUserMapper;
    private final SysMenuMapper sysMenuMapper;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<LoginResultDTO> login(@RequestBody LoginDTO loginDTO) {
        SysUser user = sysUserMapper.selectByUsername(loginDTO.getUsername());
        if (user == null) {
            return Result.error("用户名或密码错误");
        }
        if (user.getStatus() != 1) {
            return Result.error("账号已被禁用，请联系管理员");
        }
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return Result.error("用户名或密码错误");
        }

        String token = JwtTokenUtil.generateToken(user.getId(), user.getUsername());

        LoginResultDTO result = new LoginResultDTO();
        result.setToken(token);
        result.setUserId(user.getId());
        result.setUsername(user.getUsername());
        result.setNickname(user.getNickname() != null ? user.getNickname() : user.getUsername());
        result.setAvatar(user.getAvatar());
        result.setExpireTime(LocalDateTime.now().plusDays(1));
        return Result.success(result);
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/info")
    public Result<Map<String, Object>> info() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        SysUser user = sysUserMapper.selectByUsername(username);
        List<SysMenu> menus = sysMenuMapper.selectMenusByUserId(user.getId());
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("avatar", user.getAvatar());
        userInfo.put("roles", new String[]{"admin"});
        userInfo.put("permissions", new String[]{"*:*:*"});
        data.put("user", userInfo);
        data.put("menus", menus);
        return Result.success(data);
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.success();
    }
}
