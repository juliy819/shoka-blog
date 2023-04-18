package com.juliy.controller;

import com.juliy.model.vo.BlogInfoAdminVO;
import com.juliy.model.vo.BlogInfoVO;
import com.juliy.model.vo.Result;
import com.juliy.service.BlogInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客信息控制器
 * @author juliy
 * @date 2023/4/8 14:33
 */
@Tag(name = "博客信息控制器")
@RestController
public class BlogInfoController {

    private final BlogInfoService blogInfoService;

    @Autowired
    public BlogInfoController(BlogInfoService blogInfoService) {
        this.blogInfoService = blogInfoService;
    }

    /**
     * 上传访客信息
     */
    @Operation(description = "上传访客信息")
    @GetMapping("/report")
    Result<?> report() {
        blogInfoService.report();
        return Result.success();
    }

    /**
     * 获取博客信息
     * @return 博客信息
     */
    @Operation(description = "获取博客信息")
    @GetMapping("/info")
    Result<BlogInfoVO> getBlogInfo() {
        return Result.success(blogInfoService.getBlogInfo());
    }

    /**
     * 获取博客后台信息
     * @return 博客后台信息
     */
    @Operation(description = "获取博客信息")
    @GetMapping("/admin/info")
    Result<BlogInfoAdminVO> getBlogInfoAdmin() {
        return Result.success(blogInfoService.getBlogInfoAdmin());
    }
}
