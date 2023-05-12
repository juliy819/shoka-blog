package com.juliy.controller;

import com.juliy.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
