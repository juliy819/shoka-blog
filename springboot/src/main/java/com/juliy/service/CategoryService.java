package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.Category;
import com.juliy.model.dto.CategoryDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.CategoryAdminVO;
import com.juliy.model.vo.CategoryOptionVO;
import com.juliy.model.vo.CategoryVO;
import com.juliy.model.vo.PageResult;

import java.util.List;

/**
 * 分类业务接口
 * @author juliy
 * @date 2023/3/25 11:16
 */
public interface CategoryService extends IService<Category> {

    /**
     * 获取分类列表
     * @return 分类列表
     */
    List<CategoryVO> listCategories();

    /**
     * 获取后台分类列表
     * @param condition 查询条件
     * @return 后台分类列表
     */
    PageResult<CategoryAdminVO> listCategoriesAdminByPage(ConditionDTO condition);

    /**
     * 获取分类选项列表
     * @param condition 查询条件
     * @return 分类选项列表
     */
    List<CategoryOptionVO> listCategoryOptions(ConditionDTO condition);

    /**
     * 添加或修改分类
     * @param categoryDTO 分类
     */
    void saveOrUpdateCategory(CategoryDTO categoryDTO);

    /**
     * 删除分类
     * @param categoryIds 分类id列表
     */
    void removeCategories(List<Integer> categoryIds);

}
