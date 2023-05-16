package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论mapper
 * @author juliy
 * @date 2023/5/16 15:21
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}