package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.Tag;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.dto.TagDTO;
import com.juliy.model.vo.PageResult;
import com.juliy.model.vo.TagAdminVO;
import com.juliy.model.vo.TagOptionVO;
import com.juliy.model.vo.TagVO;

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
     * 添加标签
     * @param tagDTO 标签
     */
    void saveTag(TagDTO tagDTO);

    /**
     * 修改标签
     * @param tagDTO 标签
     */
    void updateTag(TagDTO tagDTO);

    /**
     * 删除标签
     * @param tagIds 标签id列表
     */
    void removeTags(List<Integer> tagIds);
}
