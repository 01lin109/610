package com.market.controller;

import com.market.common.result.Result;
import com.market.entity.SysUser;
import com.market.entity.SysRole;
import com.market.entity.SysMenu;
import com.market.entity.SysDept;
import com.market.service.SysUserService;
import com.market.service.SysRoleService;
import com.market.service.SysMenuService;
import com.market.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "权限管理")
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;
    private final SysDeptService sysDeptService;

    @ApiOperation("用户列表")
    @GetMapping("/users")
    public Result<?> userList(@RequestParam(required = false) String keyword,
                               @RequestParam(required = false) Long deptId,
                               @RequestParam(required = false) Integer status) {
        return Result.success(sysUserService.selectList(keyword, deptId, status));
    }

    @ApiOperation("用户详情")
    @GetMapping("/users/{id}")
    public Result<?> userDetail(@PathVariable Long id) {
        return Result.success(sysUserService.getById(id));
    }

    @ApiOperation("新增用户")
    @PostMapping("/users")
    public Result<?> addUser(@RequestBody SysUser user) {
        return Result.success(sysUserService.save(user));
    }

    @ApiOperation("编辑用户")
    @PutMapping("/users")
    public Result<?> updateUser(@RequestBody SysUser user) {
        return Result.success(sysUserService.update(user));
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/users/{id}")
    public Result<?> deleteUser(@PathVariable Long id) {
        return Result.success(sysUserService.deleteById(id));
    }

    @ApiOperation("用户状态变更")
    @PutMapping("/users/{id}/status/{status}")
    public Result<?> updateUserStatus(@PathVariable Long id, @PathVariable Integer status) {
        return Result.success(sysUserService.updateStatus(id, status));
    }

    @ApiOperation("角色列表")
    @GetMapping("/roles")
    public Result<?> roleList(@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer status) {
        return Result.success(sysRoleService.selectList(keyword, status));
    }

    @ApiOperation("角色详情")
    @GetMapping("/roles/{id}")
    public Result<?> roleDetail(@PathVariable Long id) {
        return Result.success(sysRoleService.getById(id));
    }

    @ApiOperation("新增角色")
    @PostMapping("/roles")
    public Result<?> addRole(@RequestBody SysRole role) {
        return Result.success(sysRoleService.save(role));
    }

    @ApiOperation("编辑角色")
    @PutMapping("/roles")
    public Result<?> updateRole(@RequestBody SysRole role) {
        return Result.success(sysRoleService.update(role));
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/roles/{id}")
    public Result<?> deleteRole(@PathVariable Long id) {
        return Result.success(sysRoleService.deleteById(id));
    }

    @ApiOperation("菜单树")
    @GetMapping("/menus")
    public Result<?> menuList(@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer status) {
        return Result.success(sysMenuService.selectList(keyword, status));
    }

    @ApiOperation("菜单详情")
    @GetMapping("/menus/{id}")
    public Result<?> menuDetail(@PathVariable Long id) {
        return Result.success(sysMenuService.getById(id));
    }

    @ApiOperation("新增菜单")
    @PostMapping("/menus")
    public Result<?> addMenu(@RequestBody SysMenu menu) {
        return Result.success(sysMenuService.save(menu));
    }

    @ApiOperation("编辑菜单")
    @PutMapping("/menus")
    public Result<?> updateMenu(@RequestBody SysMenu menu) {
        return Result.success(sysMenuService.update(menu));
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("/menus/{id}")
    public Result<?> deleteMenu(@PathVariable Long id) {
        return Result.success(sysMenuService.deleteById(id));
    }

    @ApiOperation("部门列表")
    @GetMapping("/depts")
    public Result<?> deptList(@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer status) {
        return Result.success(sysDeptService.selectList(keyword, status));
    }

    @ApiOperation("部门详情")
    @GetMapping("/depts/{id}")
    public Result<?> deptDetail(@PathVariable Long id) {
        return Result.success(sysDeptService.getById(id));
    }

    @ApiOperation("新增部门")
    @PostMapping("/depts")
    public Result<?> addDept(@RequestBody SysDept dept) {
        return Result.success(sysDeptService.save(dept));
    }

    @ApiOperation("编辑部门")
    @PutMapping("/depts")
    public Result<?> updateDept(@RequestBody SysDept dept) {
        return Result.success(sysDeptService.update(dept));
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/depts/{id}")
    public Result<?> deleteDept(@PathVariable Long id) {
        return Result.success(sysDeptService.deleteById(id));
    }
}