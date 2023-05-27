package com.juliy.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.OperationLog;
import com.juliy.mapper.OperationLogMapper;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.PageResult;
import com.juliy.service.OperationLogService;
import com.juliy.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志服务接口实现类
 * @author juliy
 * @date 2023/5/16 18:15
 */
@Slf4j
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    private final OperationLogMapper operationLogMapper;

    @Autowired
    public OperationLogServiceImpl(OperationLogMapper operationLogMapper) {this.operationLogMapper = operationLogMapper;}


    @Override
    public PageResult<OperationLog> listOperationLogs(ConditionDTO condition) {
        // 查询操作日志数量
        long count = this.count(
                new LambdaQueryWrapper<OperationLog>()
                        .like(StrUtil.isNotBlank(condition.getOptModule()),
                              OperationLog::getModule, condition.getOptModule())
                        .or()
                        .like(StrUtil.isNotBlank(condition.getKeywords()),
                              OperationLog::getDescription, condition.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询异常日志列表
        List<OperationLog> operationLogList = operationLogMapper.selectOperationLogList(PageUtils.getLimitCurrent(),
                                                                                        PageUtils.getSize(), condition);
        return new PageResult<>(operationLogList, count);
    }

    @Override
    public void saveOperationLog(OperationLog operationLog) {
        this.save(operationLog);
    }
}