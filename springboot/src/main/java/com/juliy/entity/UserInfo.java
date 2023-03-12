package com.juliy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户信息
 * @author JuLiy
 * @date 2023/3/1 19:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user_info")
public class UserInfo {

    /** 用户信息id */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 邮箱 */
    private String email;

    /** 用户昵称 */
    private String nickname;

    /** 用户头像 */
    private String avatar;

    /** 用户简介 */
    private String intro;

    /** 个人网站 */
    private String website;

    /** 是否订阅 */
    private Integer isSubscribe;

    /** 是否禁用 */
    private Integer isDisable;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}