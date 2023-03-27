package com.juliy.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Objects;

/**
 * 分页工具类
 * @author juliy
 * @date 2023/3/25 12:39
 */
public class PageUtils {

    private static final ThreadLocal<Page<?>> PAGE_HOLDER = new ThreadLocal<>();

    /**
     * 保存分页
     * @param page 分页
     */
    public static void setCurrentPage(Page<?> page) {
        PAGE_HOLDER.set(page);
    }

    /**
     * 获取分页
     * @return 分页
     */
    public static Page<?> getPage() {
        Page<?> page = PAGE_HOLDER.get();
        if (Objects.isNull(page)) {
            setCurrentPage(new Page<>());
        }
        return PAGE_HOLDER.get();
    }

    /**
     * 获取当前页
     * @return 当前页
     */
    public static Long getCurrent() {
        return getPage().getCurrent();
    }

    /**
     * 获取每页显示条数
     * @return 每页显示条数
     */
    public static Long getSize() {
        return getPage().getSize();
    }

    /**
     * 获取当前分页位置数据
     * @return 当前分页位置数据
     */
    public static Long getLimitCurrent() {
        return (getCurrent() - 1) * getSize();
    }

    /**
     * 删除分页数据
     */
    public static void remove() {
        PAGE_HOLDER.remove();
    }
}
