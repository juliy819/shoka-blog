package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 文章上下篇
 * @author juliy
 * @date 2023/4/13 20:37
 */
@Data
@Schema(description = "文章上下篇")
public class ArticleNavigationVO {

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
}
