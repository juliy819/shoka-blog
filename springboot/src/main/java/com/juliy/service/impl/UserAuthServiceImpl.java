package com.juliy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.juliy.constant.CommonConstant;
import com.juliy.entity.UserAuth;
import com.juliy.entity.UserInfo;
import com.juliy.enums.LoginTypeEnum;
import com.juliy.exception.BizException;
import com.juliy.mapper.UserAuthMapper;
import com.juliy.mapper.UserInfoMapper;
import com.juliy.model.dto.LoginDTO;
import com.juliy.model.dto.RegisterDTO;
import com.juliy.service.UserAuthService;
import com.juliy.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static com.juliy.utils.CommonUtil.checkEmail;

/**
 * 登录业务接口实现类
 * @author JuLiy
 * @date 2023/3/1 13:56
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    UserAuthMapper userAuthMapper;
    UserInfoMapper userInfoMapper;

    @Autowired
    public UserAuthServiceImpl(UserAuthMapper userAuthMapper,
                               UserInfoMapper userInfoMapper) {
        this.userAuthMapper = userAuthMapper;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public String login(LoginDTO loginInfo) {
        UserAuth user = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                                                         .select(UserAuth::getId)
                                                         .eq(UserAuth::getUsername, loginInfo.getUsername())
                                                         .eq(UserAuth::getPassword, SecurityUtil.sha256Encrypt(loginInfo.getPassword())));
        Assert.notNull(user, "用户不存在或密码错误");
        // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
        StpUtil.checkDisable(user.getId());
        // 通过校验后，再进行登录
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    @Override
    public void register(RegisterDTO registerInfo) {
        if (!checkEmail(registerInfo.getUsername())) {
            throw new BizException("邮箱格式不对!");
        }
        if (checkUser(registerInfo)) {
            throw new BizException("邮箱已被注册！");
        }
        UserInfo userInfo = UserInfo.builder()
                .email(registerInfo.getUsername())
                .nickname(CommonConstant.DEFAULT_NICKNAME + IdWorker.getId())
                //.avatar(auroraInfoService.getWebsiteConfig().getUserAvatar())
                .avatar("https://static.linhaojun.top/avatar/2af2e2db20740e712f0a011a6f8c9af5.jpg")
                .build();
        userInfoMapper.insert(userInfo);
        //UserRole userRole = UserRole.builder()
        //        .userId(userInfo.getId())
        //        .roleId(RoleEnum.USER.getRoleId())
        //        .build();
        //userRoleMapper.insert(userRole);
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(registerInfo.getUsername())
                .password(SecurityUtil.sha256Encrypt(registerInfo.getPassword()))
                .loginType(LoginTypeEnum.EMAIL.getType())
                .build();
        userAuthMapper.insert(userAuth);
    }

    private Boolean checkUser(RegisterDTO registerInfo) {
        //if (!user.getCode().equals(redisService.get(USER_CODE_KEY + user.getUsername()))) {
        //    throw new BizException("验证码错误！");
        //}
        //UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
        //                                                     .select(UserAuth::getUsername)
        //                                                     .eq(UserAuth::getUsername, user.getUsername()));
        //return Objects.nonNull(userAuth);
        return false;
    }
}
