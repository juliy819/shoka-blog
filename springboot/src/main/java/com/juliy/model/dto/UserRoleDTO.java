package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 用户角色DTO
 * @author juliy
 * @date 2023/5/28 12:51
 */
@Data
@Schema(description = "用户角色DTO")
public class UserRoleDTO {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    @Schema(description = "用户id")
    private Integer id;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 角色id
     */
    @NotNull(message = "角色id不能为空")
    @Schema(description = "角色id")
    private List<Integer> roleIdList;
}
