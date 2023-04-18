package com.juliy.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.VisitLog;
import com.juliy.mapper.VisitLogMapper;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.PageResult;
import com.juliy.model.vo.UserViewVO;
import com.juliy.service.VisitLogService;
import com.juliy.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 访问日志服务接口实现类
 * @author juliy
 * @date 2023/4/13 18:01
 */
@Slf4j
@Service
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog> implements VisitLogService {


    private final VisitLogMapper visitLogMapper;

    @Autowired
    public VisitLogServiceImpl(VisitLogMapper visitLogMapper) {
        this.visitLogMapper = visitLogMapper;
    }

    @Override
    public void saveVisitLog(VisitLog visitLog) {
        this.save(visitLog);
    }

    @Override
    public PageResult<VisitLog> listVisitLogs(ConditionDTO condition) {
        long count = this.count(
                new LambdaQueryWrapper<VisitLog>()
                        .like(StrUtil.isNotBlank(condition.getKeywords()), VisitLog::getPage, condition.getKeywords())
        );
        if (count == 0) {
            return new PageResult<>();
        }
        List<VisitLog> visitLogList = visitLogMapper.selectVisitLogs(
                PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(visitLogList, count);
    }

    @Override
    public List<UserViewVO> listUserViews() {
        // 计算起始时间和截止时间
        DateTime startTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        DateTime endTime = DateUtil.endOfDay(new Date());
        return visitLogMapper.selectUserViews(startTime, endTime);
    }
}