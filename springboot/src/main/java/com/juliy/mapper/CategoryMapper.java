package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.Category;
import com.juliy.model.dto.CategoryAdminDTO;
import com.juliy.model.dto.CategoryDTO;
import com.juliy.model.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类 mapper
 * @author juliy
 * @date 2023/3/25 10:36
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询分类列表
     * @return 分类列表
     */
    List<CategoryDTO> selectCategories();

    /**
     * 查询后台分类列表
     * @param current   页码
     * @param size      规模
     * @param condition 查询条件
     * @return 后台分类列表
     */
    List<CategoryAdminDTO> selectCategoriesAdmin(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);


}
