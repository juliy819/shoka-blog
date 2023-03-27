package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.Article;
import com.juliy.model.vo.ArticleAdminVO;
import com.juliy.model.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章 mapper
 * @author juliy
 * @date 2023/3/25 10:00
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询后台文章数量
     * @param condition 查询条件
     * @return 文章数量
     */
    Long countArticleBackVO(ConditionVO condition);

    /**
     * 查询后台文章列表
     * @param limit     页码
     * @param size      数量
     * @param condition 查询条件
     * @return 后台文章列表
     */
    List<ArticleAdminVO> selectArticleBackVO(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionVO condition);

}
