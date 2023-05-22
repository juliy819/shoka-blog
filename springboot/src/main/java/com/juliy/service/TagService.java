package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.Tag;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.dto.TagDTO;
import com.juliy.model.vo.*;

import java.util.List;

/**
 * 标签业务接口
 * @author juliy
 * @date 2023/3/27 11:43
 */
public interface TagService extends IService<Tag> {

    /**
     * 获取标签列表
     * @return 标签列表
     */
    List<TagVO> listTags();

    /**
     * 获取后台标签列表
     * @param condition 查询条件
     * @return 后台标签列表
     */
    PageResult<TagAdminVO> listTagsAdminByPage(ConditionDTO condition);

    /**
     * 获取标签选项列表
     * @param condition 查询条件
     * @return 标签选项列表
     */
    List<TagOptionVO> listTagOptions(ConditionDTO condition);

    /**
     * 添加或修改标签
     * @param tagDTO 标签
     */
    void saveOrUpdateTag(TagDTO tagDTO);

    /**
     * 删除标签
     * @param tagIds 标签id列表
     */
    void removeTags(List<Integer> tagIds);

    /**
     * 查看分类下的文章
     * @param condition 条件
     * @return 文章列表
     */
    ArticleConditionList listTagArticles(ConditionDTO condition);
}
