package com.juliy.interceptor;

import com.alibaba.fastjson2.JSON;
import com.juliy.annotation.AccessLimit;
import com.juliy.model.vo.Result;
import com.juliy.service.RedisService;
import com.juliy.utils.IpUtils;
import com.juliy.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Redis拦截器
 * @author juliy
 * @date 2023/3/11 21:13
 */
@Slf4j
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    private final RedisService redisService;

    @Autowired
    public AccessLimitInterceptor(RedisService redisService) {this.redisService = redisService;}

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        boolean result = true;
        // Handler 是否为 HandlerMethod 实例
        if (handler instanceof HandlerMethod handlerMethod) {
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            //方法上没有访问控制的注解，直接通过
            if (accessLimit != null) {
                int seconds = accessLimit.seconds();
                int maxCount = accessLimit.maxCount();
                String ip = IpUtils.getIpAddress(request);
                String method = request.getMethod();
                String requestUri = request.getRequestURI();
                String redisKey = ip + ":" + method + ":" + requestUri;
                try {
                    Long count = redisService.incr(redisKey, 1L);
                    // 第一次访问
                    if (Objects.nonNull(count) && count == 1) {
                        redisService.setExpire(redisKey, seconds, TimeUnit.SECONDS);
                    } else if (count > maxCount) {
                        WebUtils.renderString(response, JSON.toJSONString(Result.fail(accessLimit.msg())));
                        log.warn(redisKey + "请求次数超过每" + seconds + "秒" + maxCount + "次");
                        result = false;
                    }
                } catch (RedisConnectionFailureException e) {
                    log.error("redis错误: " + e.getMessage());
                    result = false;
                }
            }
        }
        return result;
    }
}
