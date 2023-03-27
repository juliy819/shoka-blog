package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.juliy.model.dto.CategoryDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.*;
import com.juliy.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类控制器
 * @author juliy
 * @date 2023/3/25 11:58
 */
@Tag(name = "分类模块")
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获取所有分类
     * @return 分类列表
     */
    @Operation(summary = "获取所有分类")
    @SaCheckPermission("category:list")
    @GetMapping("/list")
    public Result<List<CategoryVO>> getCategories() {
        return Result.success(categoryService.listCategories());
    }

    /**
     * 获取后台分类列表
     * @param condition 查询条件
     * @return 后台分类列表
     */
    @Operation(summary = "获取后台分类列表")
    @SaCheckPermission("category:list")
    @GetMapping("/admin/list")
    public Result<PageResult<CategoryAdminVO>> getCategoriesAdminByPage(ConditionDTO condition) {
        return Result.success(categoryService.listCategoriesAdminByPage(condition));
    }

    /**
     * 获取分类选项
     * @param condition 查询条件
     * @return 分类选项列表
     */
    @Operation(summary = "获取分类选项")
    @SaCheckPermission("category:list")
    @GetMapping("/admin/listOptions")
    public Result<List<CategoryOptionVO>> getCategoryOptions(ConditionDTO condition) {
        return Result.success(categoryService.listCategoryOptions(condition));
    }

    /**
     * 删除分类
     * @param categoryIds 要删除的分类ID列表
     */
    @Operation(summary = "删除分类")
    @SaCheckPermission("category:delete")
    @DeleteMapping("/{categoryIds}")
    public Result<?> deleteCategories(@PathVariable Integer[] categoryIds) {
        categoryService.removeCategories(List.of(categoryIds));
        return Result.success();
    }

    /**
     * 添加分类
     * @param categoryDTO 要添加的分类
     */
    @Operation(summary = "添加分类")
    @SaCheckPermission("category:add")
    @PostMapping
    public Result<?> addCategory(@Validated @RequestBody CategoryDTO categoryDTO) {
        categoryService.saveCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 修改分类
     * @param categoryDTO 要修改的分类
     */
    @Operation(summary = "修改分类")
    @SaCheckPermission("category:update")
    @PutMapping
    public Result<?> updateCategory(@Validated @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }


}
