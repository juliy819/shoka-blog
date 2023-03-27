package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分类后台VO
 * @author juliy
 * @date 2023/3/25 10:39
 */
@Data
@Schema(description = "分类后台VO")
public class CategoryAdminVO {
    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Integer id;

    /**
     * 分类名
     */
    @Schema(description = "分类名")
    private String categoryName;

    /**
     * 文章数量
     */
    @Schema(description = "文章数量")
    private Integer articleCount;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
