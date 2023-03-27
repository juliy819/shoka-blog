package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 标签选项VO
 * @author juliy
 * @date 2023/3/27 10:30
 */
@Data
@Schema(description = "标签选项VO")
public class TagOptionVO {

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Integer id;

    /**
     * 标签名
     */
    @Schema(description = "标签名")
    private String tagName;
}
