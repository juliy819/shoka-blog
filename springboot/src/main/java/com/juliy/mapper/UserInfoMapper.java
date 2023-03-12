package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息 mapper
 * @author JuLiy
 * @date 2023/3/1 20:00
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
