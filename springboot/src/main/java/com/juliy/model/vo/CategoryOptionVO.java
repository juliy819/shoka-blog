package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分类选项VO
 * @author juliy
 * @date 2023/3/25 11:31
 */
@Data
@Schema(description = "分类选项VO")
public class CategoryOptionVO {

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
}
