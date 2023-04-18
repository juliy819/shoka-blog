package com.juliy.model.vo;

import com.juliy.entity.SiteConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客信息VO
 * @author juliy
 * @date 2023/4/8 14:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "博客信息VO")
public class BlogInfoVO {

    /**
     * 文章数量
     */
    @Schema(description = "文章数量")
    private Long articleCount;

    /**
     * 分类数量
     */
    @Schema(description = "分类数量")
    private Long categoryCount;

    /**
     * 标签数量
     */
    @Schema(description = "标签数量")
    private Long tagCount;

    /**
     * 网站访问量
     */
    @Schema(description = "网站访问量")
    private Long viewCount;

    /**
     * 网站配置
     */
    @Schema(description = "网站配置")
    private SiteConfig siteConfig;
}
