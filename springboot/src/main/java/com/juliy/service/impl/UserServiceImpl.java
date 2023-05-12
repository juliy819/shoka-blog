package com.juliy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.User;
import com.juliy.mapper.UserMapper;
import com.juliy.model.vo.AdminUserInfoVO;
import com.juliy.model.vo.UserInfoVO;
import com.juliy.service.RedisService;
import com.juliy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.juliy.constant.RedisConstant.*;

/**
 * 用户服务接口实现类
 * @author juliy
 * @date 2023/3/15 17:31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final RedisService redisService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,
                           RedisService redisService) {
        this.userMapper = userMapper;
        this.redisService = redisService;
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

    @Override
    public UserInfoVO getUserInfo() {
        Integer userId = StpUtil.getLoginIdAsInt();
        User user = this.getOne(new LambdaQueryWrapper<User>()
                                        .select(User::getNickname, User::getAvatar, User::getUsername, User::getWebSite,
                                                User::getIntro, User::getEmail, User::getLoginType)
                                        .eq(User::getId, userId));
        Set<Object> articleLikeSet = redisService.getSet(ARTICLE_LIKE + userId);
        Set<Object> commentLikeSet = redisService.getSet(COMMENT_LIKE + userId);
        Set<Object> talkLikeSet = redisService.getSet(TALK_LIKE + userId);
        return UserInfoVO
                .builder()
                .id(userId)
                .avatar(user.getAvatar())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .email(user.getEmail())
                .webSite(user.getWebSite())
                .intro(user.getIntro())
                .articleLikeSet(articleLikeSet)
                .commentLikeSet(commentLikeSet)
                .talkLikeSet(talkLikeSet)
                .loginType(user.getLoginType())
                .build();
    }
}
