package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 文章条件列表VO
 * @author juliy
 * @date 2023/4/13 21:13
 */
@Data
@Builder
@Schema(description = "文章条件列表VO")
public class ArticleConditionList {
    /**
     * 文章列表
     */
    @Schema(description = "文章列表")
    private List<ArticleConditionVO> articleConditionList;

    /**
     * 条件名
     */
    @Schema(description = "条件名")
    private String name;
}
