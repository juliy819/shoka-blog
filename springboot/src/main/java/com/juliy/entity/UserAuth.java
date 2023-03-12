package com.juliy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户认证
 * @author JuLiy
 * @date 2023/3/1 13:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_auth")
public class UserAuth {

    /** 用户认证id */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 用户信息id */
    private Integer userInfoId;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 登录类型 */
    private Integer loginType;

    /** 登录ip */
    private String ipAddress;

    /** ip来源 */
    private String ipSource;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /** 上次登录时间 */
    private LocalDateTime lastLoginTime;
}
