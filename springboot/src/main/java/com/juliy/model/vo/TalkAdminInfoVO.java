package com.juliy.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 后台说说信息VO
 * @author juliy
 * @date 2023/5/23 11:51
 */
@Data
@Schema(description = "后台说说信息VO")
public class TalkAdminInfoVO {

    /**
     * 说说id
     */
    @Schema(description = "说说id")
    private Integer id;

    /**
     * 说说内容
     */
    @Schema(description = "说说内容")
    private String talkContent;

    /**
     * 图片
     */
    @JsonIgnore
    @Schema(description = "图片")
    private String images;

    /**
     * 图片列表
     */
    @Schema(description = "图片列表")
    private List<String> imgList;

    /**
     * 是否置顶 (0否 1是)
     */
    @Schema(description = "是否置顶 (0否 1是)")
    private Integer isTop;

    /**
     * 说说状态 (1公开 2私密)
     */
    @Schema(description = "说说状态 (1公开 2私密)")
    private Integer status;


}
