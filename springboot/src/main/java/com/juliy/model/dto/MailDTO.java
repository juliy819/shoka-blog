package com.juliy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 邮箱DTO
 * @author juliy
 * @date 2023/5/27 14:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "邮箱DTO")
public class MailDTO {

    /**
     * 接收者邮箱号
     */
    @Schema(description = "接收者邮箱号")
    private String toEmail;

    /**
     * 主题
     */
    @Schema(description = "主题")
    private String subject;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String content;

    /**
     * 内容信息
     */
    @Schema(description = "内容信息")
    private Map<String, Object> contentMap;

    /**
     * 邮件模板
     */
    @Schema(description = "邮件模板")
    private String template;
}
