package com.juliy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.juliy.model.dto.ArticleDTO;
import com.juliy.model.dto.ArticleFeaturedDTO;
import com.juliy.model.dto.ArticleTopDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.ArticleAdminVO;
import com.juliy.model.vo.ArticleInfoVO;
import com.juliy.model.vo.PageResult;
import com.juliy.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author juliy
 * @date 2023/3/30 13:36
 */
@SpringBootTest
class ArticleServiceImplTest {

    @Autowired
    private ArticleService articleService;

    @Test
    void listArticlesAdminByPageTestCase1() {
        ConditionDTO condition = new ConditionDTO();
        condition.setIsDelete(0);
        condition.setStatus(3);
        PageResult<ArticleAdminVO> articleList = articleService.listArticlesAdminByPage(condition);
        System.out.println(articleList);
    }

    @Test
    void getArticleInfoByIdTestCase1() {
        ArticleInfoVO articleInfo = articleService.getArticleInfoById(1);
        System.out.println(articleInfo);
    }

    @Test
    void saveOrUpdateArticleTestCase1() {
        StpUtil.login(1);

        ArticleDTO article1 = new ArticleDTO();
        article1.setArticleTitle("测试文章1标题");
        article1.setArticleContent("测试文章1内容");
        article1.setCategoryName("测试分类");
        List<String> tagNameList = new ArrayList<>(List.of("测试标签1", "测试标签2", "测试标签3"));
        article1.setTagNameList(tagNameList);
        article1.setStatus(3);
        article1.setIsTop(1);
        article1.setIsFeatured(1);
        articleService.saveOrUpdateArticle(article1);

        ArticleDTO article2 = new ArticleDTO();
        article2.setArticleTitle("测试文章2标题");
        article2.setArticleContent("测试文章2内容");
        article2.setStatus(3);
        article2.setIsTop(1);
        article2.setIsFeatured(1);
        articleService.saveOrUpdateArticle(article2);
    }

    @Test
    void saveOrUpdateArticleTestCase2() {
        ArticleDTO article = new ArticleDTO();
        article.setId(2);
        article.setIsFeatured(0);
        article.setArticleContent("修改内容");
        articleService.saveOrUpdateArticle(article);
    }

    @Test
    void deleteArticlesTestCase1() {
        List<Integer> articleIdList = List.of(3, 4);
        articleService.deleteArticles(articleIdList);
    }

    @Test
    void recycleArticlesTestCase1() {
        List<Integer> articleIdList = List.of(7, 8);
        articleService.recycleArticles(articleIdList);
    }

    @Test
    void recoverArticleTestCase1() {
        List<Integer> articleIdList = List.of(1, 2);
        articleService.recoverArticles(articleIdList);
    }

    @Test
    void updateArticleTopTestCase1() {
        ArticleTopDTO top = new ArticleTopDTO();
        top.setArticleId(1);
        top.setIsTop(0);
        articleService.updateArticleTop(top);
    }

    @Test
    void updateArticleTopTestCase2() {
        ArticleTopDTO top = new ArticleTopDTO();
        top.setArticleId(1);
        top.setIsTop(1);
        articleService.updateArticleTop(top);
    }

    @Test
    void updateArticleFeaturedTestCase1() {
        ArticleFeaturedDTO featured = new ArticleFeaturedDTO();
        featured.setArticleId(1);
        featured.setIsFeatured(0);
        articleService.updateArticleFeatured(featured);
    }

    @Test
    void updateArticleFeaturedTestCase2() {
        ArticleFeaturedDTO featured = new ArticleFeaturedDTO();
        featured.setArticleId(1);
        featured.setIsFeatured(1);
        articleService.updateArticleFeatured(featured);
    }

    @Test
    void saveArticleImages() {
    }
}