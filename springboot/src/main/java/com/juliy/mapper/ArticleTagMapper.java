package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签mapper
 * @author juliy
 * @date 2023/3/27 12:04
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
}
