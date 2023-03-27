package com.juliy.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分类后台DTO
 * @author juliy
 * @date 2023/3/25 10:39
 */
@Data
public class CategoryAdminDTO {
    /**
     * 分类id
     */
    private Integer id;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
