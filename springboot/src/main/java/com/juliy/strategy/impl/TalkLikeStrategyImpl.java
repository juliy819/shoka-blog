package com.juliy.strategy.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.juliy.entity.Talk;
import com.juliy.service.RedisService;
import com.juliy.service.TalkService;
import com.juliy.strategy.LikeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.juliy.constant.RedisConstant.TALK_LIKE;
import static com.juliy.constant.RedisConstant.TALK_LIKE_COUNT;

/**
 * 说说点赞策略
 * @author juliy
 * @date 2023/4/19 10:22
 */
@Service("TalkLikeStrategyImpl")
public class TalkLikeStrategyImpl extends LikeStrategy {

    private final TalkService talkService;

    @Autowired
    public TalkLikeStrategyImpl(RedisService redisService, TalkService talkService) {
        super(redisService);
        this.talkService = talkService;
    }

    @Override
    public void like(Integer talkId) {
        Talk talk = talkService.getOne(new LambdaQueryWrapper<Talk>()
                                               .select(Talk::getId)
                                               .eq(Talk::getId, talkId));
        Assert.notNull(talk, "说说不存在");
        // 以用户id作为key
        String userLikeTalkKey = TALK_LIKE + StpUtil.getLoginIdAsString();
        judgeLike(userLikeTalkKey, TALK_LIKE_COUNT, talkId);
    }
}
