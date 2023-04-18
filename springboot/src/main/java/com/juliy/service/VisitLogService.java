package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.VisitLog;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.PageResult;
import com.juliy.model.vo.UserViewVO;

import java.util.List;

/**
 * 访问日志服务接口
 * @author juliy
 * @date 2023/4/13 18:01
 */
public interface VisitLogService extends IService<VisitLog> {

    /**
     * 保存访问日志
     * @param visitLog 访问日志
     */
    void saveVisitLog(VisitLog visitLog);

    /**
     * 获取访问日志列表
     * @param condition 查询条件
     * @return 访问日志列表
     */
    PageResult<VisitLog> listVisitLogs(ConditionDTO condition);

    /**
     * 获取近七天用户访问数据
     * @return 用户访问数据
     */
    List<UserViewVO> listUserViews();

}