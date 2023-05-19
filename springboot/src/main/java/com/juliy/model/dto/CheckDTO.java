package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 审核DTO
 * @author juliy
 * @date 2023/5/18 22:36
 */
@Data
@Schema(description = "审核DTO")
public class CheckDTO {

    /**
     * id集合
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "id集合")
    private List<Integer> idList;

    /**
     * 是否通过 (0否 1是)
     */
    @NotNull(message = "状态值不能为空")
    @Schema(description = "是否通过 (0否 1是)")
    private Integer isCheck;
}
