package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import static com.juliy.enums.StatusCodeEnum.FAIL;
import static com.juliy.enums.StatusCodeEnum.SUCCESS;

/**
 * 结果返回类
 * @author juliy
 * @date 2023/3/1 14:26
 */
@Data
@Schema(description = "结果返回类")
public class Result<T> {

    /**
     * 返回状态
     */
    @Schema(description = "返回状态")
    private Boolean flag;

    /**
     * 状态码
     */
    @Schema(description = "状态码")
    private Integer code;

    /**
     * 返回信息
     */
    @Schema(description = "返回信息")
    private String msg;

    /**
     * 返回数据
     */
    @Schema(description = "返回数据")
    private T data;

    public static <T> Result<T> success() {
        return buildResult(true, null, SUCCESS.getCode(), SUCCESS.getMsg());
    }

    public static <T> Result<T> success(T data) {
        return buildResult(true, data, SUCCESS.getCode(), SUCCESS.getMsg());
    }

    public static <T> Result<T> fail(String message) {
        return buildResult(false, null, FAIL.getCode(), message);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return buildResult(false, null, code, message);
    }

    private static <T> Result<T> buildResult(Boolean flag, T data, Integer code, String message) {
        Result<T> r = new Result<>();
        r.setFlag(flag);
        r.setData(data);
        r.setCode(code);
        r.setMsg(message);
        return r;
    }
}
