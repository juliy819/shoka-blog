package com.juliy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页返回类
 * @author juliy
 * @date 2023/3/25 11:18
 */
@Data
@AllArgsConstructor
@Schema(description = "分页返回类")
public class PageResult<T> {

    /**
     * 分页结果
     */
    @Schema(description = "分页结果")
    private List<T> recordList;

    /**
     * 总数
     */
    @Schema(description = "总数")
    private Long count;

    public PageResult() {
        // 无参构造中赋初始值，避免没有数据时返回null
        this.recordList = new ArrayList<>();
        this.count = 0L;
    }
}
