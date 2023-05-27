package com.juliy.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.juliy.entity.Article;
import com.juliy.entity.SiteConfig;
import com.juliy.model.vo.*;
import com.juliy.service.*;
import com.juliy.utils.IpUtils;
import com.juliy.utils.UserAgentUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.juliy.constant.CommonConstant.FALSE;
import static com.juliy.constant.RedisConstant.*;
import static com.juliy.enums.ArticleStatusEnum.PUBLIC;

/**
 * 博客信息服务接口实现类
 * @author juliy
 * @date 2023/4/8 14:35
 */
@Slf4j
@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final SiteConfigService siteConfigService;
    private final UserService userService;
    private final MessageService messageService;
    private final VisitLogService visitLogService;
    private final RedisService redisService;
    private final HttpServletRequest request;

    @Autowired
    public BlogInfoServiceImpl(ArticleService articleService,
                               CategoryService categoryService,
                               TagService tagService,
                               SiteConfigService siteConfigService,
                               UserService userService,
                               MessageService messageService,
                               VisitLogService visitLogService,
                               RedisService redisService,
                               HttpServletRequest request) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.siteConfigService = siteConfigService;
        this.userService = userService;
        this.messageService = messageService;
        this.visitLogService = visitLogService;
        this.redisService = redisService;
        this.request = request;
    }

    @Override
    public void report() {
        // 获取访问者ip、系统和浏览器
        String ipAddress = IpUtils.getIpAddress(request);
        Map<String, String> userAgentMap = UserAgentUtils.parseOsAndBrowser(request.getHeader("User-Agent"));
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        // 生成唯一标识
        String uuid = ipAddress + os + browser;
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否为新访客
        if (!redisService.hasSetValue(UNIQUE_VISITOR, md5)) {
            // 若为新访客则增加访问量并保存记录
            redisService.incr(BLOG_VIEW_COUNT, 1);
            redisService.setSet(UNIQUE_VISITOR, md5);
        }
    }

    @Override
    public BlogInfoVO getBlogInfo() {
        // 文章数量
        Long articleCount = articleService.count(
                new LambdaQueryWrapper<Article>()
                        .eq(Article::getStatus, PUBLIC.getStatus())
                        .eq(Article::getIsDelete, FALSE));
        // 分类数量
        Long categoryCount = categoryService.count();
        // 标签数量
        Long tagCount = tagService.count();
        // 博客访问量
        Integer count = redisService.getObject(BLOG_VIEW_COUNT);
        Long viewCount = Optional.of(count.longValue()).orElse(0L);
        // 网站配置
        SiteConfig siteConfig = siteConfigService.getSiteConfig();

        return BlogInfoVO.builder()
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .viewCount(viewCount)
                .siteConfig(siteConfig)
                .build();
    }

    @Override
    public BlogInfoAdminVO getBlogInfoAdmin() {
        // 访问量
        Integer count = redisService.getObject(BLOG_VIEW_COUNT);
        Long viewCount = Optional.of(count.longValue()).orElse(0L);
        // 留言量
        Long messageCount = messageService.count();
        // 用户量
        Long userCount = userService.count();
        // 文章量
        Long articleCount = articleService.count(
                new LambdaQueryWrapper<Article>()
                        .eq(Article::getStatus, PUBLIC.getStatus())
                        .eq(Article::getIsDelete, FALSE));
        // 分类
        List<CategoryVO> categoryList = categoryService.listCategories();
        // 标签
        List<TagVO> tagList = tagService.listTags();
        // 用户浏览数据
        List<UserViewVO> userViewList = visitLogService.listUserViews();
        // 文章贡献统计
        List<ArticleStatisticsVO> articleStatisticsList = articleService.getArticleStatistics();
        // 文章访问量排行前五
        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(ARTICLE_VIEW_COUNT, 0, 4);
        List<ArticleRankVO> articleRankList = null;
        if (CollectionUtil.isNotEmpty(articleMap)) {
            articleRankList = listArticleRank(articleMap);
        }
        return BlogInfoAdminVO.builder()
                .articleStatisticsList(articleStatisticsList)
                .tagList(tagList)
                .viewCount(viewCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryList(categoryList)
                .userViewList(userViewList)
                .articleRankList(articleRankList)
                .build();
    }

    /**
     * 查询文章排行
     * @param articleMap 文章浏览量信息
     * @return {@link List<ArticleRankVO>} 文章排行
     */
    private List<ArticleRankVO> listArticleRank(Map<Object, Double> articleMap) {
        // 提取文章id
        List<Integer> articleIdList = new ArrayList<>(articleMap.size());
        articleMap.forEach((key, value) -> articleIdList.add((Integer) key));
        // 查询文章信息
        List<Article> articleList = articleService.list(new LambdaQueryWrapper<Article>()
                                                                .select(Article::getId, Article::getArticleTitle)
                                                                .in(Article::getId, articleIdList));
        return articleList.stream()
                .map(article -> ArticleRankVO.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewCount(articleMap.get(article.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankVO::getViewCount).reversed())
                .collect(Collectors.toList());
    }
}