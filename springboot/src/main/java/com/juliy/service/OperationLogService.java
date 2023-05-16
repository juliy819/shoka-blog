package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.OperationLog;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.PageResult;

/**
 * 操作日志服务接口
 * @author juliy
 * @date 2023/5/16 18:14
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 查看操作日志列表
     * @param condition 条件
     * @return 日志列表
     */
    PageResult<OperationLog> listOperationLogVO(ConditionDTO condition);

    /**
     * 保存操作日志
     * @param operationLog 操作日志信息
     */
    void saveOperationLog(OperationLog operationLog);

}