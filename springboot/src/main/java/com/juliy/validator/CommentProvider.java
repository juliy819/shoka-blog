package com.juliy.validator;

import com.juliy.model.dto.CommentDTO;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.juliy.enums.CommentTypeEnum.*;

/**
 * 评论分组校验器
 * @author juliy
 * @date 2023/5/16 15:27
 */
public class CommentProvider implements DefaultGroupSequenceProvider<CommentDTO> {
    @Override
    public List<Class<?>> getValidationGroups(CommentDTO commentDTO) {
        List<Class<?>> defaultGroupSequence = new ArrayList<>();
        defaultGroupSequence.add(CommentDTO.class);
        if (commentDTO != null) {
            if (commentDTO.getCommentType().equals(ARTICLE.getType()) || commentDTO.getCommentType().equals(TALK.getType())) {
                defaultGroupSequence.add(ValidationGroups.ArticleTalk.class);
            }
            if (commentDTO.getCommentType().equals(LINK.getType())) {
                defaultGroupSequence.add(ValidationGroups.FriendLink.class);
            }
            if (Objects.isNull(commentDTO.getParentId()) || commentDTO.getParentId().equals(0)) {
                defaultGroupSequence.add(ValidationGroups.ParentIdNull.class);
            } else {
                defaultGroupSequence.add(ValidationGroups.ParentIdNotNull.class);
            }
        }
        return defaultGroupSequence;
    }
}
