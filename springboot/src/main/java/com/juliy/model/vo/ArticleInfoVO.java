package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 文章信息VO
 * @author juliy
 * @date 2023/3/29 20:18
 */
@Data
@Schema(description = "文章信息VO")
public class ArticleInfoVO {

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
     * 文章内容
     */
    @Schema(description = "文章内容")
    private String articleContent;

    /**
     * 文章分类id
     */
    @Schema(description = "文章分类id")
    private Integer categoryId;

    /**
     * 文章分类名
     */
    @Schema(description = "文章分类名")
    private String categoryName;

    /**
     * 文章标签名
     */
    @Schema(description = "文章标签名")
    private List<String> tagNameList;

    /**
     * 文章类型 (1原创 2转载 3翻译)
     */
    @Schema(description = "文章类型 (1原创 2转载 3翻译)")
    private Integer articleType;

    /**
     * 文章缩略图
     */
    @Schema(description = "文章缩略图")
    private String articleCover;

    /**
     * 是否置顶 (0否 1是)
     */
    @Schema(description = "是否置顶 (0否 1是)")
    private Integer isTop;

    /**
     * 是否推荐 (0否 1是)
     */
    @Schema(description = "是否推荐 (0否 1是)")
    private Integer isRecommend;

    /**
     * 状态 (1公开 2私密 3草稿)
     */
    @Schema(description = "状态 (1公开 2私密 3草稿)")
    private Integer status;
}
