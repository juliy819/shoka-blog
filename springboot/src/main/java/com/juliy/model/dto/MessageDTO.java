package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 留言DTO
 * @author juliy
 * @date 2023/5/27 15:20
 */
@Data
@Schema(description = "留言DTO")
public class MessageDTO {
    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空")
    @Schema(description = "头像")
    private String avatar;

    /**
     * 留言内容
     */
    @NotBlank(message = "留言内容不能为空")
    @Schema(description = "留言内容")
    private String messageContent;

}
