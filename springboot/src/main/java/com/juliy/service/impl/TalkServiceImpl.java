package com.juliy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.Talk;
import com.juliy.enums.FilePathEnum;
import com.juliy.mapper.CommentMapper;
import com.juliy.mapper.TalkMapper;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.dto.TalkDTO;
import com.juliy.model.vo.*;
import com.juliy.service.FileService;
import com.juliy.service.RedisService;
import com.juliy.service.TalkService;
import com.juliy.utils.BeanCopyUtils;
import com.juliy.utils.CommonUtils;
import com.juliy.utils.HTMLUtils;
import com.juliy.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.juliy.constant.RedisConstant.TALK_LIKE_COUNT;
import static com.juliy.enums.ArticleStatusEnum.PUBLIC;
import static com.juliy.enums.CommentTypeEnum.TALK;

/**
 * 说说服务接口实现类
 * @author juliy
 * @date 2023/5/23 11:43
 */
@Slf4j
@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk> implements TalkService {

    private final TalkMapper talkMapper;
    private final RedisService redisService;
    private final CommentMapper commentMapper;
    private final FileService fileService;

    @Autowired
    public TalkServiceImpl(TalkMapper talkMapper,
                           RedisService redisService,
                           CommentMapper commentMapper,
                           FileService fileService) {
        this.talkMapper = talkMapper;
        this.redisService = redisService;
        this.commentMapper = commentMapper;
        this.fileService = fileService;
    }

    @Override
    public PageResult<TalkAdminVO> listTalksAdmin(ConditionDTO condition) {
        // 查询说说数量
        long count = this.count(
                new LambdaQueryWrapper<Talk>()
                        .eq(Objects.nonNull(condition.getStatus()), Talk::getStatus, condition.getStatus()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询说说列表
        List<TalkAdminVO> talkList = talkMapper.selectTalksAdmin(PageUtils.getLimitCurrent(), PageUtils.getSize(),
                                                                 condition);
        talkList.forEach(item -> {
            // 转换图片格式
            if (Objects.nonNull(item.getImages())) {
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
        });
        return new PageResult<>(talkList, count);
    }

    @Override
    public List<TalkHomeVO> listTalksHome() {
        // 查询最新5条说说
        List<Talk> talkList = this.list(new LambdaQueryWrapper<Talk>()
                                                .select(Talk::getId, Talk::getTalkContent)
                                                .eq(Talk::getStatus, PUBLIC.getStatus())
                                                .orderByDesc(Talk::getIsTop)
                                                .orderByDesc(Talk::getId)
                                                .last("limit 5"));
        List<TalkHomeVO> talkHomeList = BeanCopyUtils.copyBeanList(talkList, TalkHomeVO.class);
        // 限制说说内容长度
        talkHomeList.forEach(item -> {
            if (item.getTalkContent().length() > 200) {
                item.setTalkContent(HTMLUtils.deleteHtmlTag(item.getTalkContent().substring(0, 200)));
            } else {
                item.setTalkContent(HTMLUtils.deleteHtmlTag(item.getTalkContent()));
            }
        });
        return talkHomeList;
    }

    @Override
    public PageResult<TalkVO> listTalks() {
        // 查询说说总量
        Long count = talkMapper.selectCount((new LambdaQueryWrapper<Talk>()
                .eq(Talk::getStatus, PUBLIC.getStatus())));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询说说
        List<TalkVO> talkList = talkMapper.selectTalks(PageUtils.getLimitCurrent(), PageUtils.getSize());
        // 查询说说评论量
        List<Integer> talkIdList = talkList.stream()
                .map(TalkVO::getId)
                .collect(Collectors.toList());
        List<CommentCountVO> commentCountVOList = commentMapper.selectCommentCountByTypeId(talkIdList, TALK.getType());
        Map<Integer, Integer> commentCountMap = commentCountVOList.stream()
                .collect(Collectors.toMap(CommentCountVO::getId, CommentCountVO::getCommentCount));
        // 查询说说点赞量
        Map<String, Integer> likeCountMap = redisService.getHashAll(TALK_LIKE_COUNT);
        // 封装说说
        talkList.forEach(item -> {
            item.setLikeCount(Optional.ofNullable(likeCountMap.get(item.getId().toString())).orElse(0));
            item.setCommentCount(Optional.ofNullable(commentCountMap.get(item.getId())).orElse(0));
            // 转换图片格式
            if (Objects.nonNull(item.getImages())) {
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
        });
        return new PageResult<>(talkList, count);
    }

    @Override
    public void addTalk(TalkDTO talk) {
        Talk newTalk = BeanCopyUtils.copyBean(talk, Talk.class);
        newTalk.setUserId(StpUtil.getLoginIdAsInt());
        this.save(newTalk);
    }

    @Override
    public void deleteTalk(Integer talkId) {
        this.removeById(talkId);
    }

    @Override
    public void updateTalk(TalkDTO talk) {
        Talk newTalk = BeanCopyUtils.copyBean(talk, Talk.class);
        newTalk.setUserId(StpUtil.getLoginIdAsInt());
        this.updateById(newTalk);
    }

    @Override
    public TalkAdminInfoVO editTalk(Integer talkId) {
        TalkAdminInfoVO talkBackVO = talkMapper.selectTalkAdminById(talkId);
        // 转换图片格式
        if (Objects.nonNull(talkBackVO.getImages())) {
            talkBackVO.setImgList(CommonUtils.castList(JSON.parseObject(talkBackVO.getImages(), List.class), String.class));
        }
        return talkBackVO;
    }

    @Override
    public TalkVO getTalkById(Integer talkId) {
        // 查询说说信息
        TalkVO talk = talkMapper.selectTalkById(talkId);
        CommonUtils.checkParamNull(talk, "说说不存在");
        // 查询说说点赞量
        Integer likeCount = redisService.getHash(TALK_LIKE_COUNT, talkId.toString());
        talk.setLikeCount(Optional.ofNullable(likeCount).orElse(0));
        // 转换图片格式
        if (Objects.nonNull(talk.getImages())) {
            talk.setImgList(CommonUtils.castList(JSON.parseObject(talk.getImages(), List.class), String.class));
        }
        return talk;
    }

    @Override
    public String saveTalkImage(MultipartFile file) {
        return fileService.saveFile(file, FilePathEnum.TALK);
    }
}