package com.juliy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件路径枚举
 * @author juliy
 * @date 2023/3/29 14:16
 */
@Getter
@AllArgsConstructor
public enum FilePathEnum {

    /**
     * 头像路径
     */
    AVATAR("/avatar/", "/avatar", "头像路径"),

    /**
     * 文章图片路径
     */
    ARTICLE("/article/", "/article", "文章图片路径");


    /**
     * 路径
     */
    private final String path;

    /**
     * 文件路径
     */
    private final String filePath;

    /**
     * 描述
     */
    private final String description;
}
