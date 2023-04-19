package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 后台博客信息VO
 * @author juliy
 * @date 2023/4/8 14:39
 */
@Data
@Builder
@Schema(description = "后台博客信息VO")
public class BlogInfoAdminVO {

    /**
     * 访问量
     */
    @Schema(description = "访问量")
    private Long viewCount;

    /**
     * 留言量
     */
    @Schema(description = "留言量")
    private Long messageCount;

    /**
     * 用户量
     */
    @Schema(description = "用户量")
    private Long userCount;

    /**
     * 文章量
     */
    @Schema(description = "文章量")
    private Long articleCount;

    /**
     * 分类统计
     */
    @Schema(description = "分类统计")
    private List<CategoryVO> categoryList;

    /**
     * 标签列表
     */
    @Schema(description = "标签列表")
    private List<TagVO> tagList;

    /**
     * 文章贡献统计
     */
    @Schema(description = "文章贡献统计")
    private List<ArticleStatisticsVO> articleStatisticsList;

    /**
     * 文章浏览量排行
     */
    @Schema(description = "文章浏览量排行")
    private List<ArticleRankVO> articleRankList;

    /**
     * 一周访问量
     */
    @Schema(description = "一周访问量")
    private List<UserViewVO> userViewList;

}
