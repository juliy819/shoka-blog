package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * 登录信息
 * @author JuLiy
 * @date 2023/3/1 13:25
 */
@Data
@Tag(name = "登录信息")
public class LoginDTO {

    /** 用户名 */
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", type = "String")
    private String username;

    /** 密码 */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码不能少于6位")
    @Schema(description = "密码", type = "String")
    private String password;
}
