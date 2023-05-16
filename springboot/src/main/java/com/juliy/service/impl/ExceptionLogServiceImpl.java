package com.juliy.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.ExceptionLog;
import com.juliy.mapper.ExceptionLogMapper;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.PageResult;
import com.juliy.service.ExceptionLogService;
import com.juliy.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 异常日志服务接口实现类
 * @author juliy
 * @date 2023/5/16 18:14
 */
@Slf4j
@Service
public class ExceptionLogServiceImpl extends ServiceImpl<ExceptionLogMapper, ExceptionLog> implements ExceptionLogService {

    private final ExceptionLogMapper exceptionLogMapper;

    @Autowired
    public ExceptionLogServiceImpl(ExceptionLogMapper exceptionLogMapper) {this.exceptionLogMapper = exceptionLogMapper;}

    @Override
    public PageResult<ExceptionLog> listExceptionLog(ConditionDTO condition) {
        // 查询异常日志数量
        long count = this.count(
                new LambdaQueryWrapper<ExceptionLog>()
                        .like(StrUtil.isNotBlank(condition.getOptModule()),
                              ExceptionLog::getModule, condition.getOptModule())
                        .or()
                        .like(StrUtil.isNotBlank(condition.getKeywords()),
                              ExceptionLog::getDescription, condition.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询异常日志列表
        List<ExceptionLog> exceptionLogList = exceptionLogMapper.selectExceptionLogList(PageUtils.getLimitCurrent(),
                                                                                        PageUtils.getSize(), condition);
        return new PageResult<>(exceptionLogList, count);
    }

    @Override
    public void saveExceptionLog(ExceptionLog exceptionLog) {
        this.save(exceptionLog);
    }
}