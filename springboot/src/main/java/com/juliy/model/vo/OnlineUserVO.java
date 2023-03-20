package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 在线用户
 * @author juliy
 * @date 2023/3/15 16:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "在线用户VO")
public class OnlineUserVO {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Integer id;

    /**
     * 在线token
     */
    @Schema(description = "在线token")
    private String token;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * ip地址
     */
    @Schema(description = "ip地址")
    private String ipAddress;

    /**
     * ip来源
     */
    @Schema(description = "ip来源")
    private String ipSource;

    /**
     * 操作系统
     */
    @Schema(description = "操作系统")
    private String os;

    /**
     * 浏览器
     */
    @Schema(description = "浏览器")
    private String browser;

    /**
     * 登录时间
     */
    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

}
