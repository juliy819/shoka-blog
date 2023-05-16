package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.ExceptionLog;
import com.juliy.model.dto.ConditionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 异常日志mapper
 * @author juliy
 * @date 2023/5/16 18:13
 */
@Mapper
public interface ExceptionLogMapper extends BaseMapper<ExceptionLog> {

    /**
     * 查询异常日志
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 异常日志列表
     */
    List<ExceptionLog> selectExceptionLogList(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionDTO condition);
}