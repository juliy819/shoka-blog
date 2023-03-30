package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章标签mapper
 * @author juliy
 * @date 2023/3/27 12:04
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    /**
     * 批量保存文章标签
     * @param articleId 文章id
     * @param tagIdList 标签id列表
     */
    void insertBatchArticleTag(@Param("articleId") Integer articleId, @Param("tagIdList") List<Integer> tagIdList);
}
