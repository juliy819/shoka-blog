package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章VO
 * @author juliy
 * @date 2023/4/13 20:26
 */
@Data
@Schema(description = "文章VO")
public class ArticleVO {

    /**
     * 文章id
     */
    @Schema(description = "文章id")
    private Integer id;

    /**
     * 文章缩略图
     */
    @Schema(description = "文章缩略图")
    private String articleCover;

    /**
     * 文章标题
     */
    @Schema(description = "文章标题")
    private String articleTitle;

    /**
     * 文章内容
     */
    @Schema(description = "文章内容")
    private String articleContent;

    /**
     * 文章类型 (1原创 2转载 3翻译)
     */
    @Schema(description = "文章类型 (1原创 2转载 3翻译)")
    private Integer articleType;

    /**
     * 浏览量
     */
    @Schema(description = "浏览量")
    private Integer viewCount;

    /**
     * 点赞量
     */
    @Schema(description = "点赞量")
    private Integer likeCount;

    /**
     * 文章分类
     */
    @Schema(description = "文章分类")
    private CategoryOptionVO category;

    /**
     * 文章标签
     */
    @Schema(description = "文章标签")
    private List<TagOptionVO> tagList;

    /**
     * 上一篇文章
     */
    @Schema(description = "上一篇文章")
    private ArticleNavigationVO lastArticle;

    /**
     * 下一篇文章
     */
    @Schema(description = "下一篇文章")
    private ArticleNavigationVO nextArticle;

    /**
     * 发表时间
     */
    @Schema(description = "发表时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
