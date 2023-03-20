package com.juliy.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 路由VO
 * @author juliy
 * @date 2023/3/13 21:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "路由")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVO {

    /** 菜单名称 */
    @Schema(description = "菜单名称")
    private String name;

    /** 路由地址 */
    @Schema(description = "路由地址")
    private String path;

    /** 菜单组件 */
    @Schema(description = "菜单组件")
    private String component;

    /** 总是显示根路由 */
    @Schema(description = "总是显示根路由")
    private Boolean alwaysShow;

    /** 重定向地址 */
    @Schema(description = "重定向地址")
    private String redirect;

    /** 其他信息 */
    @Schema(description = "其他信息")
    private MetaVO meta;

    /** 子菜单列表 */
    @Schema(description = "子菜单列表")
    private List<RouterVO> children;

}
