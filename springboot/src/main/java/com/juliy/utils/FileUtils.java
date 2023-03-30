package com.juliy.utils;

import cn.hutool.core.io.file.FileNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 文件工具类
 * @author juliy
 * @date 2023/3/29 12:47
 */
@Slf4j
public class FileUtils {

    /**
     * 获取文件md5值
     * @param is 文件输入流
     * @return {@link String} 文件md5值
     */
    public static String getMd5(InputStream is) {
        String md5 = null;
        try {
            md5 = DigestUtils.md5DigestAsHex(is);
        } catch (IOException e) {
            log.error("获取文件md5值失败\n", e);
        }
        return md5;
    }

    /**
     * 获取文件的后缀名
     * @param file 表单文件
     * @return 后缀名
     */
    public static String getSuffix(MultipartFile file) {
        String extension = FileNameUtil.getSuffix(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

    /**
     * 转换file
     * @param file 文件
     * @return {@link File} 临时文件
     */
    public static File multipartFileToFile(MultipartFile file) {
        File tempFile = null;
        try {
            // 获取文件md5值
            String md5 = getMd5(file.getInputStream());
            // 获取文件扩展名
            String extName = getSuffix(file);
            // 重新生成文件名
            String fileName = md5 + extName;
            // 创建临时文件
            tempFile = File.createTempFile(fileName, extName);
            file.transferTo(tempFile);
        } catch (IOException e) {
            log.error("文件转换失败\n", e);
        }
        return tempFile;
    }

    /**
     * 创建目录
     * @param file 文件
     * @return 是否创建成功
     */
    public static boolean mkdir(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return false;
        }
        return file.mkdirs();
    }

}
