package com.juliy.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.juliy.annotation.AccessLimit;
import com.juliy.entity.Menu;
import com.juliy.model.dto.LoginDTO;
import com.juliy.model.dto.RegisterDTO;
import com.juliy.model.vo.AdminUserInfoVO;
import com.juliy.model.vo.Result;
import com.juliy.model.vo.RouterVO;
import com.juliy.model.vo.UserInfoVO;
import com.juliy.service.LoginService;
import com.juliy.service.MenuService;
import com.juliy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 登录控制器
 * @author juliy
 * @date 2023/3/1 13:23
 */
@Tag(name = "登录模块")
@RestController
public class LoginController {

    private final LoginService loginService;

    private final MenuService menuService;

    private final UserService userService;

    @Autowired
    public LoginController(LoginService loginService,
                           MenuService menuService,
                           UserService userService) {
        this.loginService = loginService;
        this.menuService = menuService;
        this.userService = userService;
    }


    /**
     * 用户登录
     * @param loginDTO 登录参数
     * @return {@link String} Token
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody LoginDTO loginDTO) {
        return Result.success(loginService.login(loginDTO));
    }

    /**
     * 用户注销登录
     */
    @Operation(summary = "用户注销登录")
    @GetMapping("/logout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.success();
    }

    /**
     * 用户邮箱注册
     * @param register 注册信息
     * @return {@link Result<>}
     */
    @Operation(summary = "用户邮箱注册")
    @PostMapping("/register")
    public Result<?> register(@Validated @RequestBody RegisterDTO register) {
        loginService.register(register);
        return Result.success();
    }

    /**
     * 发送邮箱验证码
     * @return {@link Result<>}
     */
    @AccessLimit(seconds = 60, maxCount = 1)
    @Operation(summary = "发送邮箱验证码")
    @GetMapping("/sendCode")
    public Result<?> sendCode(String username) {
        loginService.sendCode(username);
        return Result.success();
    }

    /**
     * 获取后台用户信息
     * @return 后台用户信息
     */
    @Operation(summary = "获取后台用户信息")
    @GetMapping("/admin/getAdminUserInfo")
    public Result<AdminUserInfoVO> getAdminUserInfo() {
        return Result.success(userService.getAdminUserInfo());
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @Operation(summary = "获取用户信息")
    @GetMapping("/getUserInfo")
    public Result<UserInfoVO> getUserInfo() {
        return Result.success(userService.getUserInfo());
    }

    /**
     * 获取菜单路由
     * @return 菜单路由
     */
    @Operation(summary = "获取菜单路由")
    @GetMapping("/admin/getRouters")
    public Result<List<RouterVO>> getRouters() {
        int userId = StpUtil.getLoginIdAsInt();
        List<Menu> menus = menuService.listMenuTreeByUserId(userId);
        return Result.success(menuService.buildMenus(menus));
    }

}
