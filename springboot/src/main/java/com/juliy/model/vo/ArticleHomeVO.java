package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 首页文章VO
 * @author juliy
 * @date 2023/4/13 20:35
 */
@Data
@Schema(description = "首页文章VO")
public class ArticleHomeVO {

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
     * 是否置顶 (0否 1是)
     */
    @Schema(description = "是否置顶 (0否 1是)")
    private Integer isTop;

    /**
     * 发表时间
     */
    @Schema(description = "发表时间")
    private LocalDateTime createTime;
}
