package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * 注册信息
 * @author juliy
 * @date 2023/3/1 19:29
 */
@Data
@Tag(name = "注册信息")
public class RegisterDTO {

    /** 用户名 */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "用户名", type = "String")
    private String username;

    /** 密码 */
    @Size(min = 6, message = "密码不能少于6位")
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", type = "String")
    private String password;

    /** 邮箱验证码 */
    @NotBlank(message = "验证码不能为空")
    @Schema(description = "邮箱验证码", type = "String")
    private String code;
}
