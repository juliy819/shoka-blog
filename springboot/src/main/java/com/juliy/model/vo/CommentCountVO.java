package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 评论数量VO
 * @author juliy
 * @date 2023/5/18 22:50
 */
@Data
@Schema(description = "评论数量VO")
public class CommentCountVO {

    /**
     * 类型id
     */
    @Schema(description = "类型id")
    private Integer id;

    /**
     * 评论数量
     */
    @Schema(description = "评论数量")
    private Integer commentCount;

}
