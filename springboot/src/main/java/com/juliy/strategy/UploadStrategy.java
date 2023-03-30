package com.juliy.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传策略
 * @author juliy
 * @date 2023/3/29 12:41
 */
public interface UploadStrategy {

    /**
     * 上传文件
     * @param file 文件
     * @param path 上传路径
     * @return 文件地址
     */
    String uploadFile(MultipartFile file, String path);
}
