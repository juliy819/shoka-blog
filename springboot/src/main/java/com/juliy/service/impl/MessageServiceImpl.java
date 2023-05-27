package com.juliy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.Message;
import com.juliy.entity.SiteConfig;
import com.juliy.mapper.MessageMapper;
import com.juliy.model.dto.CheckDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.dto.MessageDTO;
import com.juliy.model.vo.MessageAdminVO;
import com.juliy.model.vo.MessageVO;
import com.juliy.model.vo.PageResult;
import com.juliy.service.MessageService;
import com.juliy.service.SiteConfigService;
import com.juliy.utils.BeanCopyUtils;
import com.juliy.utils.HTMLUtils;
import com.juliy.utils.IpUtils;
import com.juliy.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.juliy.constant.CommonConstant.FALSE;
import static com.juliy.constant.CommonConstant.TRUE;

/**
 * 服务接口实现类
 * @author juliy
 * @date 2023/5/27 15:19
 */
@Slf4j
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    private final MessageMapper messageMapper;
    private final HttpServletRequest request;
    private final SiteConfigService siteConfigService;

    @Autowired
    public MessageServiceImpl(MessageMapper messageMapper,
                              HttpServletRequest request,
                              SiteConfigService siteConfigService) {
        this.messageMapper = messageMapper;
        this.request = request;
        this.siteConfigService = siteConfigService;
    }

    @Override
    public List<MessageVO> listMessages() {
        return messageMapper.selectMessageList();
    }

    @Override
    public PageResult<MessageAdminVO> listMessagesAdmin(ConditionDTO condition) {
        // 查询留言数量
        Long count = messageMapper.selectCount(new LambdaQueryWrapper<Message>()
                                                       .like(StringUtils.hasText(condition.getKeywords()),
                                                             Message::getNickname, condition.getKeywords())
                                                       .eq(Objects.nonNull(condition.getIsCheck()),
                                                           Message::getIsCheck, condition.getIsCheck()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台友链列表
        List<MessageAdminVO> messageBackList = messageMapper.selectMessageAdminList(PageUtils.getLimitCurrent(),
                                                                                    PageUtils.getSize(), condition);
        return new PageResult<>(messageBackList, count);
    }

    @Override
    public void addMessage(MessageDTO message) {
        SiteConfig siteConfig = siteConfigService.getSiteConfig();
        Integer messageCheck = siteConfig.getMessageCheck();
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        Message newMessage = BeanCopyUtils.copyBean(message, Message.class);
        newMessage.setMessageContent(HTMLUtils.filter(message.getMessageContent()));
        newMessage.setIpAddress(ipAddress);
        newMessage.setIsCheck(messageCheck.equals(FALSE) ? TRUE : FALSE);
        newMessage.setIpSource(ipSource);
        messageMapper.insert(newMessage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMessageCheck(CheckDTO check) {
        // 修改留言审核状态
        List<Message> messageList = check.getIdList()
                .stream()
                .map(id -> Message.builder()
                        .id(id)
                        .isCheck(check.getIsCheck())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(messageList);
    }
}