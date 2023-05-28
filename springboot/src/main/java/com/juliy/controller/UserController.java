package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.juliy.annotation.OptLogger;
import com.juliy.model.dto.*;
import com.juliy.model.vo.PageResult;
import com.juliy.model.vo.Result;
import com.juliy.model.vo.UserAdminVO;
import com.juliy.model.vo.UserRoleVO;
import com.juliy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.juliy.constant.OptTypeConstant.UPDATE;

/**
 * 用户控制器
 * @author juliy
 * @date 2023/5/9 9:26
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户模块")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    /**
     * 查看后台用户列表
     * @param condition 条件
     * @return {@link UserAdminVO} 用户后台列表
     */
    @Operation(description = "查看后台用户列表")
    @SaCheckPermission("user:list")
    @GetMapping("/list")
    public Result<PageResult<UserAdminVO>> listUserAdmin(ConditionDTO condition) {
        return Result.success(userService.listUserAdmin(condition));
    }

    /**
     * 查看用户角色选项
     * @return {@link UserRoleVO} 用户角色选项
     */
    @Operation(description = "查看用户角色选项")
    @SaCheckPermission("user:list")
    @GetMapping("/role")
    public Result<List<UserRoleVO>> listUserRole() {
        return Result.success(userService.listUserRole());
    }

    /**
     * 修改用户
     * @param user 用户信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @Operation(description = "修改用户")
    @SaCheckPermission("user:update")
    @PutMapping("/admin/update")
    public Result<?> updateUser(@Validated @RequestBody UserRoleDTO user) {
        userService.updateUser(user);
        return Result.success();
    }

    /**
     * 修改用户状态
     * @param disable 禁用信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @Operation(description = "修改用户状态")
    @SaCheckPermission("user:status")
    @PutMapping("/status")
    public Result<?> updateUserStatus(@Validated @RequestBody DisableDTO disable) {
        userService.updateUserStatus(disable);
        return Result.success();
    }


    /**
     * 修改用户邮箱
     * @param email 邮箱信息
     * @return {@link Result<>}
     */
    @Operation(summary = "修改用户邮箱")
    @PutMapping("/email")
    public Result<?> updateUserEmail(@Validated @RequestBody EmailDTO email) {
        userService.updateUserEmail(email);
        return Result.success();
    }

    /**
     * 修改用户头像
     * @param file 文件
     * @return {@link Result<String>} 头像地址
     */
    @Operation(summary = "修改用户头像")
    @PostMapping("/avatar")
    public Result<String> updateUserAvatar(@RequestParam(value = "file") MultipartFile file) {
        return Result.success(userService.saveAvatar(file));
    }

    /**
     * 修改用户信息
     * @param userInfo 用户信息
     * @return {@link Result<>}
     */
    @Operation(summary = "修改用户信息")
    @PutMapping("/info")
    public Result<?> updateUserInfo(@Validated @RequestBody UserInfoDTO userInfo) {
        userService.updateUserInfo(userInfo);
        return Result.success();
    }

    /**
     * 修改用户密码
     * @param password 用户信息
     * @return {@link Result<>}
     */
    @Operation(summary = "修改用户密码")
    @PutMapping("/password")
    public Result<?> updatePassword(@Validated @RequestBody PasswordDTO password) {
        userService.updatePassword(password);
        return Result.success();
    }


}
