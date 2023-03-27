package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.juliy.model.dto.CategoryAdminDTO;
import com.juliy.model.dto.CategoryDTO;
import com.juliy.model.dto.CategoryOptionDTO;
import com.juliy.model.vo.CategoryVO;
import com.juliy.model.vo.ConditionVO;
import com.juliy.model.vo.PageResult;
import com.juliy.model.vo.Result;
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
    @GetMapping("/categories/list")
    public Result<List<CategoryDTO>> listCategories() {
        return Result.success(categoryService.listCategories());
    }

    /**
     * 获取后台分类列表
     * @param condition 查询条件
     * @return 后台分类列表
     */
    @Operation(summary = "获取后台分类列表")
    @SaCheckPermission("category:list")
    @GetMapping("/admin/categories")
    public Result<PageResult<CategoryAdminDTO>> listCategoriesAdmin(ConditionVO condition) {
        return Result.success(categoryService.listCategoriesAdmin(condition));
    }

    /**
     * 获取分类选项
     * @param condition 查询条件
     * @return 分类选项列表
     */
    @Operation(summary = "获取分类选项")
    @SaCheckPermission("category:list")
    @GetMapping("/admin/categories/options")
    public Result<List<CategoryOptionDTO>> listCategoryOptions(ConditionVO condition) {
        return Result.success(categoryService.listCategoryOptions(condition));
    }

    /**
     * 删除分类
     * @param categoryList 要删除的分类ID列表
     */
    @Operation(summary = "删除分类")
    @SaCheckPermission("category:delete")
    @DeleteMapping("/admin/categories")
    public Result<?> deleteCategories(@RequestBody List<Integer> categoryList) {
        categoryService.deleteCategories(categoryList);
        return Result.success();
    }

    /**
     * 添加分类
     * @param categoryVO 要添加的分类
     */
    @Operation(summary = "添加分类")
    @SaCheckPermission("category:add")
    @PostMapping("/admin/categories")
    public Result<?> addCategory(@Validated @RequestBody CategoryVO categoryVO) {
        categoryService.addCategory(categoryVO);
        return Result.success();
    }

    /**
     * 修改分类
     * @param categoryVO 要修改的分类
     */
    @Operation(summary = "修改分类")
    @SaCheckPermission("category:update")
    @PutMapping("/admin/categories")
    public Result<?> updateCategory(@Validated @RequestBody CategoryVO categoryVO) {
        categoryService.updateCategory(categoryVO);
        return Result.success();
    }


}
