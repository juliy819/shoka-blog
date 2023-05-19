package com.juliy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.*;
import com.juliy.exception.ServiceException;
import com.juliy.mapper.ArticleMapper;
import com.juliy.mapper.CommentMapper;
import com.juliy.mapper.TalkMapper;
import com.juliy.mapper.UserMapper;
import com.juliy.model.dto.CheckDTO;
import com.juliy.model.dto.CommentDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.*;
import com.juliy.service.CommentService;
import com.juliy.service.RedisService;
import com.juliy.service.SiteConfigService;
import com.juliy.utils.HTMLUtils;
import com.juliy.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.juliy.constant.CommonConstant.FALSE;
import static com.juliy.constant.CommonConstant.TRUE;
import static com.juliy.constant.RedisConstant.COMMENT_LIKE_COUNT;
import static com.juliy.enums.CommentTypeEnum.ARTICLE;
import static com.juliy.enums.CommentTypeEnum.TALK;
import static com.juliy.utils.CommonUtils.checkParamNotEqual;
import static com.juliy.utils.CommonUtils.checkParamNull;

/**
 * 评论服务接口实现类
 * @author juliy
 * @date 2023/5/16 15:21
 */
@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Value("${blog.url}")
    private String blogUrl;

    private final ArticleMapper articleMapper;
    private final TalkMapper talkMapper;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final SiteConfigService siteConfigService;
    private final RedisService redisService;

    @Autowired
    public CommentServiceImpl(ArticleMapper articleMapper,
                              TalkMapper talkMapper,
                              CommentMapper commentMapper,
                              UserMapper userMapper,
                              SiteConfigService siteConfigService,
                              RedisService redisService) {
        this.articleMapper = articleMapper;
        this.talkMapper = talkMapper;
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
        this.siteConfigService = siteConfigService;
        this.redisService = redisService;
    }


    @Override
    public PageResult<CommentAdminVO> listCommentsAdmin(ConditionDTO condition) {
        // 查询后台评论数量
        Long count = commentMapper.countComment(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台评论集合
        List<CommentAdminVO> commentBackVOList = commentMapper.selectCommentAdmin(PageUtils.getLimitCurrent(),
                                                                                  PageUtils.getSize(), condition);
        return new PageResult<>(commentBackVOList, count);
    }

    @Override
    public void addComment(CommentDTO comment) {
        // 校验评论参数
        verifyComment(comment);
        SiteConfig siteConfig = siteConfigService.getSiteConfig();
        Integer commentCheck = siteConfig.getCommentCheck();
        // 过滤标签
        comment.setCommentContent(HTMLUtils.filter(comment.getCommentContent()));
        Comment newComment = Comment.builder()
                .fromUid(StpUtil.getLoginIdAsInt())
                .toUid(comment.getToUid())
                .typeId(comment.getTypeId())
                .commentType(comment.getCommentType())
                .parentId(comment.getParentId())
                .replyId(comment.getReplyId())
                .commentContent(comment.getCommentContent())
                .isCheck(commentCheck.equals(FALSE) ? TRUE : FALSE)
                .build();
        // 保存评论
        commentMapper.insert(newComment);
        // 查询评论用户昵称
        String fromNickname = userMapper.selectOne(new LambdaQueryWrapper<User>()
                                                           .select(User::getNickname)
                                                           .eq(User::getId, StpUtil.getLoginIdAsInt()))
                .getNickname();
        // 通知用户
        if (siteConfig.getEmailNotice().equals(TRUE)) {
            CompletableFuture.runAsync(() -> {
                log.info("异步通知用户");
                // todo 通知用户
                //notice(newComment, fromNickname);
            });
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCommentCheck(CheckDTO check) {
        // 修改评论审核状态
        List<Comment> commentList = check.getIdList()
                .stream()
                .map(id -> Comment.builder().id(id).isCheck(check.getIsCheck()).build())
                .collect(Collectors.toList());
        this.updateBatchById(commentList);
    }

    @Override
    public List<RecentCommentVO> listRecentComments() {
        return commentMapper.selectRecentComment();
    }

    @Override
    public PageResult<CommentVO> listComments(ConditionDTO condition) {
        // 查询父评论数量
        long count = this.count(new LambdaQueryWrapper<Comment>()
                                        .eq(Objects.nonNull(condition.getTypeId()), Comment::getTypeId, condition.getTypeId())
                                        .eq(Comment::getCommentType, condition.getCommentType())
                                        .eq(Comment::getIsCheck, TRUE)
                                        .eq(Comment::getParentId, 0));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询父评论
        List<CommentVO> commentList = commentMapper.selectParentComment(PageUtils.getLimitCurrent(),
                                                                        PageUtils.getSize(), condition);
        if (CollectionUtils.isEmpty(commentList)) {
            return new PageResult<>();
        }
        // 评论点赞
        Map<String, Integer> likeCountMap = redisService.getHashAll(COMMENT_LIKE_COUNT);
        // 父评论id集合
        List<Integer> parentCommentIdList = commentList.stream().map(CommentVO::getId).collect(Collectors.toList());
        // 分组查询每组父评论下的子评论前三条
        List<ReplyVO> replyList = commentMapper.selectReplyByParentIdList(parentCommentIdList);
        // 封装子评论点赞量
        replyList.forEach(item -> item.setLikeCount(Optional.ofNullable(likeCountMap.get(item.getId().toString()))
                                                            .orElse(0)));
        // 根据父评论id生成对应子评论的Map
        Map<Integer, List<ReplyVO>> replyMap = replyList.stream().collect(Collectors.groupingBy(ReplyVO::getParentId));
        // 父评论的回复数量
        List<ReplyCountVO> replyCountList = commentMapper.selectReplyCountByParentId(parentCommentIdList);
        // 转换Map
        Map<Integer, Integer> replyCountMap = replyCountList.stream()
                .collect(Collectors.toMap(ReplyCountVO::getCommentId, ReplyCountVO::getReplyCount));
        // 封装评论数据
        commentList.forEach(item -> {
            item.setLikeCount(Optional.ofNullable(likeCountMap.get(item.getId().toString())).orElse(0));
            item.setReplyList(replyMap.get(item.getId()));
            item.setReplyCount(Optional.ofNullable(replyCountMap.get(item.getId())).orElse(0));
        });
        return new PageResult<>(commentList, count);
    }

    @Override
    public List<ReplyVO> listReply(Integer commentId) {
        // 分页查询子评论
        List<ReplyVO> replyVOList = commentMapper.selectReplyByParentId(PageUtils.getLimitCurrent(),
                                                                        PageUtils.getSize(), commentId);
        // 子评论点赞Map
        Map<String, Integer> likeCountMap = redisService.getHashAll(COMMENT_LIKE_COUNT);
        replyVOList.forEach(item -> item.setLikeCount(likeCountMap.get(item.getId().toString())));
        return replyVOList;
    }

    /**
     * 评论验证
     * @param comment 评论
     */
    private void verifyComment(CommentDTO comment) {
        if (comment.getCommentType().equals(ARTICLE.getType())) {
            Article article = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                                                              .select(Article::getId)
                                                              .eq(Article::getId, comment.getTypeId()));
            checkParamNull(article, "文章不存在");
        }
        if (comment.getCommentType().equals(TALK.getType())) {
            Talk talk = talkMapper.selectOne(new LambdaQueryWrapper<Talk>()
                                                     .select(Talk::getId)
                                                     .eq(Talk::getId, comment.getTypeId()));
            checkParamNull(talk, "说说不存在");
        }
        // 评论为子评论，判断回复的评论和用户是否存在
        if (Objects.nonNull(comment.getParentId()) && comment.getParentId() != 0) {
            Integer parentId = comment.getParentId();
            // 判断父评论是否存在
            Comment parentComment = commentMapper.selectOne(new LambdaQueryWrapper<Comment>()
                                                                    .select(Comment::getId, Comment::getParentId,
                                                                            Comment::getCommentType)
                                                                    .eq(Comment::getId, parentId));
            checkParamNull(parentComment, "父评论不存在");
            checkParamNotEqual(parentComment.getParentId(), 0, "当前评论为子评论，不能作为父评论");
            checkParamNotEqual(comment.getCommentType(), parentComment.getCommentType(), "只能以同类型的评论作为父评论");
            // 判断回复的评论和用户是否存在
            Comment replyComment = commentMapper.selectOne(new LambdaQueryWrapper<Comment>()
                                                                   .select(Comment::getId, Comment::getParentId,
                                                                           Comment::getFromUid, Comment::getCommentType)
                                                                   .eq(Comment::getId, comment.getReplyId()));
            User toUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                                                       .select(User::getId)
                                                       .eq(User::getId, comment.getToUid()));
            checkParamNull(replyComment, "回复的评论不存在");
            checkParamNull(toUser, "回复的用户不存在");
            checkParamNotEqual(comment.getCommentType(), replyComment.getCommentType(), "只能回复同类型的评论");
            if (replyComment.getParentId() != 0) {
                checkParamNotEqual(replyComment.getParentId(), parentId, "提交的评论父id与当前回复评论父id不一致");
            }
            checkParamNotEqual(replyComment.getFromUid(), comment.getToUid(), "提交的评论toUid与当前回复评论fromUid不一致");

            // 只能回复当前父评论及其子评论
            List<Integer> replyIdList = commentMapper.selectCommentIdByParentId(parentId);
            replyIdList.add(parentId);
            if (!replyIdList.contains(comment.getReplyId())) {
                throw new ServiceException("当前父评论下不存在该子评论");
            }
        }
    }
}