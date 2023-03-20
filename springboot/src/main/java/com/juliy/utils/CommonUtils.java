package com.juliy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 * @author juliy
 * @date 2023/3/1 19:51
 */
public class CommonUtils {
    public static boolean checkEmail(String email) {
        String rule = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(rule);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        //进行正则匹配
        return m.matches();
    }
}
