package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 文章推荐DTO
 * @author juliy
 * @date 2023/3/29 20:30
 */
@Data
@Schema(description = "文章推荐DTO")
public class ArticleFeaturedDTO {

    /**
     * 文章id
     */
    @NotNull(message = "文章id不能为空")
    @Schema(description = "文章id")
    private Integer articleId;

    /**
     * 是否推荐 (0否 1是)
     */
    @NotNull(message = "推荐状态不能为空")
    @Schema(description = "是否推荐 (0否 1是)")
    private Integer isFeatured;
}
