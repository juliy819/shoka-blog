package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.ExceptionLog;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.PageResult;

/**
 * 异常日志服务接口
 * @author juliy
 * @date 2023/5/16 18:14
 */
public interface ExceptionLogService extends IService<ExceptionLog> {

    /**
     * 查看异常日志列表
     * @param condition 条件
     * @return 日志列表
     */
    PageResult<ExceptionLog> listExceptionLog(ConditionDTO condition);

    /**
     * 保存异常日志
     * @param exceptionLog 异常日志信息
     */
    void saveExceptionLog(ExceptionLog exceptionLog);

}