package com.juliy.strategy.context;

import com.juliy.strategy.UploadStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.juliy.enums.UploadModeEnum.getStrategy;

/**
 * 上传策略上下文
 * @author juliy
 * @date 2023/3/29 12:42
 */
@Service
public class UploadStrategyContext {

    /**
     * 实现类
     */
    private final Map<String, UploadStrategy> uploadStrategyMap;
    /**
     * 上传模式
     */
    @Value("${upload.strategy}")
    private String uploadStrategy;

    @Autowired
    public UploadStrategyContext(Map<String, UploadStrategy> uploadStrategyMap) {
        this.uploadStrategyMap = uploadStrategyMap;
    }

    /**
     * 上传文件
     * @param file 文件
     * @param path 路径
     * @return {@link String} 文件地址
     */
    public String executeUploadStrategy(MultipartFile file, String path) {
        return uploadStrategyMap.get(getStrategy(uploadStrategy)).uploadFile(file, path);
    }

}
