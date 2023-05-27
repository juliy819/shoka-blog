package com.juliy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.juliy.entity.SiteConfig;
import com.juliy.entity.User;
import com.juliy.entity.UserRole;
import com.juliy.enums.StatusCodeEnum;
import com.juliy.exception.ServiceException;
import com.juliy.mapper.UserMapper;
import com.juliy.mapper.UserRoleMapper;
import com.juliy.model.dto.LoginDTO;
import com.juliy.model.dto.MailDTO;
import com.juliy.model.dto.RegisterDTO;
import com.juliy.service.LoginService;
import com.juliy.service.RedisService;
import com.juliy.utils.CommonUtils;
import com.juliy.utils.SecurityUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.juliy.constant.CommonConstant.*;
import static com.juliy.constant.MqConstant.EMAIL_EXCHANGE;
import static com.juliy.constant.MqConstant.EMAIL_SIMPLE_KEY;
import static com.juliy.constant.RedisConstant.*;
import static com.juliy.enums.LoginTypeEnum.EMAIL;
import static com.juliy.enums.RoleEnum.USER;

/**
 * 登录业务接口实现类
 * @author juliy
 * @date 2023/3/1 13:56
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RabbitTemplate rabbitTemplate;
    private final RedisService redisService;

    @Autowired
    public LoginServiceImpl(UserMapper userMapper,
                            UserRoleMapper userRoleMapper,
                            RabbitTemplate rabbitTemplate,
                            RedisService redisService) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.redisService = redisService;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(RegisterDTO register) {
        verifyCode(register.getUsername(), register.getCode());
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                                                 .select(User::getUsername)
                                                 .eq(User::getUsername, register.getUsername()));
        CommonUtils.checkParamNotNull(user, "邮箱已注册！");
        SiteConfig siteConfig = redisService.getObject(SITE_CONFIG);
        // 添加用户
        User newUser = User.builder()
                .username(register.getUsername())
                .email(register.getUsername())
                .nickname(USER_NICKNAME + IdWorker.getId())
                .avatar(siteConfig.getUserAvatar())
                .password(SecurityUtils.sha256Encrypt(register.getPassword()))
                .loginType(EMAIL.getType())
                .isDisable(FALSE)
                .build();
        userMapper.insert(newUser);
        // 绑定用户角色
        UserRole userRole = UserRole.builder()
                .userId(newUser.getId())
                .roleId(USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
    }

    @Override
    public void sendCode(String email) {
        CommonUtils.checkEmail(email);
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 6);
        String code = randomGenerator.generate();
        MailDTO mailDTO = MailDTO.builder()
                .toEmail(email)
                .subject(CAPTCHA)
                .content("您的验证码为 " + code + " 有效期为" + CODE_EXPIRE_TIME + "分钟")
                .build();
        //// 验证码存入消息队列
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, EMAIL_SIMPLE_KEY, mailDTO);
        // 验证码存入redis
        redisService.setObject(CODE_KEY + email, code, CODE_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    /**
     * 校验验证码
     * @param username 用户名
     * @param code     验证码
     */
    public void verifyCode(String username, String code) {
        String sysCode = redisService.getObject(CODE_KEY + username);
        CommonUtils.checkParam(StrUtil.isBlank(code), "验证码未发送或已过期！");
        CommonUtils.checkParam(!sysCode.equals(code), "验证码错误，请重新输入！");
    }
}
