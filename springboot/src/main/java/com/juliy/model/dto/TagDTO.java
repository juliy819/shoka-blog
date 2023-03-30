package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 标签DTO
 * @author juliy
 * @date 2023/3/25 10:05
 */
@Data
@Schema(description = "标签DTO")
public class TagDTO {

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    private Integer id;

    /**
     * 标签名
     */
    @NotBlank(message = "标签名不能为空")
    @Schema(description = "标签名")
    private String tagName;
}
