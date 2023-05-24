package com.juliy.strategy.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.juliy.entity.Comment;
import com.juliy.service.CommentService;
import com.juliy.service.RedisService;
import com.juliy.strategy.LikeStrategy;
import com.juliy.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.juliy.constant.CommonConstant.FALSE;
import static com.juliy.constant.RedisConstant.COMMENT_LIKE;
import static com.juliy.constant.RedisConstant.COMMENT_LIKE_COUNT;

/**
 * 评论点赞策略
 * @author juliy
 * @date 2023/4/19 10:20
 */
@Service("CommentLikeStrategyImpl")
public class CommentLikeStrategyImpl extends LikeStrategy {

    private final CommentService commentService;

    @Autowired
    public CommentLikeStrategyImpl(RedisService redisService, CommentService commentService) {
        super(redisService);
        this.commentService = commentService;
    }

    @Override
    public void like(Integer commentId) {
        // 判断评论是否存在或是否通过或是否进入回收站
        Comment comment = commentService.getOne(new LambdaQueryWrapper<Comment>()
                                                        .select(Comment::getId, Comment::getIsCheck)
                                                        .eq(Comment::getId, commentId).last("limit 1"));
        CommonUtils.checkParam(Objects.isNull(comment) || comment.getIsCheck().equals(FALSE), "评论不存在");
        // 以用户id作为key
        String userLikeCommentKey = COMMENT_LIKE + StpUtil.getLoginIdAsString();
        judgeLike(userLikeCommentKey, COMMENT_LIKE_COUNT, commentId);
    }
}
