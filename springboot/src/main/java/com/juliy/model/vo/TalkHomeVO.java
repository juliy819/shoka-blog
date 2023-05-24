package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 首页说说VO
 * @author juliy
 * @date 2023/5/23 11:49
 */
@Data
@Schema(description = "首页说说VO")
public class TalkHomeVO {
    /**
     * 说说id
     */
    @Schema(description = "说说id")
    private Integer id;

    /**
     * 说说内容
     */
    @Schema(description = "说说内容")
    private String talkContent;

}
