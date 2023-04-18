package com.juliy.strategy.impl;

import com.juliy.config.QiniuConfig;
import com.juliy.exception.ServiceException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * 七牛云上传实现类
 * @author juliy
 * @date 2023/3/29 12:43
 */
@Slf4j
@Service("QiniuUploadStrategyImpl")
public class QiniuUploadStrategyImpl extends AbstractUploadStrategyImpl {

    private final QiniuConfig qiniuConfig;

    private UploadManager uploadManager;
    private String token;
    private Auth auth;
    private BucketManager bucketManager;


    @Autowired
    public QiniuUploadStrategyImpl(QiniuConfig qiniuConfig) {
        this.qiniuConfig = qiniuConfig;
        init();
    }

    private void init() {
        uploadManager = new UploadManager(new Configuration(qiniuConfig.getRegion()));
        auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        bucketManager = new BucketManager(auth, new Configuration(qiniuConfig.getRegion()));
        token = auth.uploadToken(qiniuConfig.getBucketName());
    }


    @Override
    public Boolean exists(String filePath) {
        return false;
    }

    @Override
    public void upload(String path, String fileName, InputStream inputStream) throws IOException {
        Response res = uploadManager.put(inputStream, path + fileName, token, null, null);
        // 判断是否需要重传，最大重传次数为3
        int retry = 0;
        while (res.needRetry() && retry < 3) {
            res = uploadManager.put(inputStream, path + fileName, token, null, null);
            retry++;
        }
        if (!res.isOK()) {
            throw new ServiceException("上传至七牛云失败:" + res);
        }

    }

    @Override
    public String getFileAccessUrl(String filePath) {
        return qiniuConfig.getUrl() + filePath;
    }
}
