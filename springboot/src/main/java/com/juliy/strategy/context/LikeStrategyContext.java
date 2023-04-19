package com.juliy.strategy.context;

import com.juliy.enums.LikeTypeEnum;
import com.juliy.strategy.LikeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 点赞策略上下文
 * @author juliy
 * @date 2023/4/19 10:05
 */
@Service
public class LikeStrategyContext {

    private final Map<String, LikeStrategy> likeStrategyMap;

    @Autowired
    public LikeStrategyContext(Map<String, LikeStrategy> likeStrategyMap) {
        this.likeStrategyMap = likeStrategyMap;
    }

    /**
     * 点赞
     * @param likeType 点赞类型
     * @param typeId   类型id
     */
    public void executeLikeStrategy(LikeTypeEnum likeType, Integer typeId) {
        likeStrategyMap.get(likeType.getStrategy()).like(typeId);
    }
}
