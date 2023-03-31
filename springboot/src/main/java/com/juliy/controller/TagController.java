package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.dto.TagDTO;
import com.juliy.model.vo.*;
import com.juliy.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签控制器
 * @author juliy
 * @date 2023/3/27 12:34
 */
@Tag(name = "标签模块")
@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * 获取所有标签
     * @return 标签列表
     */
    @Operation(summary = "获取所有标签")
    @SaCheckPermission("tag:list")
    @GetMapping("/list")
    public Result<List<TagVO>> getTags() {
        return Result.success(tagService.listTags());
    }

    /**
     * 获取后台标签列表
     * @param condition 查询条件
     * @return 后台标签列表
     */
    @Operation(summary = "获取后台标签列表")
    @SaCheckPermission("tag:list")
    @GetMapping("/admin/list")
    public Result<PageResult<TagAdminVO>> getTagsAdminByPage(ConditionDTO condition) {
        return Result.success(tagService.listTagsAdminByPage(condition));
    }

    /**
     * 获取标签选项
     * @param condition 查询条件
     * @return 标签选项列表
     */
    @Operation(summary = "获取标签选项")
    @SaCheckPermission("tag:list")
    @GetMapping("/admin/listOptions")
    public Result<List<TagOptionVO>> getTagOptions(ConditionDTO condition) {
        return Result.success(tagService.listTagOptions(condition));
    }

    /**
     * 添加标签
     * @param tagDTO 要添加的标签
     */
    @Operation(summary = "添加标签")
    @SaCheckPermission("tag:add")
    @PostMapping
    public Result<?> addTag(@Validated @RequestBody TagDTO tagDTO) {
        tagService.saveOrUpdateTag(tagDTO);
        return Result.success();
    }

    /**
     * 修改标签
     * @param tagDTO 要修改的标签
     */
    @Operation(summary = "修改标签")
    @SaCheckPermission("tag:update")
    @PutMapping
    public Result<?> updateTag(@Validated @RequestBody TagDTO tagDTO) {
        tagService.saveOrUpdateTag(tagDTO);
        return Result.success();
    }

    /**
     * 删除标签
     * @param tagIds 要删除的标签ID列表
     */
    @Operation(summary = "删除标签")
    @SaCheckPermission("tag:delete")
    @DeleteMapping("/{tagIds}")
    public Result<?> deleteTag(@PathVariable Integer[] tagIds) {
        tagService.removeTags(List.of(tagIds));
        return Result.success();
    }
}
