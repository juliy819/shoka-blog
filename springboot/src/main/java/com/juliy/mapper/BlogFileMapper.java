package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.BlogFile;
import org.apache.ibatis.annotations.Mapper;

/**
 * 博客文件mapper
 * @author juliy
 * @date 2023/3/29 14:23
 */
@Mapper
public interface BlogFileMapper extends BaseMapper<BlogFile> {
}
