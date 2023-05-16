package com.juliy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.Comment;
import com.juliy.mapper.CommentMapper;
import com.juliy.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 评论服务接口实现类
 * @author juliy
 * @date 2023/5/16 15:21
 */
@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}