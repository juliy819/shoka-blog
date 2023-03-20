package com.juliy.service;

import com.juliy.model.vo.BackendUserInfoVO;

/**
 * 用户服务接口
 * @author juliy
 * @date 2023/3/15 17:31
 */
public interface UserService {

    /**
     * 获取后台用户信息
     * @return 后台用户信息
     */
    BackendUserInfoVO getBackendUserInfo();
}
