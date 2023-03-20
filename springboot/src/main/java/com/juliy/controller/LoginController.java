package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.juliy.entity.Menu;
import com.juliy.model.dto.LoginDTO;
import com.juliy.model.vo.BackendUserInfoVO;
import com.juliy.model.vo.Result;
import com.juliy.model.vo.RouterVO;
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
     * @param loginInfo 登录参数
     * @return {@link String} Token
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody LoginDTO loginInfo) {
        return Result.success(loginService.login(loginInfo));
    }

    /**
     * 用户注销登录
     */
    @SaCheckLogin
    @Operation(summary = "用户注销登录")
    @GetMapping("/logout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.success();
    }

    ///**
    // * 用户注册
    // * @param registerInfo
    // * @return
    // */
    //@Operation(summary = "用户注册")
    //@PostMapping("/users/register")
    //public Result<?> register(@Valid @RequestBody RegisterDTO registerInfo) {
    //    loginService.register(registerInfo);
    //    return Result.success();
    //}

    /**
     * 获取后台用户信息
     * @return 后台用户信息
     */
    @Operation(summary = "获取后台用户信息")
    @GetMapping("/admin/getBackendUserInfo")
    public Result<BackendUserInfoVO> getInfo() {
        return Result.success(userService.getBackendUserInfo());
    }

    /**
     * 获取菜单路由
     * @return 菜单路由
     */
    @Operation(summary = "获取菜单路由")
    @GetMapping("/admin/getRouters")
    public Result<List<RouterVO>> getRouters() {
        int userId = StpUtil.getLoginIdAsInt();
        List<Menu> menus = menuService.selectMenuTreeByUserId(userId);
        return Result.success(menuService.buildMenus(menus));
    }

}
