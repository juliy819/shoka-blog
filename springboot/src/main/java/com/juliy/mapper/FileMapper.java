package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.File;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件mapper
 * @author juliy
 * @date 2023/3/29 14:23
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {
}
