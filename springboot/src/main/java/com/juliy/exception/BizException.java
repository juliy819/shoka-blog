package com.juliy.exception;

import com.juliy.enums.StatusCodeEnum;

/**
 * 业务异常类
 * @author JuLiy
 * @date 2023/3/1 19:41
 */
public class BizException extends RuntimeException {
    private Integer code = StatusCodeEnum.FAIL.getCode();

    private final String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getMsg();
    }
}
