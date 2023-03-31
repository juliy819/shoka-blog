package com.juliy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.juliy.entity.User;
import com.juliy.mapper.UserMapper;
import com.juliy.model.vo.AdminUserInfoVO;
import com.juliy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务接口实现类
 * @author juliy
 * @date 2023/3/15 17:31
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public AdminUserInfoVO getAdminUserInfo() {
        // 获取登录用户id
        int userId = StpUtil.getLoginIdAsInt();
        // 获取用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getAvatar)
                .eq(User::getId, userId));
        List<String> roleList = StpUtil.getRoleList();
        // 去重、去空
        List<String> permissionList = StpUtil.getPermissionList()
                .stream()
                .filter(perm -> !StrUtil.isEmpty(perm))
                .distinct()
                .toList();
        //构建并返回
        return AdminUserInfoVO.builder()
                .id(userId)
                .avatar(user.getAvatar())
                .roleList(roleList)
                .permissionList(permissionList)
                .build();
    }
}
