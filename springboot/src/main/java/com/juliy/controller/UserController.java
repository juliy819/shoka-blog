package com.juliy.controller;

import com.juliy.model.dto.EmailDTO;
import com.juliy.model.dto.PasswordDTO;
import com.juliy.model.dto.UserInfoDTO;
import com.juliy.model.vo.Result;
import com.juliy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * 修改用户邮箱
     * @param email 邮箱信息
     * @return {@link Result<>}
     */
    @Operation(description = "修改用户邮箱")
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
    @Operation(description = "修改用户头像")
    @PostMapping("/avatar")
    public Result<String> updateUserAvatar(@RequestParam(value = "file") MultipartFile file) {
        return Result.success(userService.saveAvatar(file));
    }

    /**
     * 修改用户信息
     * @param userInfo 用户信息
     * @return {@link Result<>}
     */
    @Operation(description = "修改用户信息")
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
    @Operation(description = "修改用户密码")
    @PutMapping("/password")
    public Result<?> updatePassword(@Validated @RequestBody PasswordDTO password) {
        userService.updatePassword(password);
        return Result.success();
    }


}
