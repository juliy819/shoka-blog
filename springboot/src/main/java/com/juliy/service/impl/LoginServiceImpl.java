package com.juliy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.juliy.entity.User;
import com.juliy.enums.StatusCodeEnum;
import com.juliy.exception.ServiceException;
import com.juliy.mapper.UserMapper;
import com.juliy.model.dto.LoginDTO;
import com.juliy.model.dto.RegisterDTO;
import com.juliy.service.LoginService;
import com.juliy.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录业务接口实现类
 * @author juliy
 * @date 2023/3/1 13:56
 */
@Service
public class LoginServiceImpl implements LoginService {

    UserMapper userMapper;

    @Autowired
    public LoginServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        // 按输入的用户名、密码查询账户，若用户名或密码有误则user为null
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                                                 .select(User::getId)
                                                 .eq(User::getUsername, loginDTO.getUsername())
                                                 .eq(User::getPassword, SecurityUtils.sha256Encrypt(loginDTO.getPassword())));
        if (user == null) {
            throw new ServiceException(StatusCodeEnum.UNAUTHORIZED, "用户名或密码错误");
        }
        // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
        StpUtil.checkDisable(user.getId());
        // 通过校验后，再进行登录
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    @Override
    public void register(RegisterDTO registerInfo) {
        //if (!checkEmail(registerInfo.getUsername())) {
        //    throw new BizException("邮箱格式不对!");
        //}
        //if (checkUser(registerInfo)) {
        //    throw new BizException("邮箱已被注册！");
        //}
        //UserInfo userInfo = UserInfo.builder()
        //        .email(registerInfo.getUsername())
        //        .nickname(CommonConstant.DEFAULT_NICKNAME + IdWorker.getId())
        //        //.avatar(auroraInfoService.getWebsiteConfig().getUserAvatar())
        //        .avatar("https://static.linhaojun.top/avatar/2af2e2db20740e712f0a011a6f8c9af5.jpg")
        //        .build();
        //userInfoMapper.insert(userInfo);
        ////UserRole userRole = UserRole.builder()
        ////        .userId(userInfo.getId())
        ////        .roleId(RoleEnum.USER.getRoleId())
        ////        .build();
        ////userRoleMapper.insert(userRole);
        //User userAuth = User.builder()
        //        .userInfoId(userInfo.getId())
        //        .username(registerInfo.getUsername())
        //        .password(SecurityUtil.sha256Encrypt(registerInfo.getPassword()))
        //        .loginType(LoginTypeEnum.EMAIL.getType())
        //        .build();
        //userAuthMapper.insert(userAuth);
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
