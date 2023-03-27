package com.juliy.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.Article;
import com.juliy.entity.Category;
import com.juliy.exception.ServiceException;
import com.juliy.mapper.ArticleMapper;
import com.juliy.mapper.CategoryMapper;
import com.juliy.model.dto.CategoryAdminDTO;
import com.juliy.model.dto.CategoryDTO;
import com.juliy.model.dto.CategoryOptionDTO;
import com.juliy.model.vo.CategoryVO;
import com.juliy.model.vo.ConditionVO;
import com.juliy.model.vo.PageResult;
import com.juliy.service.CategoryService;
import com.juliy.utils.BeanCopyUtils;
import com.juliy.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 分类业务接口实现类
 * @author juliy
 * @date 2023/3/25 12:01
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final ArticleMapper articleMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper, ArticleMapper articleMapper) {
        this.categoryMapper = categoryMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public List<CategoryDTO> listCategories() {
        return categoryMapper.selectCategories();
    }

    @Override
    public PageResult<CategoryAdminDTO> listCategoriesAdmin(ConditionVO condition) {
        Long count = categoryMapper.selectCount(new LambdaQueryWrapper<Category>()
                                                        .like(StrUtil.isNotBlank(condition.getKeywords()), Category::getCategoryName, condition.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        List<CategoryAdminDTO> categoryList = categoryMapper.selectCategoriesAdmin(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(categoryList, count);
    }

    @Override
    public List<CategoryOptionDTO> listCategoryOptions(ConditionVO condition) {
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                                                                        .like(StrUtil.isNotBlank(condition.getKeywords()), Category::getCategoryName, condition.getKeywords())
                                                                        .orderByDesc(Category::getId));
        return BeanCopyUtils.copyBeanList(categoryList, CategoryOptionDTO.class);
    }

    @Override
    public void addCategory(CategoryVO categoryVO) {
        // 判断分类是否已存在
        Category existCategory = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                                                                  .select(Category::getId)
                                                                  .eq(Category::getCategoryName, categoryVO.getCategoryName()));
        if (Objects.nonNull(existCategory)) {
            throw new ServiceException("分类已存在");
        }
        // 添加新分类
        Category newCategory = Category.builder()
                .categoryName(categoryVO.getCategoryName())
                .build();
        this.save(newCategory);
    }

    @Override
    public void deleteCategories(List<Integer> categoryList) {
        Long count = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                                                       .in(Article::getCategoryId, categoryList));
        if (count > 0) {
            throw new ServiceException("删除失败，该分类下存在文章");
        }
        categoryMapper.deleteBatchIds(categoryList);
    }

    @Override
    public void updateCategory(CategoryVO categoryVO) {
        Category existCategory = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                                                                  .select(Category::getId)
                                                                  .eq(Category::getCategoryName, categoryVO.getCategoryName()));
        if (Objects.nonNull(existCategory) && !existCategory.getId().equals(categoryVO.getId())) {
            throw new ServiceException("分类已存在");
        }
        // 修改分类
        Category newCategory = Category.builder()
                .id(categoryVO.getId())
                .categoryName(categoryVO.getCategoryName())
                .build();
        this.updateById(newCategory);
    }
}
