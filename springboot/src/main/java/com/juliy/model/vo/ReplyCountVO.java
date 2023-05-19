package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 回复数VO
 * @author juliy
 * @date 2023/5/18 22:52
 */
@Data
@Schema(description = "回复数VO")
public class ReplyCountVO {

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private Integer commentId;

    /**
     * 回复数
     */
    @Schema(description = "回复数")
    private Integer replyCount;

}
