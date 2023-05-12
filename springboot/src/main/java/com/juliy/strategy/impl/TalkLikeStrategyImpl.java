package com.juliy.strategy.impl;

import com.juliy.strategy.LikeStrategy;
import org.springframework.stereotype.Service;

/**
 * 说说点赞策略
 * @author juliy
 * @date 2023/4/19 10:22
 */
@Service("TalkLikeStrategyImpl")
public class TalkLikeStrategyImpl implements LikeStrategy {
    @Override
    public void like(Integer typeId) {
        // todo 说说点赞策略
    }
}
