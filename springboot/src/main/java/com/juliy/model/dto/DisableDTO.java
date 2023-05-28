package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户禁用DTO
 * @author juliy
 * @date 2023/5/28 12:52
 */
@Data
@Schema(description = "用户禁用DTO")
public class DisableDTO {
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "id")
    private Integer id;

    /**
     * 是否禁用 (0否 1是)
     */
    @NotNull(message = "状态不能为空")
    @Schema(description = "是否禁用 (0否 1是)")
    private Integer isDisable;
}
