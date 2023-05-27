package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.Message;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.MessageAdminVO;
import com.juliy.model.vo.MessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 留言mapper
 * @author juliy
 * @date 2023/5/27 15:12
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 查询留言列表
     * @return 留言列表
     */
    List<MessageVO> selectMessageList();

    /**
     * 查询后台留言列表
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 后台留言列表
     */
    List<MessageAdminVO> selectMessageAdminList(@Param("current") Long current, @Param("size") Long size,
                                                @Param("condition") ConditionDTO condition);


}