package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.Talk;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper
 * @author juliy
 * @date 2023/5/18 23:00
 */
@Mapper
public interface TalkMapper extends BaseMapper<Talk> {
}