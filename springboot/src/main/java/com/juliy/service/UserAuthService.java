package com.juliy.service;

import com.juliy.model.dto.LoginDTO;
import com.juliy.model.dto.RegisterDTO;

/**
 * 登录业务接口
 * @author JuLiy
 * @date 2023/3/1 13:55
 */
public interface UserAuthService {

    /**
     * 用户登录
     * @param loginInfo 登录参数
     * @return token
     */
    String login(LoginDTO loginInfo);

    /**
     * 用户注册
     * @param register 注册信息
     */
    void register(RegisterDTO register);
}
