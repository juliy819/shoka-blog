package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 后台留言VO
 * @author juliy
 * @date 2023/5/27 15:13
 */
@Data
@Schema(description = "后台留言VO")
public class MessageAdminVO {

    /**
     * 留言id
     */
    @Schema(description = "留言id")
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
     * 留言内容
     */
    @Schema(description = "留言内容")
    private String messageContent;

    /**
     * 用户ip
     */
    @Schema(description = "用户ip")
    private String ipAddress;

    /**
     * 用户地址
     */
    @Schema(description = "用户地址")
    private String ipSource;

    /**
     * 是否通过 (0否 1是)
     */
    @Schema(description = "是否通过 (0否 1是)")
    private Integer isCheck;

    /**
     * 留言时间
     */
    @Schema(description = "留言时间")
    private LocalDateTime createTime;
}
