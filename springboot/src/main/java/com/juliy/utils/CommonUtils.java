package com.juliy.utils;

import com.juliy.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 * @author juliy
 * @date 2023/3/1 19:51
 */
public class CommonUtils {
    /**
     * 校验邮箱
     * @param email 邮箱
     * @return boolean
     */
    public static void checkEmail(String email) {
        String rule = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(rule);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        //进行正则匹配
        if (!m.matches()) {
            throw new ServiceException("请输入正确的邮箱!");
        }
    }

    /**
     * 转换List
     * @param obj   obj
     * @param clazz clazz
     * @return {@link List <T>}
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return result;
    }

    /**
     * 自定义参数校验,flag为true则抛出异常
     * @param flag    校验标识
     * @param message 异常信息
     */
    public static void checkParam(boolean flag, String message) {
        if (flag) {
            throw new ServiceException(message);
        }
    }

    /**
     * 校验参数是否为空，为空则抛出异常
     * @param param   校验对象
     * @param message 异常信息
     */
    public static void checkParamNull(Object param, String message) {
        if (Objects.isNull(param)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 校验参数是否不为空，不为空则抛出异常
     * @param param   校验对象
     * @param message 异常信息
     */
    public static void checkParamNotNull(Object param, String message) {
        if (Objects.nonNull(param)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 校验参数是否相等，不相等则抛出异常
     * @param param1  参数1
     * @param param2  参数2
     * @param message 异常信息
     */
    public static void checkParamNotEqual(Object param1, Object param2, String message) {
        if (!param1.equals(param2)) {
            throw new ServiceException(message);
        }
    }
}
