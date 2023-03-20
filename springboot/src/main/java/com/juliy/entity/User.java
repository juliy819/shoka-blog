package com.juliy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户
 * @author juliy
 * @date 2023/3/1 13:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class User {

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 个人网站
     */
    private String webSite;

    /**
     * 个人介绍
     */
    private String intro;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 登录ip
     */
    private String ipAddress;

    /**
     * 登录地址
     */
    private String ipSource;

    /**
     * 登录方式 (1邮箱 2QQ 3Gitee 4Github)
     */
    private Integer loginType;

    /**
     * 是否禁用 (0否 1是)
     */
    private Integer isDisable;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
