package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.juliy.model.dto.LoginDTO;
import com.juliy.model.dto.RegisterDTO;
import com.juliy.model.vo.Result;
import com.juliy.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 * @author JuLiy
 * @date 2023/3/1 13:23
 */
@Tag(name = "登录模块")
@RestController
public class LoginController {

    @Autowired
    private UserAuthService userAuthService;

    //@Autowired
    //public LoginController(LoginService loginService) {
    //    this.loginService = loginService;
    //}


    /**
     * 用户登录
     * @param loginInfo 登录参数
     * @return {@link String} Token
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody LoginDTO loginInfo) {
        return Result.success(userAuthService.login(loginInfo));
    }

    /**
     * 用户退出
     */
    @SaCheckLogin
    @Operation(summary = "用户退出")
    @GetMapping("/logout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.success();
    }

    @Operation(summary = "用户注册")
    @PostMapping("/users/register")
    public Result<?> register(@Valid @RequestBody RegisterDTO registerInfo) {
        userAuthService.register(registerInfo);
        return Result.success();
    }


}
