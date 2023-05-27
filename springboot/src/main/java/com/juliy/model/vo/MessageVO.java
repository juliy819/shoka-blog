package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 留言VO
 * @author juliy
 * @date 2023/5/27 15:13
 */
@Data
@Schema(description = "留言VO")
public class MessageVO {


    /**
     * 留言id
     */
    @Schema(description = "留言id")
    private Integer id;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 留言内容
     */
    @Schema(description = "留言内容")
    private String messageContent;
}
