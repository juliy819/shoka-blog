package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色VO
 * @author juliy
 * @date 2023/5/28 12:58
 */
@Data
@Schema(description = "角色VO")
public class RoleVO {

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

    /**
     * 角色描述
     */
    @Schema(description = "角色描述")
    private String roleDesc;

    /**
     * 是否禁用 (0否 1是)
     */
    @Schema(description = "是否禁用 (0否 1是)")
    private Integer isDisable;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
