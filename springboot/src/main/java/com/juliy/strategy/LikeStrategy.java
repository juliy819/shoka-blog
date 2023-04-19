package com.juliy.strategy;

/**
 * 点赞策略
 * @author juliy
 * @date 2023/4/19 10:05
 */
public interface LikeStrategy {

    /**
     * 点赞
     * @param typeId 类型id
     */
    void like(Integer typeId);
}
