package com.juliy.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 说说VO
 * @author juliy
 * @date 2023/5/23 11:49
 */
@Data
@Schema(description = "说说VO")
public class TalkVO {
    /**
     * 说说id
     */
    @Schema(description = "说说id")
    private Integer id;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

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
     * 点赞量
     */
    @Schema(description = "点赞量")
    private Integer likeCount;

    /**
     * 评论量
     */
    @Schema(description = "评论量")
    private Integer commentCount;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
