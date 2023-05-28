package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.User;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.UserAdminVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户mapper
 * @author juliy
 * @date 2023/3/1 19:22
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户后台数量
     * @param condition 条件
     * @return 用户数量
     */
    Long countUser(@Param("condition") ConditionDTO condition);

    /**
     * 查询后台用户列表
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 用户后台列表
     */
    List<UserAdminVO> selectUserAdmin(@Param("current") Long current, @Param("size") Long size,
                                      @Param("condition") ConditionDTO condition);

}
