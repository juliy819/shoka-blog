package com.juliy.service;

import com.juliy.model.dto.MailDTO;

/**
 * 邮件服务接口
 * @author juliy
 * @date 2023/5/27 14:43
 */
public interface EmailService {

    /**
     * 发送简单邮件
     * @param mailDTO 邮件信息
     */
    void sendSimpleMail(MailDTO mailDTO);

    /**
     * 发送HTML邮件
     * @param mailDTO 邮件信息
     */
    void sendHtmlMail(MailDTO mailDTO);
}
