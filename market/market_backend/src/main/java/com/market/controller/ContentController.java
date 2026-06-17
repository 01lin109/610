package com.market.controller;

import com.market.common.result.Result;
import com.market.entity.Article;
import com.market.entity.Notice;
import com.market.service.ArticleService;
import com.market.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "内容管理")
@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
public class ContentController {

    private final ArticleService articleService;
    private final NoticeService noticeService;

    @ApiOperation("文章列表")
    @GetMapping("/articles")
    public Result<?> articleList(@RequestParam(required = false) String keyword,
                                  @RequestParam(required = false) Long categoryId,
                                  @RequestParam(required = false) Integer status) {
        return Result.success(articleService.selectList(keyword, categoryId, status));
    }

    @ApiOperation("文章详情")
    @GetMapping("/articles/{id}")
    public Result<?> articleDetail(@PathVariable Long id) {
        return Result.success(articleService.getById(id));
    }

    @ApiOperation("新增文章")
    @PostMapping("/articles")
    public Result<?> addArticle(@RequestBody Article article) {
        return Result.success(articleService.save(article));
    }

    @ApiOperation("编辑文章")
    @PutMapping("/articles")
    public Result<?> updateArticle(@RequestBody Article article) {
        return Result.success(articleService.update(article));
    }

    @ApiOperation("删除文章")
    @DeleteMapping("/articles/{id}")
    public Result<?> deleteArticle(@PathVariable Long id) {
        return Result.success(articleService.deleteById(id));
    }

    @ApiOperation("文章状态变更")
    @PutMapping("/articles/{id}/status/{status}")
    public Result<?> updateArticleStatus(@PathVariable Long id, @PathVariable Integer status) {
        return Result.success(articleService.updateStatus(id, status));
    }

    @ApiOperation("公告列表")
    @GetMapping("/notices")
    public Result<?> noticeList(@RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) Integer noticeType,
                                 @RequestParam(required = false) Integer status) {
        return Result.success(noticeService.selectList(keyword, noticeType, status));
    }

    @ApiOperation("公告详情")
    @GetMapping("/notices/{id}")
    public Result<?> noticeDetail(@PathVariable Long id) {
        return Result.success(noticeService.getById(id));
    }

    @ApiOperation("新增公告")
    @PostMapping("/notices")
    public Result<?> addNotice(@RequestBody Notice notice) {
        return Result.success(noticeService.save(notice));
    }

    @ApiOperation("编辑公告")
    @PutMapping("/notices")
    public Result<?> updateNotice(@RequestBody Notice notice) {
        return Result.success(noticeService.update(notice));
    }

    @ApiOperation("删除公告")
    @DeleteMapping("/notices/{id}")
    public Result<?> deleteNotice(@PathVariable Long id) {
        return Result.success(noticeService.deleteById(id));
    }

    @ApiOperation("公告状态变更")
    @PutMapping("/notices/{id}/status/{status}")
    public Result<?> updateNoticeStatus(@PathVariable Long id, @PathVariable Integer status) {
        return Result.success(noticeService.updateStatus(id, status));
    }

    @ApiOperation("公告置顶")
    @PutMapping("/notices/{id}/top/{isTop}")
    public Result<?> updateNoticeTop(@PathVariable Long id, @PathVariable Integer isTop) {
        return Result.success(noticeService.updateTop(id, isTop));
    }
}