package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.juliy.annotation.OptLogger;
import com.juliy.entity.SiteConfig;
import com.juliy.model.vo.Result;
import com.juliy.service.SiteConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.juliy.constant.OptTypeConstant.UPDATE;
import static com.juliy.constant.OptTypeConstant.UPLOAD;

/**
 * 网站配置控制器
 * @author juliy
 * @date 2023/4/8 12:59
 */
@Tag(name = "网站配置模块")
@RequestMapping("/site")
@RestController
public class SiteConfigController {

    private final SiteConfigService siteConfigService;

    @Autowired
    public SiteConfigController(SiteConfigService siteConfigService) {
        this.siteConfigService = siteConfigService;
    }

    /**
     * 获取网站配置
     * @return 网站配置
     */
    @Operation(summary = "获取网站配置")
    @SaCheckPermission("site:list")
    @GetMapping("/list")
    public Result<SiteConfig> getSiteConfig() {
        return Result.success(siteConfigService.getSiteConfig());
    }

    /**
     * 修改网站配置
     * @param siteConfig 网站配置
     */
    @Operation(summary = "修改网站配置")
    @SaCheckPermission("site:update")
    @OptLogger(value = UPDATE)
    @PutMapping
    public Result<?> updateSiteConfig(@RequestBody SiteConfig siteConfig) {
        siteConfigService.updateSiteConfig(siteConfig);
        return Result.success();
    }

    /**
     * 上传网站配置图片
     * @param file 图片
     * @return 图片路径
     */
    @Operation(summary = "上传网站配置图片")
    @SaCheckPermission("web:site:upload")
    @OptLogger(value = UPLOAD)
    @PostMapping("/upload")
    public Result<String> saveSiteImg(@RequestParam("file") MultipartFile file) {
        return Result.success(siteConfigService.saveSiteImage(file));
    }

}
