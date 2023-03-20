package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户mapper
 * @author juliy
 * @date 2023/3/1 19:22
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
