package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 标签VO
 * @author juliy
 * @date 2023/3/25 10:05
 */
@Data
@Schema(description = "标签VO")
public class TagVO {

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

    /**
     * 文章数量
     */
    @Schema(description = "文章数量")
    private Integer articleCount;
}
