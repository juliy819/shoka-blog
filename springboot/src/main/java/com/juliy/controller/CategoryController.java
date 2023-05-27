package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.juliy.annotation.OptLogger;
import com.juliy.annotation.VisitLogger;
import com.juliy.model.dto.CategoryDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.*;
import com.juliy.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.juliy.constant.OptTypeConstant.*;

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
    @OptLogger(value = DELETE)
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
    @OptLogger(value = ADD)
    @PostMapping
    public Result<?> addCategory(@Validated @RequestBody CategoryDTO categoryDTO) {
        categoryService.saveOrUpdateCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 修改分类
     * @param categoryDTO 要修改的分类
     */
    @Operation(summary = "修改分类")
    @SaCheckPermission("category:update")
    @OptLogger(value = UPDATE)
    @PutMapping
    public Result<?> updateCategory(@Validated @RequestBody CategoryDTO categoryDTO) {
        categoryService.saveOrUpdateCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 查看分类下的文章
     * @param condition 查询条件
     * @return 文章列表
     */
    @VisitLogger(value = "分类文章")
    @Operation(summary = "查看分类下的文章")
    @GetMapping("/article")
    public Result<ArticleConditionList> listCategoryArticles(ConditionDTO condition) {
        return Result.success(categoryService.listCategoryArticles(condition));
    }
}
