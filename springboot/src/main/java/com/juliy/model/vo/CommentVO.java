package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论VO
 * @author juliy
 * @date 2023/5/18 22:43
 */
@Data
@Schema(description = "评论VO")
public class CommentVO {

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private Integer id;

    /**
     * 评论用户id
     */
    @Schema(description = "评论用户id")
    private Integer fromUid;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String fromNickname;

    /**
     * 个人网站
     */
    @Schema(description = "个人网站")
    private String webSite;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String commentContent;

    /**
     * 点赞数
     */
    @Schema(description = "点赞数")
    private Integer likeCount;

    /**
     * 回复量
     */
    @Schema(description = "回复量")
    private Integer replyCount;

    /**
     * 回复列表
     */
    @Schema(description = "回复列表")
    private List<ReplyVO> replyList;

    /**
     * 评论时间
     */
    @Schema(description = "评论时间")
    private LocalDateTime createTime;

}
