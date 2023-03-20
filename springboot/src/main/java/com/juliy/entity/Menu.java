package com.juliy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 * @author juliy
 * @date 2023/3/13 12:06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_menu")
public class Menu {

    /**
     * 菜单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父菜单id
     */
    private Integer parentId;

    /**
     * 类型（M目录 C菜单 B按钮）
     */
    private String menuType;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单组件
     */
    private String component;

    /**
     * 权限标识
     */
    private String perms;

    /** 是否隐藏 (0否 1是) */
    private Integer isHidden;

    /** 是否禁用 (0否 1是) */
    private Integer isDisable;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 子菜单列表(不在表字段中)
     */
    private List<Menu> children = new ArrayList<>();

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
