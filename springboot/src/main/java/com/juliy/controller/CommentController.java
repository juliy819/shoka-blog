package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.juliy.annotation.AccessLimit;
import com.juliy.annotation.OptLogger;
import com.juliy.enums.LikeTypeEnum;
import com.juliy.model.dto.CheckDTO;
import com.juliy.model.dto.CommentDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.*;
import com.juliy.service.CommentService;
import com.juliy.strategy.context.LikeStrategyContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.juliy.constant.OptTypeConstant.DELETE;
import static com.juliy.constant.OptTypeConstant.UPDATE;

/**
 * 评论控制器
 * @author juliy
 * @date 2023/5/19 12:10
 */
@RestController
@Tag(name = "评论模块")
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final LikeStrategyContext likeStrategyContext;

    @Autowired
    public CommentController(CommentService commentService,
                             LikeStrategyContext likeStrategyContext) {
        this.commentService = commentService;
        this.likeStrategyContext = likeStrategyContext;
    }

    /**
     * 查看后台评论列表
     * @param condition 条件
     * @return {@link Result<CommentAdminVO>} 后台评论
     */
    @Operation(description = "查看后台评论")
    @SaCheckPermission("comment:list")
    @GetMapping("/admin/list")
    public Result<PageResult<CommentAdminVO>> getCommentsAdmin(ConditionDTO condition) {
        return Result.success(commentService.listCommentsAdmin(condition));
    }

    /**
     * 添加评论
     * @param comment 评论信息
     * @return {@link Result<>}
     */
    @SaCheckLogin
    @Operation(description = "添加评论")
    @SaCheckPermission("comment:add")
    @PostMapping
    public Result<?> addComment(@Validated @RequestBody CommentDTO comment) {
        commentService.addComment(comment);
        return Result.success();
    }

    /**
     * 删除评论
     * @param commentIdList 评论id列表
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @Operation(description = "删除评论")
    @SaCheckPermission("comment:delete")
    @DeleteMapping
    public Result<?> deleteComments(@RequestBody List<Integer> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.success();
    }

    /**
     * 审核评论
     * @param check 审核信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @Operation(description = "审核评论")
    @SaCheckPermission("comment:check")
    @PutMapping("/admin/check")
    public Result<?> checkComment(@Validated @RequestBody CheckDTO check) {
        commentService.updateCommentCheck(check);
        return Result.success();
    }

    /**
     * 点赞评论
     * @param commentId 评论id
     * @return {@link Result<>}
     */
    @SaCheckLogin
    @Operation(description = "点赞评论")
    @AccessLimit(seconds = 60, maxCount = 10)
    @SaCheckPermission("comment:like")
    @PostMapping("/{commentId}/like")
    public Result<?> likeComment(@PathVariable("commentId") Integer commentId) {
        likeStrategyContext.executeLikeStrategy(LikeTypeEnum.COMMENT, commentId);
        return Result.success();
    }

    /**
     * 查看最新评论
     * @return {@link List<RecentCommentVO>}
     */
    @Operation(description = "查看最新评论")
    @GetMapping("/recent")
    public Result<List<RecentCommentVO>> getRecentComments() {
        return Result.success(commentService.listRecentComments());
    }

    /**
     * 查看评论
     * @param condition 条件
     * @return {@link Result<CommentVO>}
     */
    @Operation(description = "查看评论")
    @GetMapping("/list")
    public Result<PageResult<CommentVO>> getComments(ConditionDTO condition) {
        return Result.success(commentService.listComments(condition));
    }

    /**
     * 查看回复评论
     * @param commentId 评论id
     * @return {@link Result<ReplyVO>} 回复评论列表
     */
    @Operation(description = "查看回复评论")
    @GetMapping("/{commentId}/reply")
    public Result<List<ReplyVO>> getReply(@PathVariable("commentId") Integer commentId) {
        return Result.success(commentService.listReply(commentId));
    }

}
