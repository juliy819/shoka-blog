package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 文章浏览量排行
 * @author juliy
 * @date 2023/4/13 17:43
 */
@Data
@Builder
@Schema(description = "文章浏览量排行")
public class ArticleRankVO {

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String articleTitle;

    /**
     * 浏览量
     */
    @Schema(description = "浏览量")
    private Integer viewCount;
}
