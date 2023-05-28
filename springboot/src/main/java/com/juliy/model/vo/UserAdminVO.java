package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户后台VO
 * @author juliy
 * @date 2023/5/28 12:49
 */
@Data
@Schema(description = "用户后台VO")
public class UserAdminVO {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Integer id;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 登录ip
     */
    @Schema(description = "登录ip")
    private String ipAddress;

    /**
     * 登录地址
     */
    @Schema(description = "登录地址")
    private String ipSource;

    /**
     * 登录方式 (1邮箱 2QQ 3Gitee 4Github)
     */
    @Schema(description = "登录方式 (1邮箱 2QQ 3Gitee 4Github)")
    private Integer loginType;

    /**
     * 用户角色
     */
    @Schema(description = "用户角色")
    private List<UserRoleVO> roleList;

    /**
     * 是否禁用 (0否 1是)
     */
    @Schema(description = "是否禁用 (0否 1是)")
    private Integer isDisable;

    /**
     * 登录时间
     */
    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
