package com.juliy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录类型枚举
 * @author juliy
 * @date 2023/3/1 20:06
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    /**
     * 邮箱
     */
    EMAIL(1, "邮箱登录", ""),

    /**
     * QQ
     */
    QQ(2, "QQ登录", "qqLoginStrategyImpl");


    /**
     * 登录方式
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 策略
     */
    private final String strategy;
}
