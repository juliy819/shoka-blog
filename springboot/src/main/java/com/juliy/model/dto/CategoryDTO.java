package com.juliy.model.dto;

import lombok.Data;

/**
 * 分类DTO
 * @author juliy
 * @date 2023/3/25 11:28
 */
@Data
public class CategoryDTO {
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
}
