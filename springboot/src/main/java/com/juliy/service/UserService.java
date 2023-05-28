package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.User;
import com.juliy.model.dto.*;
import com.juliy.model.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * 查看后台用户列表
     * @param condition 条件
     * @return 用户列表
     */
    PageResult<UserAdminVO> listUserAdmin(ConditionDTO condition);

    /**
     * 查看用户角色选项
     * @return 用户角色选项
     */
    List<UserRoleVO> listUserRole();

    /**
     * 修改用户
     * @param user 用户信息
     */
    void updateUser(UserRoleDTO user);

    /**
     * 修改用户状态
     * @param disable 禁用信息
     */
    void updateUserStatus(DisableDTO disable);

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
