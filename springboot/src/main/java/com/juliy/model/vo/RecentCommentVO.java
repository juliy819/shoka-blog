package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 最近评论VO
 * @author juliy
 * @date 2023/5/18 22:42
 */
@Data
@Schema(description = "最新评论")
public class RecentCommentVO {

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private Integer id;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @Schema(description = "用户昵称")
    private String avatar;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String commentContent;

    /**
     * 评论时间
     */
    @Schema(description = "评论时间")
    private LocalDateTime createTime;
    
}
