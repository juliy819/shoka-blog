package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 标签后台VO
 * @author juliy
 * @date 2023/3/25 10:08
 */
@Data
@Schema(description = "标签后台VO")
public class TagAdminVO {

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

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
