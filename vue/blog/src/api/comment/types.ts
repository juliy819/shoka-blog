import type { PageQuery, PageResult, Result } from '@/model';
import type { AxiosPromise } from 'axios';

/**
 * 评论api接口
 */
export interface CommentApi {

  /**
   * 获取最新评论
   * @return 最新评论
   */
  getRecentComments(): AxiosPromise<Result<RecentComment[]>>;

  /**
   * 获取评论列表
   * @param commentQuery 评论查询参数
   * @return 评论列表
   */
  getCommentList(commentQuery: CommentQuery): AxiosPromise<Result<PageResult<Comment[]>>>;

  /**
   * 获取回复列表
   * @param commentId 评论id
   * @param pageQuery 分页参数
   * @return 回复评论列表
   */
  getReplyList(commentId: number, pageQuery: PageQuery): AxiosPromise<Result<Reply[]>>;

  /**
   * 添加评论
   * @param commentForm 评论表单
   */
  addComment(commentForm: CommentForm): AxiosPromise<Result<null>>;

  /**
   * 点赞评论
   * @param commentId 评论id
   */
  likeComment(commentId: number): AxiosPromise<Result<null>>;
}

/**
 * 最近评论
 */
export interface RecentComment {

  /**
   * 评论id
   */
  id: number;

  /**
   * 昵称
   */
  nickname: string;

  /**
   * 头像
   */
  avatar: string;

  /**
   * 评论内容
   */
  commentContent: string;

  /**
   * 评论时间
   */
  createTime: string;
}

/**
 * 评论查询参数
 */
export interface CommentQuery extends PageQuery {

  /**
   * 类型id
   */
  typeId?: number;

  /**
   * 评论类型
   */
  commentType: number;
}

/**
 * 回复
 */
export interface Reply {

  /**
   * 评论id
   */
  id: number;

  /**
   * 父级评论id
   */
  parentId: number;

  /**
   * 评论用户id
   */
  fromUid: number;

  /**
   * 被评论用户id
   */
  toUid: number;

  /**
   * 评论用户昵称
   */
  fromNickname: string;

  /**
   * 被评论用户昵称
   */
  toNickname: string;

  /**
   * 头像
   */
  avatar: string;

  /**
   * 评论内容
   */
  commentContent: string;

  /**
   * 点赞数
   */
  likeCount: number;

  /**
   * 评论时间
   */
  createTime: string;
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
   * 评论用户id
   */
  fromUid: number;

  /**
   * 昵称
   */
  fromNickname: string;

  /**
   * 头像
   */
  avatar: string;

  /**
   * 评论内容
   */
  commentContent: string;

  /**
   * 点赞数
   */
  likeCount: number;

  /**
   * 回复量
   */
  replyCount: number;

  /**
   * 回复列表
   */
  replyList: Reply[];

  /**
   * 评论时间
   */
  createTime: string;
}

/**
 * 评论表单
 */
export interface CommentForm {
  /**
   * 类型id
   */
  typeId?: number;
  /**
   * 评论类型 (1文章 2友链 3说说)
   */
  commentType: number;
  /**
   * 父评论id
   */
  parentId?: number;
  /**
   * 被回复评论id
   */
  replyId?: number;
  /**
   * 被回复用户id
   */
  toUid?: number;
  /**
   * 评论内容
   */
  commentContent: string;
}