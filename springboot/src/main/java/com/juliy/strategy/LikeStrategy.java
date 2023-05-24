package com.juliy.strategy;

import com.juliy.service.RedisService;

/**
 * 点赞策略
 * @author juliy
 * @date 2023/4/19 10:05
 */
public abstract class LikeStrategy {

    private final RedisService redisService;

    protected LikeStrategy(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 点赞
     * @param typeId 点赞对象id
     */
    public abstract void like(Integer typeId);

    /**
     * 判断是否点赞
     * @param key    redis key
     * @param type   redis hash key
     * @param typeId 点赞对象id
     */
    public void judgeLike(String key, String type, Integer typeId) {
        if (redisService.hasSetValue(key, typeId)) {
            // 取消点赞
            redisService.deleteSet(key, typeId);
            redisService.decrHash(type, typeId.toString(), 1L);
        } else {
            // 点赞
            redisService.setSet(key, typeId);
            redisService.incrHash(type, typeId.toString(), 1L);
        }
    }
}
