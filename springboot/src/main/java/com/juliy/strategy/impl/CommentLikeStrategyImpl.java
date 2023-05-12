package com.juliy.strategy.impl;

import com.juliy.strategy.LikeStrategy;
import org.springframework.stereotype.Service;

/**
 * 评论点赞策略
 * @author juliy
 * @date 2023/4/19 10:20
 */
@Service("CommentLikeStrategyImpl")
public class CommentLikeStrategyImpl implements LikeStrategy {

    @Override
    public void like(Integer typeId) {
        //todo 评论点赞策略
    }
}
