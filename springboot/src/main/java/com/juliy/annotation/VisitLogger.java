package com.juliy.annotation;

import java.lang.annotation.*;

/**
 * 访问日志注解
 * @author juliy
 * @date 2023/5/16 17:41
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisitLogger {
    /**
     * @return 访问页面
     */
    String value() default "";
}
