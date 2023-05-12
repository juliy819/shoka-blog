package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.juliy.model.dto.ArticleDTO;
import com.juliy.model.dto.ArticleFeaturedDTO;
import com.juliy.model.dto.ArticleTopDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.*;
import com.juliy.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文章控制器
 * @author juliy
 * @date 2023/3/28 21:43
 */
@Tag(name = "文章模块")
@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 获取后台文章列表
     * @param condition 查询条件
     * @return 后台文章列表
     */
    @Operation(summary = "获取后台文章列表")
    @SaCheckPermission("article:list")
    @GetMapping("/admin/list")
    public Result<PageResult<ArticleAdminVO>> getArticlesAdminByPage(ConditionDTO condition) {
        return Result.success(articleService.listArticlesAdminByPage(condition));
    }

    /**
     * 添加文章
     * @param articleDTO 文章
     */
    @Operation(summary = "添加文章")
    @SaCheckPermission("article:add")
    @PostMapping
    public Result<?> addArticle(@Validated @RequestBody ArticleDTO articleDTO) {
        articleService.saveOrUpdateArticle(articleDTO);
        return Result.success();
    }

    /**
     * 修改文章
     * @param articleDTO 文章
     */
    @Operation(summary = "修改文章")
    @SaCheckPermission("article:update")
    @PutMapping
    public Result<?> updateArticle(@Validated @RequestBody ArticleDTO articleDTO) {
        articleService.saveOrUpdateArticle(articleDTO);
        return Result.success();
    }

    /**
     * 编辑文章
     * @param articleId 文章id
     * @return 文章信息
     */
    @Operation(summary = "编辑文章")
    @SaCheckPermission("article:edit")
    @GetMapping("/edit/{articleId}")
    public Result<ArticleInfoVO> editArticle(@PathVariable("articleId") Integer articleId) {
        return Result.success(articleService.getArticleInfoById(articleId));
    }

    /**
     * 删除文章
     * @param articleIds 文章id列表
     */
    @Operation(summary = "删除文章")
    @SaCheckPermission("article:delete")
    @DeleteMapping("/{articleIds}")
    public Result<?> deleteArticles(@PathVariable List<Integer> articleIds) {
        articleService.deleteArticles(articleIds);
        return Result.success();
    }

    /**
     * 回收文章
     * @param articleIds 文章id列表
     */
    @Operation(summary = "回收文章")
    @SaCheckPermission("article:recycle")
    @PutMapping("/recycle")
    public Result<?> recycleArticles(@RequestBody List<Integer> articleIds) {
        articleService.recycleArticles(articleIds);
        return Result.success();
    }

    /**
     * 恢复文章
     * @param articleIds 文章id列表
     */
    @Operation(summary = "恢复文章")
    @SaCheckPermission("article:recover")
    @PutMapping("/recover")
    public Result<?> recoverArticles(@RequestBody List<Integer> articleIds) {
        articleService.recoverArticles(articleIds);
        return Result.success();
    }

    /**
     * 保存文章图片
     * @param file 图片
     * @return 图片地址
     */
    @Operation(summary = "保存文章图片")
    @SaCheckPermission("article:upload")
    @PostMapping("/upload")
    public Result<String> saveArticleImages(@RequestParam("file") MultipartFile file) {
        return Result.success(articleService.saveArticleImage(file));
    }


    /**
     * 置顶文章
     * @param top 置顶信息
     */
    @Operation(summary = "置顶文章")
    @SaCheckPermission("article:top")
    @PutMapping("/top")
    public Result<?> updateArticleTop(@Validated @RequestBody ArticleTopDTO top) {
        articleService.updateArticleTop(top);
        return Result.success();
    }

    /**
     * 推荐文章
     * @param featured 推荐信息
     */
    @Operation(summary = "推荐文章")
    @SaCheckPermission("article:featured")
    @PutMapping("/featured")
    public Result<?> updateArticleFeatured(@Validated @RequestBody ArticleFeaturedDTO featured) {
        articleService.updateArticleFeatured(featured);
        return Result.success();
    }

    /**
     * 搜索文章
     * @param keywords 关键词
     * @return 文章列表
     */
    @Operation(summary = "搜索文章")
    @GetMapping("/search")
    public Result<List<ArticleSearchVO>> getArticlesBySearch(String keywords) {
        return Result.success(articleService.listArticlesBySearch(keywords));
    }

    /**
     * 获取首页文章列表
     * @return 首页文章列表
     */
    @Operation(summary = "获取首页文章列表")
    @GetMapping("/list")
    public Result<PageResult<ArticleHomeVO>> getArticlesByPage() {
        return Result.success(articleService.listArticlesHomeByPage());
    }

    /**
     * 查看文章
     * @param articleId 文章id
     * @return 文章
     */
    @Operation(summary = "查看文章")
    @GetMapping("/{articleId}")
    public Result<ArticleVO> getArticleById(@PathVariable Integer articleId) {
        return Result.success(articleService.getArticleHomeById(articleId));
    }

    /**
     * 获取推荐文章
     * @return 推荐文章列表
     */
    @Operation(summary = "获取推荐文章")
    @GetMapping("/featured")
    public Result<List<ArticleFeaturedVO>> getArticlesFeatured() {
        return Result.success(articleService.listArticlesFeatured());
    }

    /**
     * 获取文章归档
     * @return 文章归档列表
     */
    @Operation(summary = "获取文章归档")
    @GetMapping("/archives/list")
    public Result<PageResult<ArchiveVO>> listArchiveVO() {
        return Result.success(articleService.listArchives());
    }

    /**
     * 点赞文章
     * @param articleId 文章id
     */
    @Operation(summary = "点赞文章")
    @PostMapping("/{articleId}/like")
    public Result<?> likeArticle(@PathVariable("articleId") Integer articleId) {
        articleService.likeArticle(articleId);
        return Result.success();
    }
}