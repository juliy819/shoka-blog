package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.Message;
import com.juliy.model.dto.CheckDTO;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.dto.MessageDTO;
import com.juliy.model.vo.MessageAdminVO;
import com.juliy.model.vo.MessageVO;
import com.juliy.model.vo.PageResult;

import java.util.List;

/**
 * 留言服务接口
 * @author juliy
 * @date 2023/5/27 15:19
 */
public interface MessageService extends IService<Message> {

    /**
     * 查看留言列表
     * @return 留言列表
     */
    List<MessageVO> listMessages();

    /**
     * 查看后台留言列表
     * @param condition 条件
     * @return 后台留言列表
     */
    PageResult<MessageAdminVO> listMessagesAdmin(ConditionDTO condition);

    /**
     * 添加留言
     * @param message 留言
     */
    void addMessage(MessageDTO message);

    /**
     * 审核留言
     * @param check 审核信息
     */
    void updateMessageCheck(CheckDTO check);

}