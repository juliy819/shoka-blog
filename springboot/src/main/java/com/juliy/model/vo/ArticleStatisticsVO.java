package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 文章贡献统计
 * @author juliy
 * @date 2023/4/13 17:50
 */
@Data
@Schema(description = "文章贡献统计")
public class ArticleStatisticsVO {

    /**
     * 日期
     */
    @Schema(description = "日期")
    private String date;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Integer count;
}
