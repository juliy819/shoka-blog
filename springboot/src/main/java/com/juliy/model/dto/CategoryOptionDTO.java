package com.juliy.model.dto;

import lombok.Data;

/**
 * 分类选项VO
 * @author juliy
 * @date 2023/3/25 11:31
 */
@Data
public class CategoryOptionDTO {

    /**
     * 分类id
     */
    private Integer id;

    /**
     * 分类名
     */
    private String categoryName;
}
