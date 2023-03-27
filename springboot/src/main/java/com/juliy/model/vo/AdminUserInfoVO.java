package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 后台用户信息
 * @author juliy
 * @date 2023/3/15 22:32
 */
@Data
@Builder
@Schema(description = "后台用户信息")
public class AdminUserInfoVO {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Integer id;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 角色
     */
    @Schema(description = "角色")
    private List<String> roleList;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    private List<String> permissionList;

}
