package com.juliy.controller;

import com.juliy.service.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单控制器
 * @author juliy
 * @date 2023/3/13 11:55
 */
@Tag(name = "菜单模块")
@Slf4j
@RestController
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


}
