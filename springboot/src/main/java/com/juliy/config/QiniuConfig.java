package com.juliy.config;

import com.juliy.exception.ServiceException;
import com.qiniu.storage.Region;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云配置类
 * @author juliy
 * @date 2023/3/29 13:14
 */
@Data
@Configuration
public class QiniuConfig {
    @Value("${upload.qiniu.accessKey}")
    private String AccessKey;

    @Value("${upload.qiniu.secretKey}")
    private String SecretKey;

    @Value("${upload.qiniu.bucketName}")
    private String BucketName;

    @Value("${upload.qiniu.region}")
    private String region;

    @Value("${upload.qiniu.url}")
    private String url;

    /**
     * 获取budget区域
     * @return budget区域
     */
    public Region getRegion() {
        switch (region) {
            case "huadong" -> {
                return Region.huadong();
            }
            case "huadong2" -> {
                return Region.huadongZheJiang2();
            }
            case "huabei" -> {
                return Region.huabei();
            }
            case "huanan" -> {
                return Region.huanan();
            }
            case "beimei" -> {
                return Region.beimei();
            }
            case "xinjiapo" -> {
                return Region.xinjiapo();
            }
            default -> throw new ServiceException("存储区域配置有误");
        }
    }
}
