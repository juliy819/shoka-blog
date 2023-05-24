package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.Talk;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.TalkAdminInfoVO;
import com.juliy.model.vo.TalkAdminVO;
import com.juliy.model.vo.TalkVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mapper
 * @author juliy
 * @date 2023/5/18 23:00
 */
@Mapper
public interface TalkMapper extends BaseMapper<Talk> {

    /**
     * 查询后台说说列表
     * @param current   页码
     * @param size      大小
     * @param condition 查询条件
     * @return 后台说说列表
     */
    List<TalkAdminVO> selectTalksAdmin(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionDTO condition);

    /**
     * 查询说说列表
     * @param current 页码
     * @param size    大小
     * @return 说说列表
     */
    List<TalkVO> selectTalks(@Param("current") Long current, @Param("size") Long size);

    /**
     * 根据id查询后台说说
     * @param talkId 说说id
     * @return 后台说说
     */
    TalkAdminInfoVO selectTalkAdminById(Integer talkId);

    /**
     * 根据id查询说说
     * @param talkId 说说id
     * @return 说说
     */
    TalkVO selectTalkById(Integer talkId);

}