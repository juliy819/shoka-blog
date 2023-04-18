package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.File;
import com.juliy.enums.FilePathEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务接口
 * @author juliy
 * @date 2023/4/8 13:36
 */
public interface FileService extends IService<File> {

    /**
     * 保存文件
     * @param file     文件
     * @param filePath 文件路径
     * @return 文件url
     */
    String saveFile(MultipartFile file, FilePathEnum filePath);

}