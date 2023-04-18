package com.juliy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.File;
import com.juliy.enums.FilePathEnum;
import com.juliy.mapper.FileMapper;
import com.juliy.strategy.context.UploadStrategyContext;
import com.juliy.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

import static com.juliy.constant.CommonConstant.FALSE;

/**
 * 文件服务接口实现类
 * @author juliy
 * @date 2023/4/8 13:36
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements com.juliy.service.FileService {

    private final UploadStrategyContext uploadStrategyContext;

    @Autowired
    public FileServiceImpl(UploadStrategyContext uploadStrategyContext) {
        this.uploadStrategyContext = uploadStrategyContext;
    }

    @Override
    public String saveFile(MultipartFile file, FilePathEnum filePath) {
        String url = uploadStrategyContext.executeUploadStrategy(file, filePath.getPath());
        try {
            // 获取文件md5值及后缀名
            String md5 = FileUtils.getMd5(file.getInputStream());
            String suffix = FileUtils.getSuffix(file);
            // 查找文件是否存在
            File existFile = this.getOne(
                    new LambdaQueryWrapper<File>()
                            .select(File::getId)
                            .eq(File::getFileName, md5)
                            .eq(File::getFilePath, filePath.getFilePath()));
            // 若不存在则保存文件信息
            if (Objects.isNull(existFile)) {
                File newFile = File.builder()
                        .fileUrl(url)
                        .fileName(md5)
                        .filePath(filePath.getFilePath())
                        .extendName(suffix)
                        .fileSize((int) file.getSize())
                        .isDir(FALSE)
                        .build();
                this.save(newFile);
            }
        } catch (IOException e) {
            log.error("文件信息保存失败\n", e);
        }
        return url;
    }
}