package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 回复评论VO
 * @author juliy
 * @date 2023/5/18 22:40
 */
@Data
@Schema(description = "回复评论VO")
public class ReplyVO {

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private Integer id;

    /**
     * 父级评论id
     */
    @Schema(description = "父级评论id")
    private Integer parentId;

    /**
     * 评论用户id
     */
    @Schema(description = "评论用户id")
    private Integer fromUid;

    /**
     * 被评论用户id
     */
    @Schema(description = "被评论用户id")
    private Integer toUid;

    /**
     * 评论用户昵称
     */
    @Schema(description = "评论用户昵称")
    private String fromNickname;

    /**
     * 个人网站
     */
    @Schema(description = "个人网站")
    private String webSite;

    /**
     * 被评论用户昵称
     */
    @Schema(description = "被评论用户昵称")
    private String toNickname;

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
     * 评论时间
     */
    @Schema(description = "评论时间")
    private LocalDateTime createTime;

}
