import type { CheckDTO, PageQuery, PageResult, Result } from '@/model';
import type { AxiosPromise } from 'axios';

export interface CommentApi {
  /**
   * 查看评论列表
   * @param commentQuery 查询条件
   * @returns 评论列表
   */
  getCommentList: (commentQuery: CommentQuery) => AxiosPromise<Result<PageResult<Comment[]>>>;

  /**
   * 删除评论
   * @param data 评论id集合
   */
  deleteComment: (commentIds: number[]) => AxiosPromise<Result<null>>;

  /**
   * 审核评论
   * @param checkDTO 审核信息
   */
  updateCommentCheck: (checkDTO: CheckDTO) => AxiosPromise<Result<null>>;
}

/**
 * 评论
 */
export interface Comment {
  /**
   * 评论id
   */
  id: number;
  /**
   * 头像
   */
  avatar: string;
  /**
   * 评论用户昵称
   */
  fromNickname: string;
  /**
   * 被回复用户昵称
   */
  toNickname: string;
  /**
   * 文章标题
   */
  articleTitle: string;
  /**
   * 评论内容
   */
  commentContent: string;
  /**
   * 评论类型
   */
  commentType: number;
  /**
   * 是否通过 (0否 1是)
   */
  isCheck: number;
  /**
   * 发表时间
   */
  createTime: string;
}

/**
 * 评论查询参数
 */
export interface CommentQuery extends PageQuery {
  /**
   * 关键词
   */
  keywords?: string;
  /**
   * 评论类型
   */
  commentType: number;
  /**
   * 是否通过 (0否 1是)
   */
  isCheck: number;
}
