package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章搜索VO
 * @author juliy
 * @date 2023/4/13 20:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文章搜索VO")
public class ArticleSearchVO {

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
     * 是否删除
     */
    @Schema(description = "是否删除")
    private Integer isDelete;

    /**
     * 文章状态
     */
    @Schema(description = "文章状态")
    private Integer status;
}
