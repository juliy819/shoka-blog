package com.juliy.model.dto;

import com.juliy.annotation.CommentType;
import com.juliy.validator.CommentProvider;
import com.juliy.validator.ValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.group.GroupSequenceProvider;

/**
 * 评论DTO
 * @author juliy
 * @date 2023/5/16 15:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@GroupSequenceProvider(value = CommentProvider.class)
@Schema(description = "评论DTO")
public class CommentDTO {

    /**
     * 类型id
     */
    @NotNull(message = "类型id不能为空", groups = {ValidationGroups.ArticleTalk.class})
    @Null(message = "类型id必须为空", groups = {ValidationGroups.FriendLink.class})
    @Schema(description = "类型id")
    private Integer typeId;

    /**
     * 评论类型 (1文章 2友链 3说说)
     */
    @CommentType(values = {1, 2, 3}, message = "评论类型只能为1、2、3")
    @NotNull(message = "评论类型不能为空")
    @Schema(description = "评论类型 (1文章 2友链 3说说)")
    private Integer commentType;

    /**
     * 父评论id
     */
    @Null(groups = {ValidationGroups.ParentIdNull.class})
    @NotNull(groups = {ValidationGroups.ParentIdNotNull.class})
    @Schema(description = "父评论id")
    private Integer parentId;

    /**
     * 被回复评论id
     */
    @Null(message = "回复评论id和回复用户id必须都为空", groups = {ValidationGroups.ParentIdNull.class})
    @NotNull(message = "回复评论id和回复用户id不能为空", groups = {ValidationGroups.ParentIdNotNull.class})
    @Schema(description = "被回复评论id")
    private Integer replyId;

    /**
     * 被回复用户id
     */
    @Null(message = "回复评论id和回复用户id必须都为空", groups = {ValidationGroups.ParentIdNull.class})
    @NotNull(message = "回复评论id和回复用户id不能为空", groups = {ValidationGroups.ParentIdNotNull.class})
    @Schema(description = "被回复用户id")
    private Integer toUid;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    @Schema(description = "评论内容")
    private String commentContent;

}
