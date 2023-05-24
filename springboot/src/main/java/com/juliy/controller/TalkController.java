package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.juliy.annotation.AccessLimit;
import com.juliy.annotation.OptLogger;
import com.juliy.annotation.VisitLogger;
import com.juliy.enums.LikeTypeEnum;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.dto.TalkDTO;
import com.juliy.model.vo.*;
import com.juliy.service.TalkService;
import com.juliy.strategy.context.LikeStrategyContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.juliy.constant.OptTypeConstant.*;

/**
 * 说说控制器
 * @author juliy
 * @date 2023/5/23 12:18
 */
@Tag(name = "说说模块")
@RequestMapping("/talk")
@RestController
public class TalkController {

    private final TalkService talkService;
    private final LikeStrategyContext likeStrategyContext;

    @Autowired
    public TalkController(TalkService talkService, LikeStrategyContext likeStrategyContext) {
        this.talkService = talkService;
        this.likeStrategyContext = likeStrategyContext;
    }

    /**
     * 查看后台说说列表
     * @param condition 条件
     * @return {@link TalkAdminVO} 后台说说
     */
    @Operation(description = "查看后台说说列表")
    @SaCheckPermission("talk:list")
    @GetMapping("/admin/list")
    public Result<PageResult<TalkAdminVO>> listTalksAdmin(ConditionDTO condition) {
        return Result.success(talkService.listTalksAdmin(condition));
    }

    /**
     * 上传说说图片
     * @param file 文件
     * @return {@link Result<String>}
     */
    @OptLogger(value = UPLOAD)
    @Operation(description = "上传说说图片")
    @SaCheckPermission("talk:upload")
    @PostMapping("/upload")
    public Result<String> uploadTalkImage(@RequestParam("file") MultipartFile file) {
        return Result.success(talkService.saveTalkImage(file));
    }

    /**
     * 添加说说
     * @param talk 说说信息
     * @return {@link Result<>}
     */
    @OptLogger(value = ADD)
    @Operation(description = "添加说说")
    @SaCheckPermission("talk:add")
    @PostMapping
    public Result<?> addTalk(@Validated @RequestBody TalkDTO talk) {
        talkService.addTalk(talk);
        return Result.success();
    }

    /**
     * 删除说说
     * @param talkId 说说id
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @Operation(description = "删除说说")
    @SaCheckPermission("talk:delete")
    @DeleteMapping("/{talkId}")
    public Result<?> deleteTalk(@PathVariable("talkId") Integer talkId) {
        talkService.deleteTalk(talkId);
        return Result.success();
    }

    /**
     * 修改说说
     * @param talk 说说信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @Operation(description = "修改说说")
    @SaCheckPermission("talk:update")
    @PutMapping
    public Result<?> updateTalk(@Validated @RequestBody TalkDTO talk) {
        talkService.updateTalk(talk);
        return Result.success();
    }

    /**
     * 编辑说说
     * @param talkId 说说id
     * @return {@link TalkAdminVO} 后台说说
     */
    @Operation(description = "编辑说说")
    @SaCheckPermission("talk:edit")
    @GetMapping("/admin/edit/{talkId}")
    public Result<TalkAdminInfoVO> editTalk(@PathVariable("talkId") Integer talkId) {
        return Result.success(talkService.editTalk(talkId));
    }

    /**
     * 点赞说说
     * @param talkId 说说id
     * @return {@link Result<>}
     */
    @SaCheckLogin
    @Operation(description = "点赞说说")
    @AccessLimit(seconds = 60, maxCount = 10)
    @SaCheckPermission("talk:like")
    @PostMapping("/{talkId}/like")
    public Result<?> saveTalkLike(@PathVariable("talkId") Integer talkId) {
        likeStrategyContext.executeLikeStrategy(LikeTypeEnum.TALK, talkId);
        return Result.success();
    }

    /**
     * 查看首页说说
     * @return {@link Result<String>}
     */
    @Operation(description = "查看首页说说")
    @GetMapping("/listHome")
    public Result<List<TalkHomeVO>> listTalkHome() {
        return Result.success(talkService.listTalksHome());
    }

    /**
     * 查看说说列表
     * @return {@link Result<TalkVO>}
     */
    @VisitLogger(value = "说说列表")
    @Operation(description = "查看说说列表")
    @GetMapping("/list")
    public Result<PageResult<TalkVO>> listTalkVO() {
        return Result.success(talkService.listTalks());
    }

    /**
     * 查看说说
     * @param talkId 说说id
     * @return {@link Result<TalkVO>}
     */
    @VisitLogger(value = "说说")
    @Operation(description = "查看说说")
    @GetMapping("/{talkId}")
    public Result<TalkVO> getTalkById(@PathVariable("talkId") Integer talkId) {
        return Result.success(talkService.getTalkById(talkId));
    }

}
