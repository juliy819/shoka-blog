package com.juliy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juliy.entity.Tag;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.vo.TagAdminVO;
import com.juliy.model.vo.TagVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签mapper
 * @author juliy
 * @date 2023/3/27 10:12
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 查询标签列表
     * @return 标签列表
     */
    List<TagVO> selectTags();

    /**
     * 查询后台标签列表
     * @param current   页码
     * @param size      数量
     * @param condition 查询条件
     * @return 后台标签列表
     */
    List<TagAdminVO> selectTagsAdmin(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionDTO condition);

    /**
     * 根据文章id查询标签名称
     * @param articleId 文章id
     * @return 标签名称列表
     */
    List<String> selectTagNamesByArticleId(Integer articleId);

}
