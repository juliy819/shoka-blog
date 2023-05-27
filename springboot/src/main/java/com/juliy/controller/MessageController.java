package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.juliy.annotation.AccessLimit;
import com.juliy.annotation.OptLogger;
import com.juliy.annotation.VisitLogger;
import com.juliy.model.dto.CheckDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.dto.MessageDTO;
import com.juliy.model.vo.MessageAdminVO;
import com.juliy.model.vo.MessageVO;
import com.juliy.model.vo.PageResult;
import com.juliy.model.vo.Result;
import com.juliy.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.juliy.constant.OptTypeConstant.DELETE;
import static com.juliy.constant.OptTypeConstant.UPDATE;

/**
 * 留言控制器
 * @author juliy
 * @date 2023/5/27 15:24
 */
@RestController
@RequestMapping("/message")
@Tag(name = "留言模块")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {this.messageService = messageService;}


    /**
     * 获取留言列表
     * @return {@link MessageVO} 留言列表
     */
    @VisitLogger(value = "留言")
    @Operation(summary = "查看留言列表")
    @GetMapping("/list")
    public Result<List<MessageVO>> listMessages() {
        return Result.success(messageService.listMessages());
    }

    /**
     * 获取后台留言列表
     * @param condition 条件
     * @return {@link Result<MessageAdminVO>} 留言列表
     */
    @Operation(summary = "查看后台留言列表")
    @SaCheckPermission("message:list")
    @GetMapping("/admin/list")
    public Result<PageResult<MessageAdminVO>> listMessagesAdmin(ConditionDTO condition) {
        return Result.success(messageService.listMessagesAdmin(condition));
    }

    /**
     * 添加留言
     * @param message 留言信息
     * @return {@link Result<>}
     */
    @AccessLimit(seconds = 60, maxCount = 3)
    @Operation(summary = "添加留言")
    @PostMapping
    public Result<?> addMessage(@Validated @RequestBody MessageDTO message) {
        messageService.addMessage(message);
        return Result.success();
    }

    /**
     * 删除留言
     * @param messageIdList 留言Id列表
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @Operation(summary = "删除留言")
    @SaCheckPermission("message:delete")
    @DeleteMapping
    public Result<?> deleteMessage(@RequestBody List<Integer> messageIdList) {
        messageService.removeByIds(messageIdList);
        return Result.success();
    }

    /**
     * 审核留言
     * @param check 审核信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @Operation(summary = "审核留言")
    @SaCheckPermission("message:check")
    @PutMapping("/admin/check")
    public Result<?> updateMessageCheck(@Validated @RequestBody CheckDTO check) {
        messageService.updateMessageCheck(check);
        return Result.success();
    }

}
