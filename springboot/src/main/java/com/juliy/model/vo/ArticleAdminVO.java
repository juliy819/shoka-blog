package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章后台VO
 * @author juliy
 * @date 2023/3/25 10:04
 */
@Data
@Schema(description = "文章后台VO")
public class ArticleAdminVO {

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
     * 文章类型 (1原创 2转载 3翻译)
     */
    @Schema(description = "文章类型 (1原创 2转载 3翻译)")
    private Integer articleType;

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
     * 是否删除 (0否 1是)
     */
    @Schema(description = "是否删除 (0否 1是)")
    private Integer isDelete;

    /**
     * 状态 (1公开 2私密 3草稿)
     */
    @Schema(description = "状态 (1公开 2私密 3草稿)")
    private Integer status;

    /**
     * 点赞量
     */
    @Schema(description = "点赞量")
    private Integer likeCount;

    /**
     * 浏览量
     */
    @Schema(description = "浏览量")
    private Integer viewCount;

    /**
     * 文章分类名
     */
    @Schema(description = "文章分类名")
    private String categoryName;

    /**
     * 文章标签
     */
    @Schema(description = "文章标签")
    private List<TagOptionVO> tagVOList;

    /**
     * 发表时间
     */
    @Schema(description = "发表时间")
    private LocalDateTime createTime;
}
