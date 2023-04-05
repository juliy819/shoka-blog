import type { PageQuery, PageResult, Result } from '@/model';
import type { AxiosPromise } from 'axios';

/**
 * 说说api接口
 */
export interface TalkApi {
  /**
   * 获取说说列表
   * @param pageQuery
   */
  getTalkList(pageQuery: PageQuery): AxiosPromise<Result<PageResult<Talk[]>>>;

  /**
   * 获取首页说说列表
   */
  getTalkHomeList(): AxiosPromise<Result<TalkHome[]>>;

  /**
   * 查看说说
   * @param talkId 说说id
   */
  getTalk(talkId: number): AxiosPromise<Result<Talk>>;

  /**
   * 点赞说说
   * @param talkId 说说id
   */
  likeTalk(talkId: number): AxiosPromise<Result<null>>;
}

/**
 * 说说
 */
export interface Talk {
  /**
   * 说说id
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
   * 说说内容
   */
  talkContent: string;
  /**
   * 图片列表
   */
  imgList: string[];
  /**
   * 是否置顶 (0否 1是)
   */
  isTop: number;
  /**
   * 点赞量
   */
  likeCount: number;
  /**
   * 评论量
   */
  commentCount: number;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 首页说说信息
 */
export interface TalkHome {
  /**
   * 说说id
   */
  id: number;

  /**
   * 说说内容
   */
  talkContent: string;
}