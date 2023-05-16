package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.OperationLog;
import com.juliy.model.dto.ConditionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作日志mapper
 * @author juliy
 * @date 2023/5/16 18:13
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    /**
     * 查询操作日志
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 操作日志列表
     */
    List<OperationLog> selectOperationLogList(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionDTO condition);


}