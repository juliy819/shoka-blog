package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.Article;
import com.juliy.model.dto.ArticleDTO;
import com.juliy.model.dto.ArticleFeaturedDTO;
import com.juliy.model.dto.ArticleTopDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.ArticleAdminVO;
import com.juliy.model.vo.ArticleInfoVO;
import com.juliy.model.vo.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件服务接口
 * @author juliy
 * @date 2023/3/29 14:12
 */
public interface ArticleService extends IService<Article> {

    /**
     * 查看后台文章列表
     * @param condition 查询条件
     * @return {@link PageResult<ArticleAdminVO>} 后台文章列表
     */
    PageResult<ArticleAdminVO> listArticlesAdminByPage(ConditionDTO condition);

    /**
     * 获取文章信息
     * @param articleId 文章id
     * @return {@link ArticleInfoVO} 文章详情
     */
    ArticleInfoVO getArticleInfoById(Integer articleId);

    /**
     * 添加或修改文章
     * @param articleDTO 文章
     */
    void saveOrUpdateArticle(ArticleDTO articleDTO);

    /**
     * 删除文章
     * @param articleIdList 文章id列表
     */
    void deleteArticles(List<Integer> articleIdList);

    /**
     * 回收文章
     * @param articleIdList 文章id列表
     */
    void recycleArticles(List<Integer> articleIdList);

    /**
     * 恢复文章
     * @param articleIdList 文章id列表
     */
    void recoverArticles(List<Integer> articleIdList);

    /**
     * 修改文章置顶状态
     * @param articleTopDTO 置顶
     */
    void updateArticleTop(ArticleTopDTO articleTopDTO);

    /**
     * 修改文章推荐状态
     * @param articleFeaturedDTO 推荐
     */
    void updateArticleFeatured(ArticleFeaturedDTO articleFeaturedDTO);

    /**
     * 上传文章图片
     * @param file 文件
     * @return 文章图片地址
     */
    String saveArticleImages(MultipartFile file);
}
