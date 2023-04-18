package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.User;
import com.juliy.model.vo.AdminUserInfoVO;

/**
 * 用户服务接口
 * @author juliy
 * @date 2023/3/15 17:31
 */
public interface UserService extends IService<User> {

    /**
     * 获取后台用户信息
     * @return 后台用户信息
     */
    AdminUserInfoVO getAdminUserInfo();
}
