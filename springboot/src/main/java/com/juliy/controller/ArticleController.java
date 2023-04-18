package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.juliy.model.dto.ArticleDTO;
import com.juliy.model.dto.ArticleFeaturedDTO;
import com.juliy.model.dto.ArticleTopDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.ArticleAdminVO;
import com.juliy.model.vo.ArticleInfoVO;
import com.juliy.model.vo.PageResult;
import com.juliy.model.vo.Result;
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
}