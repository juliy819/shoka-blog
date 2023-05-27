package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.User;
import com.juliy.model.dto.EmailDTO;
import com.juliy.model.dto.PasswordDTO;
import com.juliy.model.dto.UserInfoDTO;
import com.juliy.model.vo.AdminUserInfoVO;
import com.juliy.model.vo.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 获取用户信息
     * @return 用户信息
     */
    UserInfoVO getUserInfo();

    /**
     * 修改用户邮箱
     * @param email 邮箱信息
     */
    void updateUserEmail(EmailDTO email);

    /**
     * 修改用户头像
     * @param file 头像
     * @return 头像链接
     */
    String saveAvatar(MultipartFile file);

    /**
     * 更新用户信息
     * @param userInfo 用户信息
     */
    void updateUserInfo(UserInfoDTO userInfo);

    /**
     * 修改用户密码
     * @param password 用户密码
     */
    void updatePassword(PasswordDTO password);
}
