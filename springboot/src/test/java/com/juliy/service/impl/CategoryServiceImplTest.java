package com.juliy.service.impl;

import com.juliy.model.dto.CategoryDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.CategoryAdminVO;
import com.juliy.model.vo.CategoryVO;
import com.juliy.model.vo.PageResult;
import com.juliy.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author juliy
 * @date 2023/3/30 15:12
 */
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void listCategories() {
        List<CategoryVO> categoryList = categoryService.listCategories();
        System.out.println(categoryList);
    }

    @Test
    void listCategoriesAdminByPage() {
        PageResult<CategoryAdminVO> categoryList = categoryService.listCategoriesAdminByPage(new ConditionDTO());
        System.out.println(categoryList);
    }

    @Test
    void listCategoryOptions() {
    }

    @Test
    void saveOrUpdateCategory() {
        // save
        CategoryDTO category1 = new CategoryDTO();
        category1.setCategoryName("测试分类3");
        categoryService.saveOrUpdateCategory(category1);
        //update
        CategoryDTO category2 = new CategoryDTO();
        category2.setId(29);
        category2.setCategoryName("测试分类3");
        categoryService.saveOrUpdateCategory(category2);
    }

    @Test
    void removeCategories() {
    }
}