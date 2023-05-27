package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色mapper
 * @author juliy
 * @date 2023/5/27 14:09
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}