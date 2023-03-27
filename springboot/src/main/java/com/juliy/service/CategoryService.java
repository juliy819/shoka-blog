package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.Category;
import com.juliy.model.dto.CategoryAdminDTO;
import com.juliy.model.dto.CategoryDTO;
import com.juliy.model.dto.CategoryOptionDTO;
import com.juliy.model.vo.CategoryVO;
import com.juliy.model.vo.ConditionVO;
import com.juliy.model.vo.PageResult;

import java.util.List;

/**
 * 分类业务接口
 * @author juliy
 * @date 2023/3/25 11:16
 */
public interface CategoryService extends IService<Category> {

    List<CategoryDTO> listCategories();

    /**
     * 查看后台分类列表
     * @param condition 查询条件
     * @return 后台分类列表
     */
    PageResult<CategoryAdminDTO> listCategoriesAdmin(ConditionVO condition);

    List<CategoryOptionDTO> listCategoryOptions(ConditionVO condition);

    void addCategory(CategoryVO categoryVO);

    void deleteCategories(List<Integer> categoryList);

    void updateCategory(CategoryVO categoryVO);


}
