package com.juliy.satoken;

import cn.dev33.satoken.listener.SaTokenListenerForLog;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.juliy.entity.User;
import com.juliy.mapper.UserMapper;
import com.juliy.model.vo.OnlineUserVO;
import com.juliy.utils.IpUtils;
import com.juliy.utils.UserAgentUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import static com.juliy.constant.CommonConstant.ONLINE_USER;
import static com.juliy.enums.ZoneEnum.SHANGHAI;

/**
 * SaToken侦听器
 *
 * @author juliy
 * @date 2023/3/15 15:38
 */
@Component
public class MySaTokenListener extends SaTokenListenerForLog {

    private final UserMapper userMapper;

    private final HttpServletRequest request;

    @Autowired
    public MySaTokenListener(UserMapper userMapper,
                             HttpServletRequest request) {
        this.userMapper = userMapper;
        this.request = request;
    }

    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        super.doLogin(loginType, loginId, tokenValue, loginModel);

        // 没有UA就不更新信息了，主要是由于测试环境登录时没UA
        if (StrUtil.isEmpty(request.getHeader("User-Agent"))) {
            return;
        }
        // 解析os和browser
        Map<String, String> userAgentMap = UserAgentUtils.parseOsAndBrowser(request.getHeader("User-Agent"));
        // 获取登录ip地址
        String ipAddress = IpUtils.getIpAddress(request);
        // 获取登录地址
        String ipSource = IpUtils.getIpSource(ipAddress);
        // 获取登录时间
        LocalDateTime loginTime = LocalDateTime.now(ZoneId.of(SHANGHAI.getZone()));

        //更新用户登录信息
        User newUser = User.builder()
                .id((Integer) loginId)
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .loginTime(loginTime)
                .build();
        userMapper.updateById(newUser);
        // 查询用户头像和昵称
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getAvatar, User::getNickname)
                .eq(User::getId, loginId));
        // 更新在线信息
        OnlineUserVO onlineUser = OnlineUserVO.builder()
                .id((Integer) loginId)
                .token(tokenValue)
                .avatar(user.getAvatar())
                .nickname(user.getNickname())
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .os(userAgentMap.get("os"))
                .browser(userAgentMap.get("browser"))
                .loginTime(loginTime)
                .build();
        // 将用户在线信息存入tokenSession
        SaSession tokenSession = StpUtil.getTokenSessionByToken(tokenValue);
        tokenSession.set(ONLINE_USER, onlineUser);
    }
}
