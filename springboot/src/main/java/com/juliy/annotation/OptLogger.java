package com.juliy.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * @author juliy
 * @date 2023/5/16 17:54
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OptLogger {
    /**
     * @return 操作描述
     */
    String value() default "";
}
