package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.Role;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.RoleVO;
import com.juliy.model.vo.UserRoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色mapper
 * @author juliy
 * @date 2023/3/15 17:07
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询用户角色
     * @param userId 用户id
     * @return 角色
     */
    List<String> selectRoleListByUserId(@Param("userId") Object userId);

    /**
     * 查询后台角色数量
     * @param condition 查询条件
     * @return 后台角色数量
     */
    Long countRole(@Param("condition") ConditionDTO condition);

    /**
     * 查询后台角色列表
     * @param current   页码
     * @param size      大小
     * @param condition 查询条件
     * @return 后台角色列表
     */
    List<RoleVO> selectRoleList(@Param("current") Long current, @Param("size") Long size,
                                @Param("condition") ConditionDTO condition);

    /**
     * 查询用户角色选项
     * @return 用户角色选项
     */
    List<UserRoleVO> selectUserRoleList();

}
