package com.juliy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色枚举
 * @author juliy
 * @date 2023/5/27 14:07
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

    /**
     * 管理员
     */
    ADMIN(1, "admin"),
    /**
     * 用户
     */
    USER(2, "user"),
    /**
     * 测试账号
     */
    TEST(3, "test");

    /**
     * 角色id
     */
    private final Integer roleId;

    /**
     * 描述
     */
    private final String name;
}
