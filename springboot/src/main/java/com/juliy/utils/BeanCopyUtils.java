package com.juliy.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象复制工具类
 * @author juliy
 * @date 2023/3/13 12:26
 */
@Slf4j
public class BeanCopyUtils {

    private BeanCopyUtils() {}

    /**
     * 复制单个对象
     * @param source 要复制的对象
     * @param target 目标对象的类
     * @return 复制之后的对象
     */
    public static <T> T copyBean(Object source, Class<T> target) {
        T temp = null;
        try {
            temp = target.getConstructor().newInstance();
            if (null != source) {
                BeanUtils.copyProperties(source, temp);
            }
        } catch (Exception e) {
            log.error("Bean复制失败\n", e);
        }
        return temp;
    }

    /**
     * 复制对象列表
     * @param source 要复制的对象列表
     * @param target 目标对象的类
     * @return 复制之后的对象列表
     */
    public static <T, S> List<T> copyBeanList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (null != source && source.size() > 0) {
            for (Object obj : source) {
                list.add(BeanCopyUtils.copyBean(obj, target));
            }
        }
        return list;
    }
}
