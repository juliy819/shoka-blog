package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.Comment;
import com.juliy.model.dto.CheckDTO;
import com.juliy.model.dto.CommentDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.*;

import java.util.List;

/**
 * 评论服务接口
 * @author juliy
 * @date 2023/5/16 15:21
 */
public interface CommentService extends IService<Comment> {

    /**
     * 查看后台评论列表
     * @param condition 条件
     * @return 后台评论列表
     */
    PageResult<CommentAdminVO> listCommentsAdmin(ConditionDTO condition);

    /**
     * 添加评论
     * @param comment 评论信息
     */
    void addComment(CommentDTO comment);

    /**
     * 审核评论
     * @param check 审核信息
     */
    void updateCommentCheck(CheckDTO check);

    /**
     * 查看最新评论
     * @return 最新评论
     */
    List<RecentCommentVO> listRecentComments();

    /**
     * 查看评论
     * @param condition 条件
     * @return 评论列表
     */
    PageResult<CommentVO> listComments(ConditionDTO condition);

    /**
     * 查看回复评论
     * @param commentId 评论id
     * @return 回复评论
     */
    List<ReplyVO> listReply(Integer commentId);

}