package com.juliy.exception;

import com.juliy.enums.StatusCodeEnum;

/**
 * 业务异常类
 * @author juliy
 * @date 2023/3/1 19:41
 */
public class ServiceException extends RuntimeException {
    private Integer code = StatusCodeEnum.FAIL.getCode();

    private final String message;

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getMsg();
    }
}
