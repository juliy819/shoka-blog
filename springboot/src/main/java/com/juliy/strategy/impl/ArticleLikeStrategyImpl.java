package com.juliy.strategy.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.juliy.entity.Article;
import com.juliy.exception.ServiceException;
import com.juliy.mapper.ArticleMapper;
import com.juliy.service.RedisService;
import com.juliy.strategy.LikeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.juliy.constant.RedisConstant.ARTICLE_LIKE;
import static com.juliy.constant.RedisConstant.ARTICLE_LIKE_COUNT;

/**
 * 文章点赞策略
 * @author juliy
 * @date 2023/4/19 10:08
 */
@Service("ArticleLikeStrategyImpl")
public class ArticleLikeStrategyImpl extends LikeStrategy {

    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleLikeStrategyImpl(RedisService redisService, ArticleMapper articleMapper) {
        super(redisService);
        this.articleMapper = articleMapper;
    }

    @Override
    public void like(Integer articleId) {
        Article article = articleMapper.selectOne(
                new LambdaQueryWrapper<Article>()
                        .select(Article::getId, Article::getIsDelete)
                        .eq(Article::getId, articleId));
        if (Objects.isNull(article) || article.getIsDelete() == 1) {
            throw new ServiceException("文章不存在或已删除");
        }
        // 以用户id作为key
        String likeArticleKey = ARTICLE_LIKE + StpUtil.getLoginIdAsString();
        super.judgeLike(likeArticleKey, ARTICLE_LIKE_COUNT, articleId);
    }
}
