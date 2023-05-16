package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台评论VO
 * @author juliy
 * @date 2023/5/16 15:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "评论后台VO")
public class CommentAdminVO {
    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private String id;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 评论用户昵称
     */
    @Schema(description = "评论用户昵称")
    private String fromNickname;

    /**
     * 被回复用户昵称
     */
    @Schema(description = "被回复用户昵称")
    private String toNickname;

    /**
     * 文章标题
     */
    @Schema(description = "文章标题")
    private String articleTitle;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String commentContent;

    /**
     * 评论类型
     */
    @Schema(description = "评论类型")
    private Integer commentType;

    /**
     * 是否通过 (0否 1是)
     */
    @Schema(description = "是否通过 (0否 1是)")
    private Integer isCheck;

    /**
     * 发表时间
     */
    @Schema(description = "发表时间")
    private LocalDateTime createTime;
}
