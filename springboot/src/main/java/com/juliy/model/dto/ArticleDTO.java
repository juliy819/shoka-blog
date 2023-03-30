package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 文章DTO
 * @author juliy
 * @date 2023/3/29 20:02
 */
@Data
@Schema(description = "文章DTO")
public class ArticleDTO {

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
    @NotBlank(message = "文章标题不能为空")
    @Schema(description = "文章标题")
    private String articleTitle;

    /**
     * 文章内容
     */
    @NotBlank(message = "文章内容不能为空")
    @Schema(description = "文章内容")
    private String articleContent;

    /**
     * 文章类型 (1原创 2转载 3翻译)
     */
    @Schema(description = "文章类型 (1原创 2转载 3翻译)")
    private Integer articleType;

    /**
     * 分类名
     */
    @Schema(description = "分类名")
    private String categoryName;

    /**
     * 标签名
     */
    @Schema(description = "标签名")
    private List<String> tagNameList;

    /**
     * 是否置顶 (0否 1是)
     */
    @Schema(description = "是否置顶 (0否 1是)")
    private Integer isTop;

    /**
     * 是否推荐 (0否 1是)
     */
    @Schema(description = "是否推荐 (0否 1是)")
    private Integer isFeatured;

    /**
     * 状态 (1公开 2私密 3草稿)
     */
    @Schema(description = "状态 (1公开 2私密 3草稿)")
    private Integer status;
}
