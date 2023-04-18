package com.juliy.service;

import com.juliy.model.vo.BlogInfoAdminVO;
import com.juliy.model.vo.BlogInfoVO;

/**
 * 博客信息服务接口
 * @author juliy
 * @date 2023/4/8 14:35
 */
public interface BlogInfoService {

    /**
     * 访客信息记录
     */
    void report();

    /**
     * 获取博客信息
     * @return 博客信息
     */
    BlogInfoVO getBlogInfo();

    /**
     * 获取后台博客信息
     * @return 后台博客信息
     */
    BlogInfoAdminVO getBlogInfoAdmin();
}