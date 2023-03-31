package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单
 * @author juliy
 * @date 2023/3/13 11:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {

    /**
     * 菜单id
     */
    @Schema(description = "菜单id")
    private Integer id;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String name;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    private String path;

    /**
     * 菜单组件
     */
    @Schema(description = "菜单组件")
    private String component;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer orderNum;

    /**
     * 父菜单id
     */
    @Schema(description = "父菜单id")
    private Integer parentId;

    /**
     * 子菜单列表
     */
    @Schema(description = "子菜单列表")
    private List<MenuDTO> children;

    /**
     * 是否隐藏 (0否 1是)
     */
    @Schema(description = "是否隐藏")
    private Integer isHidden;

}
