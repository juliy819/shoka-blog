package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 说说DTO
 * @author juliy
 * @date 2023/5/23 11:46
 */
@Data
@Schema(description = "说说DTO")
public class TalkDTO {
    /**
     * 说说id
     */
    @Schema(description = "说说id")
    private Integer id;

    /**
     * 说说内容
     */
    @NotBlank(message = "说说内容不能为空")
    @Schema(description = "说说内容")
    private String talkContent;

    /**
     * 说说图片
     */
    @Schema(description = "说说图片")
    private String images;

    /**
     * 是否置顶 (0否 1是)
     */
    @NotNull(message = "置顶状态不能为空")
    @Schema(description = "是否置顶 (0否 1是)")
    private Integer isTop;

    /**
     * 说说状态 (1公开 2私密)
     */
    @NotNull(message = "说说状态不能为空")
    @Schema(description = "说说状态 (1公开 2私密)")
    private Integer status;
}
