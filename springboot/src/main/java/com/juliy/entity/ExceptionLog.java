package com.juliy.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 异常记录
 * @author juliy
 * @date 2023/5/16 17:58
 */
@Data
@TableName("t_exception_log")
public class ExceptionLog {

    /**
     * 异常id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 异常模块
     */
    private String module;

    /**
     * 异常uri
     */
    private String uri;

    /**
     * 异常名称
     */
    private String name;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 异常方法
     */
    private String errorMethod;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 请求参数
     */
    @JsonIgnore
    private String params;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作ip
     */
    private String ipAddress;

    /**
     * 操作地址
     */
    private String ipSource;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
