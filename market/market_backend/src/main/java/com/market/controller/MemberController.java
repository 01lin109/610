package com.market.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.market.common.result.PageResult;
import com.market.common.result.Result;
import com.market.entity.Member;
import com.market.entity.MemberLevel;
import com.market.mapper.MemberLevelMapper;
import com.market.mapper.MemberMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "会员管理")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberLevelMapper levelMapper;

    @ApiOperation("会员列表(分页)")
    @GetMapping("/list")
    public Result<PageResult<List<Member>>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long levelId,
            @RequestParam(required = false) Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Member> list = memberMapper.selectList(keyword, levelId, status);
        PageInfo<Member> info = new PageInfo<>(list);
        return Result.success(PageResult.of(info.getTotal(), list));
    }

    @ApiOperation("会员详情")
    @GetMapping("/{id}")
    public Result<Member> detail(@PathVariable Long id) {
        return Result.success(memberMapper.selectById(id));
    }

    @ApiOperation("更新会员信息")
    @PutMapping
    public Result<Void> update(@RequestBody Member member) {
        member.setUpdateTime(LocalDateTime.now());
        memberMapper.update(member);
        return Result.success();
    }

    @ApiOperation("修改会员状态")
    @PutMapping("/{id}/status/{status}")
    public Result<Void> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        memberMapper.updateStatus(id, status);
        return Result.success();
    }

    @ApiOperation("删除会员")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        memberMapper.deleteById(id);
        return Result.success();
    }

    @ApiOperation("会员等级列表")
    @GetMapping("/level/list")
    public Result<List<MemberLevel>> levelList() {
        return Result.success(levelMapper.selectAll());
    }

    @ApiOperation("新增等级")
    @PostMapping("/level")
    public Result<Void> createLevel(@RequestBody MemberLevel level) {
        level.setCreateTime(LocalDateTime.now());
        levelMapper.insert(level);
        return Result.success();
    }

    @ApiOperation("更新等级")
    @PutMapping("/level")
    public Result<Void> updateLevel(@RequestBody MemberLevel level) {
        levelMapper.update(level);
        return Result.success();
    }

    @ApiOperation("删除等级")
    @DeleteMapping("/level/{id}")
    public Result<Void> deleteLevel(@PathVariable Long id) {
        levelMapper.deleteById(id);
        return Result.success();
    }
}