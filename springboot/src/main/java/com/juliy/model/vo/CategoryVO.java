package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 分类VO
 * @author juliy
 * @date 2023/3/25 10:39
 */
@Data
@Schema(description = "分类VO")
public class CategoryVO {
    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Integer id;

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    @Schema(description = "分类名")
    private String categoryName;
}
