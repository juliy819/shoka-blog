package com.juliy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.*;
import com.juliy.exception.ServiceException;
import com.juliy.mapper.*;
import com.juliy.model.dto.ArticleDTO;
import com.juliy.model.dto.ArticleFeaturedDTO;
import com.juliy.model.dto.ArticleTopDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.ArticleAdminVO;
import com.juliy.model.vo.ArticleInfoVO;
import com.juliy.model.vo.PageResult;
import com.juliy.service.ArticleService;
import com.juliy.strategy.context.UploadStrategyContext;
import com.juliy.utils.BeanCopyUtils;
import com.juliy.utils.FileUtils;
import com.juliy.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.juliy.constant.CommonConstant.FALSE;
import static com.juliy.constant.CommonConstant.TRUE;
import static com.juliy.enums.FilePathEnum.ARTICLE;

/**
 * 文件服务接口实现类
 * @author juliy
 * @date 2023/3/29 14:12
 */
@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final UploadStrategyContext uploadStrategyContext;
    private final BlogFileMapper blogFileMapper;
    private final ArticleMapper articleMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;
    private final ArticleTagMapper articleTagMapper;
    private final TagServiceImpl tagService;

    @Autowired
    public ArticleServiceImpl(UploadStrategyContext uploadStrategyContext,
                              BlogFileMapper blogFileMapper,
                              ArticleMapper articleMapper,
                              CategoryMapper categoryMapper,
                              TagMapper tagMapper,
                              ArticleTagMapper articleTagMapper,
                              TagServiceImpl tagService) {
        this.uploadStrategyContext = uploadStrategyContext;
        this.blogFileMapper = blogFileMapper;
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
        this.articleTagMapper = articleTagMapper;
        this.tagService = tagService;
    }


    @Override
    public PageResult<ArticleAdminVO> listArticlesAdminByPage(ConditionDTO condition) {
        // 查询文章数量
        Long count = articleMapper.countArticlesAdmin(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询文章后台信息
        List<ArticleAdminVO> articleAdminList = articleMapper
                .selectArticlesAdmin(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        // todo 获取点赞量和浏览量
        articleAdminList.forEach(articleAdminVO -> {
            articleAdminVO.setLikeCount(0);
            articleAdminVO.setViewCount(0);
        });
        return new PageResult<>(articleAdminList, count);
    }

    @Override
    public ArticleInfoVO getArticleInfoById(Integer articleId) {
        // 查询文章信息
        ArticleInfoVO articleInfoVO = articleMapper.selectArticleInfoById(articleId);
        if (Objects.isNull(articleInfoVO)) {
            throw new ServiceException("文章不存在");
        }
        // 查询分类名称
        String categoryName = categoryMapper.selectOne(
                        new LambdaQueryWrapper<Category>()
                                .select(Category::getCategoryName)
                                .eq(Category::getId, articleInfoVO.getCategoryId()))
                .getCategoryName();
        // 查询标签名称
        List<String> tagNameList = tagMapper.selectTagNamesByArticleId(articleId);
        articleInfoVO.setCategoryName(categoryName);
        articleInfoVO.setTagNameList(tagNameList);
        return articleInfoVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateArticle(ArticleDTO articleDTO) {
        // 若没有分类名，则设置为默认分类
        if (StrUtil.isEmpty(articleDTO.getCategoryName())) {
            articleDTO.setCategoryName("默认分类");
        }
        // 保存分类
        Integer categoryId = saveCategory(articleDTO.getCategoryName());
        // 修改文章
        Article article = BeanCopyUtils.copyBean(articleDTO, Article.class);
        article.setCategoryId(categoryId);
        // 添加文章时设置作者
        if (Objects.isNull(article.getId())) {
            article.setUserId(StpUtil.getLoginIdAsInt());
        }
        this.saveOrUpdate(article);
        // 保存文章标签
        saveTags(articleDTO.getTagNameList(), article.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteArticles(List<Integer> articleIdList) {
        // 删除文章标签
        articleTagMapper.delete(
                new LambdaQueryWrapper<ArticleTag>()
                        .in(ArticleTag::getArticleId, articleIdList));
        // 删除文章
        this.removeBatchByIds(articleIdList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void recycleArticles(List<Integer> articleIdList) {
        // 设置要回收的文章的状态
        List<Article> articleList = articleIdList.stream()
                .map(articleId -> Article.builder()
                        .id(articleId)
                        .isDelete(TRUE)
                        .isTop(FALSE)
                        .isFeatured(FALSE)
                        .build())
                .toList();
        this.updateBatchById(articleList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void recoverArticles(List<Integer> articleIdList) {
        // 设置要恢复的文章的状态
        List<Article> articleList = articleIdList.stream()
                .map(articleId -> Article.builder()
                        .id(articleId)
                        .isDelete(FALSE)
                        .build())
                .toList();
        this.updateBatchById(articleList);
    }

    @Override
    public void updateArticleTop(ArticleTopDTO articleTopDTO) {
        Article article = Article.builder()
                .id(articleTopDTO.getId())
                .isTop(articleTopDTO.getIsTop())
                .build();
        this.updateById(article);
    }

    @Override
    public void updateArticleFeatured(ArticleFeaturedDTO articleFeaturedDTO) {
        Article article = Article.builder()
                .id(articleFeaturedDTO.getId())
                .isFeatured(articleFeaturedDTO.getIsFeatured())
                .build();
        this.updateById(article);
    }

    @Override
    public String saveArticleImages(MultipartFile file) {
        String url = uploadStrategyContext.executeUploadStrategy(file, ARTICLE.getPath());
        try {
            // 获取文件md5值及后缀名
            String md5 = FileUtils.getMd5(file.getInputStream());
            String suffix = FileUtils.getSuffix(file);
            // 查找文件是否存在
            BlogFile existFile = blogFileMapper.selectOne(
                    new LambdaQueryWrapper<BlogFile>()
                            .select(BlogFile::getId)
                            .eq(BlogFile::getFileName, md5)
                            .eq(BlogFile::getFilePath, ARTICLE.getFilePath()));
            // 若不存在则保存文件信息
            if (Objects.isNull(existFile)) {
                BlogFile newFile = BlogFile.builder()
                        .fileUrl(url)
                        .fileName(md5)
                        .filePath(ARTICLE.getFilePath())
                        .extendName(suffix)
                        .fileSize((int) file.getSize())
                        .isDir(FALSE)
                        .build();
                blogFileMapper.insert(newFile);
            }
        } catch (IOException e) {
            log.error("文件信息保存失败\n", e);
        }
        return url;
    }

    /**
     * 保存分类
     * @param categoryName 分类名称
     * @return 分类id
     */
    private Integer saveCategory(String categoryName) {
        // 查询分类
        Category category = categoryMapper.selectOne(
                new LambdaQueryWrapper<Category>()
                        .select(Category::getId)
                        .eq(Category::getCategoryName, categoryName));

        if (Objects.isNull(category)) {
            // 不存在则保存分类
            category = Category.builder()
                    .categoryName(categoryName)
                    .build();
            categoryMapper.insert(category);
        }
        return category.getId();
    }

    /**
     * 保存标签
     * @param tagNameList 标签名称列表
     */
    private void saveTags(List<String> tagNameList, Integer articleId) {
        // 先删除文章对应的标签
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                                        .eq(ArticleTag::getArticleId, articleId));

        if (CollectionUtils.isNotEmpty(tagNameList)) {
            // 查询已存在的标签，并分别获取标签id和标签名称
            List<Tag> existTagList = tagMapper.selectTagList(tagNameList);
            List<String> existTagNameList = existTagList.stream()
                    .map(Tag::getTagName)
                    .toList();
            List<Integer> existTagIdList = existTagList.stream()
                    .map(Tag::getId)
                    .collect(Collectors.toList());
            // 移除已存在的标签
            tagNameList.removeAll(existTagNameList);
            // 保存新标签
            if (CollectionUtils.isNotEmpty(tagNameList)) {
                List<Tag> newTagList = tagNameList.stream()
                        .map(tagName -> Tag.builder()
                                .tagName(tagName)
                                .build())
                        .toList();
                tagService.saveBatch(newTagList);
                // 获取新标签id
                List<Integer> newTagIdList = newTagList.stream()
                        .map(Tag::getId)
                        .toList();
                // 将新增的标签id进行合并
                existTagIdList.addAll(newTagIdList);
            }
            articleTagMapper.insertBatchArticleTag(articleId, existTagIdList);
        }
    }
}
