package com.juliy.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.juliy.entity.ExceptionLog;
import com.juliy.entity.OperationLog;
import com.juliy.entity.VisitLog;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.PageResult;
import com.juliy.model.vo.Result;
import com.juliy.service.ExceptionLogService;
import com.juliy.service.OperationLogService;
import com.juliy.service.VisitLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日志控制器
 * @author juliy
 * @date 2023/5/27 22:35
 */
@RestController
@RequestMapping("/log")
@Tag(name = "日志模块")
public class LogController {

    private final OperationLogService operationLogService;
    private final ExceptionLogService exceptionLogService;
    private final VisitLogService visitLogService;

    @Autowired
    public LogController(OperationLogService operationLogService,
                         ExceptionLogService exceptionLogService,
                         VisitLogService visitLogService) {
        this.operationLogService = operationLogService;
        this.exceptionLogService = exceptionLogService;
        this.visitLogService = visitLogService;
    }

    /**
     * 查看操作日志
     * @param condition 条件
     * @return {@link OperationLog} 操作日志
     */
    @Operation(summary = "查看操作日志")
    @SaCheckPermission("log:operation:list")
    @GetMapping("/operation")
    public Result<PageResult<OperationLog>> listOperationLogs(ConditionDTO condition) {
        return Result.success(operationLogService.listOperationLogs(condition));
    }

    /**
     * 删除操作日志
     * @param logIdList 日志id集合
     * @return {@link Result<>}
     */
    @Operation(summary = "删除操作日志")
    @SaCheckPermission("log:operation:delete")
    @DeleteMapping("/operation")
    public Result<?> deleteOperationLogs(@RequestBody List<Integer> logIdList) {
        operationLogService.removeByIds(logIdList);
        return Result.success();
    }

    /**
     * 查看异常日志
     * @param condition 条件
     * @return {@link Result<ExceptionLog>} 异常日志列表
     */
    @Operation(summary = "查看异常日志")
    @SaCheckPermission("log:exception:list")
    @GetMapping("/exception")
    public Result<PageResult<ExceptionLog>> listExceptionLogs(ConditionDTO condition) {
        return Result.success(exceptionLogService.listExceptionLogs(condition));
    }

    /**
     * 删除异常日志
     * @param logIdList 日志id集合
     * @return {@link Result<>}
     */
    @Operation(summary = "删除异常日志")
    @SaCheckPermission("log:exception:delete")
    @DeleteMapping("/exception")
    public Result<?> deleteExceptionLogs(@RequestBody List<Integer> logIdList) {
        exceptionLogService.removeByIds(logIdList);
        return Result.success();
    }

    /**
     * 查看访问日志
     * @param condition 条件
     * @return {@link Result<VisitLog>} 访问日志列表
     */
    @Operation(summary = "查看访问日志")
    @SaCheckPermission("log:visit:list")
    @GetMapping("/visit")
    public Result<PageResult<VisitLog>> listVisitLogs(ConditionDTO condition) {
        return Result.success(visitLogService.listVisitLogs(condition));
    }

    /**
     * 删除访问日志
     * @param logIdList 日志id集合
     * @return {@link Result<>}
     */
    @Operation(summary = "删除访问日志")
    @SaCheckPermission("log:visit:delete")
    @DeleteMapping("/visit")
    public Result<?> deleteVisitLogs(@RequestBody List<Integer> logIdList) {
        visitLogService.removeByIds(logIdList);
        return Result.success();
    }

}
