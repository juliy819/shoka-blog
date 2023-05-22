package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.Article;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章mapper
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
    Long countArticlesAdmin(@Param("condition") ConditionDTO condition);

    /**
     * 查询后台文章列表
     * @param current   页码
     * @param size      数量
     * @param condition 查询条件
     * @return 后台文章列表
     */
    List<ArticleAdminVO> selectArticlesAdmin(@Param("current") Long current, @Param("size") Long size,
                                             @Param("condition") ConditionDTO condition);

    /**
     * 根据id查询文章信息
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleInfoVO selectArticleInfoAdminById(@Param("articleId") Integer articleId);

    /**
     * 文章搜索
     * @param condition 搜索条件
     * @return 文章列表
     */
    List<ArticleSearchVO> searchArticles(@Param("condition") ConditionDTO condition);

    /**
     * 查询首页文章
     * @param current 页码
     * @param size    大小
     * @return 首页文章
     */
    List<ArticleHomeVO> selectArticles(@Param("current") Long current, @Param("size") Long size);

    /**
     * 根据id查询首页文章
     * @param articleId 文章id
     * @return 首页文章
     */
    ArticleVO selectArticleHomeById(Integer articleId);

    /**
     * 查询上一篇文章
     * @param articleId 文章id
     * @return 上一篇文章
     */
    ArticleNavigationVO selectLastArticle(Integer articleId);

    /**
     * 查询下一篇文章
     * @param articleId 文章id
     * @return 下一篇文章
     */
    ArticleNavigationVO selectNextArticle(Integer articleId);

    /**
     * 查询文章归档
     * @param current 页码
     * @param size    大小
     * @return 文章归档
     */
    List<ArchiveVO> selectArchives(@Param("current") Long current, @Param("size") Long size);

    /**
     * 查询文章统计
     * @return 文章统计
     */
    List<ArticleStatisticsVO> selectArticleStatistics();

    /**
     * 查询推荐文章
     * @return 推荐文章
     */
    List<ArticleFeaturedVO> selectArticlesFeatured();

    /**
     * 根据条件查询文章
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticleConditionVO> selectArticlesByCondition(@Param("current") Long current, @Param("size") Long size,
                                                       @Param("condition") ConditionDTO condition);

}
