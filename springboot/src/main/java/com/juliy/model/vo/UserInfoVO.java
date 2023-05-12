package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * 用户信息VO
 * @author juliy
 * @date 2023/5/9 9:13
 */
@Data
@Builder
@Schema(description = "用户信息VO")
public class UserInfoVO {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Integer id;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    private String email;

    /**
     * 个人网站
     */
    @Schema(description = "个人网站")
    private String webSite;

    /**
     * 个人简介
     */
    @Schema(description = "个人简介")
    private String intro;

    /**
     * 点赞文章集合
     */
    @Schema(description = "点赞文章集合")
    private Set<Object> articleLikeSet;

    /**
     * 点赞评论集合
     */
    @Schema(description = "点赞评论集合")
    private Set<Object> commentLikeSet;

    /**
     * 点赞说说集合
     */
    @Schema(description = "点赞说说集合")
    private Set<Object> talkLikeSet;

    /**
     * 登录类型
     */
    @Schema(description = "登录类型")
    private Integer loginType;
}
