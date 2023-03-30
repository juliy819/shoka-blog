package com.juliy.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juliy.entity.ArticleTag;
import com.juliy.entity.Tag;
import com.juliy.exception.ServiceException;
import com.juliy.mapper.ArticleTagMapper;
import com.juliy.mapper.TagMapper;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.dto.TagDTO;
import com.juliy.model.vo.PageResult;
import com.juliy.model.vo.TagAdminVO;
import com.juliy.model.vo.TagOptionVO;
import com.juliy.model.vo.TagVO;
import com.juliy.service.TagService;
import com.juliy.utils.BeanCopyUtils;
import com.juliy.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 标签业务接口实现类
 *
 * @author juliy
 * @date 2023/3/27 11:43
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    private final TagMapper tagMapper;

    private final ArticleTagMapper articleTagMapper;

    @Autowired
    public TagServiceImpl(TagMapper tagMapper, ArticleTagMapper articleTagMapper) {
        this.tagMapper = tagMapper;
        this.articleTagMapper = articleTagMapper;
    }

    @Override
    public List<TagVO> listTags() {
        return tagMapper.selectTags();
    }

    @Override
    public PageResult<TagAdminVO> listTagsAdminByPage(ConditionDTO condition) {
        Long count = tagMapper.selectCount(new LambdaQueryWrapper<Tag>()
                .like(StrUtil.isNotBlank(condition.getKeywords()),
                        Tag::getTagName,
                        condition.getKeywords()));

        if (count == 0) {
            return new PageResult<>();
        }
        List<TagAdminVO> tagList = tagMapper.selectTagsAdmin(PageUtils.getLimitCurrent(),
                PageUtils.getSize(),
                condition);
        return new PageResult<>(tagList, count);
    }

    @Override
    public List<TagOptionVO> listTagOptions(ConditionDTO condition) {
        List<Tag> tagList = tagMapper.selectList(
                new LambdaQueryWrapper<Tag>()
                        .like(StrUtil.isNotBlank(condition.getKeywords()),
                                Tag::getTagName,
                                condition.getKeywords())
                        .orderByDesc(Tag::getId));
        return BeanCopyUtils.copyBeanList(tagList, TagOptionVO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateTag(TagDTO tagDTO) {
        // 判断标签是否已存在
        Tag existTag = tagMapper.selectOne(
                new LambdaQueryWrapper<Tag>()
                        .select(Tag::getId)
                        .eq(Tag::getTagName, tagDTO.getTagName()));

        if (Objects.nonNull(existTag) && !existTag.getId().equals(tagDTO.getId())) {
            throw new ServiceException("标签已存在");
        }
        // 修改分类
        Tag newTag = Tag.builder()
                .id(tagDTO.getId())
                .tagName(tagDTO.getTagName())
                .build();
        this.saveOrUpdate(newTag);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeTags(List<Integer> tagIds) {
        Long count = articleTagMapper.selectCount(
                new LambdaQueryWrapper<ArticleTag>()
                        .in(ArticleTag::getTagId, tagIds));

        if (count > 0) {
            throw new ServiceException("删除失败，该标签下存在文章");
        }
        this.removeBatchByIds(tagIds);
    }
}
