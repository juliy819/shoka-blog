package com.juliy.interceptor;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juliy.utils.PageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.Optional;

import static com.juliy.constant.PageConstant.*;

/**
 * 分页拦截器
 * @author juliy
 * @date 2023/3/25 13:12
 */
@Component
@SuppressWarnings("all")
public class PaginationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求参数中获取到分页相关的参数
        String currentPage = request.getParameter(CURRENT);
        String pageSize = Optional.ofNullable(request.getParameter(SIZE)).orElse(DEFAULT_SIZE);
        // 若是分页请求，则保存分页数据，供其它类通过PageUtils直接调用
        if (!Objects.isNull(currentPage) && !StrUtil.isEmpty(currentPage)) {
            PageUtils.setCurrentPage(new Page<>(Long.parseLong(currentPage), Long.parseLong(pageSize)));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
        // 请求完成后则删除保存的信息
        PageUtils.remove();
    }
}
