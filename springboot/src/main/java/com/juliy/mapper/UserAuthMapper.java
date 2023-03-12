package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.UserAuth;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户认证 mapper
 * @author JuLiy
 * @date 2023/3/1 19:22
 */
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuth> {
}
