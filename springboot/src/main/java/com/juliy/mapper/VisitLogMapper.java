package com.juliy.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.VisitLog;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.UserViewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 访问日志mapper
 * @author juliy
 * @date 2023/4/13 17:59
 */
@Mapper
public interface VisitLogMapper extends BaseMapper<VisitLog> {

    /**
     * 查询访问日志
     * @param current   页码
     * @param size      大小
     * @param condition 查询条件
     * @return 访问日志列表
     */
    List<VisitLog> selectVisitLogs(@Param("current") Long current, @Param("size") Long size, @Param("condition")
    ConditionDTO condition);

    /**
     * 获取7天用户访问数据
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 用户访问数据
     */
    List<UserViewVO> selectUserViews(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);
}