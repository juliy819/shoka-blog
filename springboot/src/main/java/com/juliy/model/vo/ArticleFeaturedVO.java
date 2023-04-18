package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 推荐文章VO
 * @author juliy
 * @date 2023/4/13 20:39
 */
@Data
@Schema(description = "推荐文章VO")
public class ArticleFeaturedVO {

    /**
     * 文章id
     */
    @Schema(description = "文章id")
    private Integer id;

    /**
     * 文章标题
     */
    @Schema(description = "文章标题")
    private String articleTitle;

    /**
     * 文章缩略图
     */
    @Schema(description = "文章缩略图")
    private String articleCover;

    /**
     * 发布时间
     */
    @Schema(description = "发布时间")
    private LocalDateTime createTime;
}
