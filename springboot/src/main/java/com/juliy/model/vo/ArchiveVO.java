package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章归档VO
 * @author juliy
 * @date 2023/4/13 20:38
 */
@Data
@Schema(description = "文章归档VO")
public class ArchiveVO {

    /**
     * 文章id
     */
    @Schema(description = "文章id")
    private Integer id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String articleTitle;

    /**
     * 文章缩略图
     */
    @Schema(description = "文章缩略图")
    private String articleCover;

    /**
     * 发表时间
     */
    @Schema(description = "发表时间")
    private LocalDateTime createTime;
}
