package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户浏览
 * @author juliy
 * @date 2023/4/13 17:48
 */
@Data
@Schema(description = "用户浏览")
public class UserViewVO {

    /**
     * 日期
     */
    @Schema(description = "日期")
    private String date;

    /**
     * 访问量
     */
    @Schema(description = "访问量")
    private Integer pv;

    /**
     * 独立访客
     */
    @Schema(description = "独立访客")
    private Integer uv;
}
