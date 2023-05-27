package com.juliy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
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
import com.juliy.model.dto.MailDTO;
import com.juliy.model.vo.*;
import com.juliy.service.CommentService;
import com.juliy.service.RedisService;
import com.juliy.service.SiteConfigService;
import com.juliy.utils.HTMLUtils;
import com.juliy.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.juliy.constant.CommonConstant.*;
import static com.juliy.constant.MqConstant.*;
import static com.juliy.constant.RedisConstant.COMMENT_LIKE_COUNT;
import static com.juliy.enums.CommentTypeEnum.*;
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
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CommentServiceImpl(ArticleMapper articleMapper,
                              TalkMapper talkMapper,
                              CommentMapper commentMapper,
                              UserMapper userMapper,
                              SiteConfigService siteConfigService,
                              RedisService redisService,
                              RabbitTemplate rabbitTemplate) {
        this.articleMapper = articleMapper;
        this.talkMapper = talkMapper;
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
        this.siteConfigService = siteConfigService;
        this.redisService = redisService;
        this.rabbitTemplate = rabbitTemplate;
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
                notice(newComment, fromNickname);
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

    /**
     * 评论通知
     * @param comment      评论
     * @param fromNickname 评论用户昵称
     */
    private void notice(Comment comment, String fromNickname) {
        // 自己回复自己不用提醒
        if (comment.getFromUid().equals(comment.getToUid())) {
            return;
        }
        String title = "友链";
        Integer toUid = BLOGGER_ID;
        // 父评论
        if (Objects.isNull(comment.getParentId())) {
            if (comment.getCommentType().equals(ARTICLE.getType())) {
                Article article = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                                                                  .select(Article::getArticleTitle, Article::getUserId)
                                                                  .eq(Article::getId, comment.getTypeId()));
                title = article.getArticleTitle();
                toUid = article.getUserId();
            }
            if (comment.getCommentType().equals(TALK.getType())) {
                title = "说说";
                toUid = talkMapper.selectOne(new LambdaQueryWrapper<Talk>()
                                                     .select(Talk::getUserId)
                                                     .eq(Talk::getId, comment.getTypeId()))
                        .getUserId();
            }
            // 自己评论自己的作品，不用提醒
            if (comment.getFromUid().equals(toUid)) {
                return;
            }
        } else {
            // 子评论
            toUid = comment.getToUid();
            if (comment.getCommentType().equals(ARTICLE.getType())) {
                title = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                                                        .select(Article::getArticleTitle)
                                                        .eq(Article::getId, comment.getTypeId()))
                        .getArticleTitle();
            }
            if (comment.getCommentType().equals(TALK.getType())) {
                title = "说说";
            }
        }
        // 查询回复用户邮箱、昵称、id
        User toUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                                                   .select(User::getEmail, User::getNickname)
                                                   .eq(User::getId, toUid));
        // 邮箱不为空
        if (StringUtils.hasText(toUser.getEmail())) {
            sendEmail(comment, toUser, title, fromNickname);
        }
    }

    /**
     * 发送邮件
     * @param comment      评论
     * @param toUser       回复用户信息
     * @param title        标题
     * @param fromNickname 评论用户昵称
     */
    private void sendEmail(Comment comment, User toUser, String title, String fromNickname) {
        MailDTO mailDTO = new MailDTO();
        if (comment.getIsCheck().equals(TRUE)) {
            Map<String, Object> contentMap = new HashMap<>(7);
            // 评论链接
            String typeId = Optional.ofNullable(comment.getTypeId())
                    .map(Object::toString)
                    .orElse("");
            String url = blogUrl + getCommentPath(comment.getCommentType()) + typeId;
            // 父评论则提醒作者
            if (Objects.isNull(comment.getParentId())) {
                mailDTO.setToEmail(toUser.getEmail());
                mailDTO.setSubject(COMMENT_REMIND);
                mailDTO.setTemplate(AUTHOR_TEMPLATE);
                String createTime = DateUtil.formatLocalDateTime(comment.getCreateTime());
                contentMap.put("time", createTime);
                contentMap.put("url", url);
                contentMap.put("title", title);
                contentMap.put("nickname", fromNickname);
                contentMap.put("content", comment.getCommentContent());
                mailDTO.setContentMap(contentMap);
            } else {
                // 子评论则回复的是用户提醒该用户
                Comment parentComment = commentMapper.selectOne(new LambdaQueryWrapper<Comment>()
                                                                        .select(Comment::getCommentContent, Comment::getCreateTime)
                                                                        .eq(Comment::getId, comment.getReplyId()));
                mailDTO.setToEmail(toUser.getEmail());
                mailDTO.setSubject(COMMENT_REMIND);
                mailDTO.setTemplate(USER_TEMPLATE);
                contentMap.put("url", url);
                contentMap.put("title", title);
                String createTime = DateUtil.formatLocalDateTime(comment.getCreateTime());
                contentMap.put("time", createTime);
                // 被回复用户昵称
                contentMap.put("toUser", toUser.getNickname());
                // 评论用户昵称
                contentMap.put("fromUser", fromNickname);
                // 被回复的评论内容
                contentMap.put("parentComment", parentComment.getCommentContent());
                // 回复评论内容
                contentMap.put("replyComment", comment.getCommentContent());
                mailDTO.setContentMap(contentMap);
            }
            // 发送HTML邮件
            rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, EMAIL_HTML_KEY, mailDTO);
        } else {
            // 审核提醒
            String adminEmail = userMapper.selectOne(new LambdaQueryWrapper<User>()
                                                             .select(User::getEmail)
                                                             .eq(User::getId, BLOGGER_ID)).getEmail();
            mailDTO.setToEmail(adminEmail);
            mailDTO.setSubject(CHECK_REMIND);
            mailDTO.setContent("您收到一条新的回复，请前往后台管理页面审核");
            // 发送普通邮件
            rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, EMAIL_SIMPLE_KEY, mailDTO);
        }
    }
}