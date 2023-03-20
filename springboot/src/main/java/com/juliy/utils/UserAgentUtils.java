package com.juliy.utils;


import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 浏览器工具类
 * @author juliy
 * @date 2023/3/15 15:57
 */
public class UserAgentUtils {


    /**
     * 从User-Agent解析客户端操作系统和浏览器版本
     */
    public static Map<String, String> parseOsAndBrowser(String userAgent) {
        UserAgent agent = UserAgentUtil.parse(userAgent);
        String os = agent.getOs().toString();
        String browser = agent.getBrowser().toString();
        Map<String, String> map = new HashMap<>(2);
        map.put("os", os);
        map.put("browser", browser);
        return map;
    }

}
