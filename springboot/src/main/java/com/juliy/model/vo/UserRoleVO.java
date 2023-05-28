package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户角色VO
 * @author juliy
 * @date 2023/5/28 12:50
 */
@Data
@Schema(description = "用户角色VO")
public class UserRoleVO {

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private Integer id;

    /**
     * 角色名
     */
    @Schema(description = "角色名")
    private String roleName;
}
