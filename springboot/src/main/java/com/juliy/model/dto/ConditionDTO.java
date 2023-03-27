package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询条件
 * @author juliy
 * @date 2023/3/25 10:16
 */
@Data
@Schema(description = "查询条件")
public class ConditionDTO {

    /**
     * 页码
     */
    @Schema(description = "页码")
    private Long current;

    /**
     * 条数
     */
    @Schema(description = "条数")
    private Long size;

    /**
     * 搜索内容
     */
    @Schema(description = "搜索内容")
    private String keywords;

    /**
     * 是否禁用 (0否 1是)
     */
    @Schema(description = "是否禁用 (0否 1是)")
    private Integer isDisable;

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Integer categoryId;

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Integer tagId;

    /**
     * 类型id
     */
    @Schema(description = "类型id")
    private Integer typeId;

    /**
     * 评论主题类型
     */
    @Schema(description = "评论主题类型")
    private Integer commentType;

    /**
     * 文章类型 (1原创 2转载 3翻译)
     */
    @Schema(description = "文章类型 (1原创 2转载 3翻译)")
    private Integer articleType;

    /**
     * 登录类型
     */
    @Schema(description = "登录类型")
    private Integer loginType;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private Integer userType;

    /**
     * 操作模块
     */
    @Schema(description = "操作模块")
    private String optModule;

    /**
     * 是否删除 (0否 1是)
     */
    @Schema(description = "是否删除 (0否 1是)")
    private Integer isDelete;

    /**
     * 是否通过 (0否 1是)
     */
    @Schema(description = "是否通过 (0否 1是)")
    private Integer isCheck;

    /**
     * 文章状态 (1公开 2私密 3草稿)
     * 任务状态 (0运行 1暂停)
     */
    @Schema(description = "状态")
    private Integer status;

    /**
     * 任务组名
     */
    @Schema(description = "任务组名")
    private String taskGroup;

    /**
     * 调用目标
     */
    @Schema(description = "调用目标")
    private String invokeTarget;

    /**
     * 文件路径
     */
    @Schema(description = "文件路径")
    private String filePath;
}
