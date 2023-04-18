package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.SiteConfig;
import org.springframework.web.multipart.MultipartFile;

/**
 * 网站配置服务接口
 * @author juliy
 * @date 2023/4/8 13:01
 */
public interface SiteConfigService extends IService<SiteConfig> {

    /**
     * 获取网站配置
     * @return 网站配置
     */
    SiteConfig getSiteConfig();

    /**
     * 更新网站配置
     * @param siteConfig 网站配置
     */
    void updateSiteConfig(SiteConfig siteConfig);

    /**
     * 上传网站配置图片
     * @param file 图片
     * @return 图片url
     */
    String saveSiteImage(MultipartFile file);
}
