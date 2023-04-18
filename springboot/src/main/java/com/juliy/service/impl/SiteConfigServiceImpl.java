package com.juliy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.SiteConfig;
import com.juliy.enums.FilePathEnum;
import com.juliy.mapper.SiteConfigMapper;
import com.juliy.service.FileService;
import com.juliy.service.RedisService;
import com.juliy.service.SiteConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static com.juliy.constant.RedisConstant.SITE_CONFIG;

/**
 * 网站配置服务接口实现类
 * @author juliy
 * @date 2023/4/8 13:01
 */
@Slf4j
@Service
public class SiteConfigServiceImpl extends ServiceImpl<SiteConfigMapper, SiteConfig> implements SiteConfigService {

    private final RedisService redisService;

    private final FileService fileService;

    @Autowired
    public SiteConfigServiceImpl(RedisService redisService,
                                 FileService fileService) {
        this.redisService = redisService;
        this.fileService = fileService;
    }

    @Override
    public SiteConfig getSiteConfig() {
        // 读取配置信息并存入redis，后续直接从redis读取
        SiteConfig siteConfig = redisService.getObject(SITE_CONFIG);
        if (Objects.isNull(siteConfig)) {
            siteConfig = this.getById(1);
            redisService.setObject(SITE_CONFIG, siteConfig);
        }
        return siteConfig;
    }

    @Override
    public void updateSiteConfig(SiteConfig siteConfig) {
        this.updateById(siteConfig);
        redisService.setObject(SITE_CONFIG, siteConfig);
    }

    @Override
    public String saveSiteImage(MultipartFile file) {
        return fileService.saveFile(file, FilePathEnum.CONFIG);
    }
}
