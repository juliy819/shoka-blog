package com.juliy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 点赞类型枚举
 * @author juliy
 * @date 2023/4/19 10:18
 */
@Getter
@AllArgsConstructor
public enum LikeTypeEnum {

    /**
     * 文章
     */
    ARTICLE("文章", "ArticleLikeStrategyImpl"),

    /**
     * 评论
     */
    COMMENT("评论", "CommentLikeStrategyImpl"),

    /**
     * 说说
     */
    TALK("说说", "TalkLikeStrategyImpl");

    /**
     * 点赞类型
     */
    private final String likeType;

    /**
     * 策略
     */
    private final String strategy;
}
