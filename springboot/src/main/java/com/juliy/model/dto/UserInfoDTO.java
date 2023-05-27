package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户信息DTO
 * @author juliy
 * @date 2023/3/11 20:44
 */
@Data
@Schema(description = "用户信息DTO")
public class UserInfoDTO {
    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 个人网站
     */
    @Schema(description = "个人网站")
    private String webSite;

    /**
     * 个人简介
     */
    @Schema(description = "个人简介")
    private String intro;

}
