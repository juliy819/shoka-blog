import type { AxiosPromise } from 'axios';
import type { PageQuery, PageResult, Result } from '@/model';

export interface TalkApi {
  /**
   * 查看说说列表
   * @param talkQuery 查询条件
   * @returns 说说列表
   */
  getTalkList(talkQuery: TalkQuery): AxiosPromise<Result<PageResult<Talk[]>>>;

  /**
   * 上传说说图片
   * @param image 文件
   * @returns 说说图片链接
   */
  uploadTalkCover(image: FormData): AxiosPromise<Result<String>>;

  /**
   * 编辑说说
   * @param talkId 说说id
   * @returns 说说
   */
  editTalk(talkId: number): AxiosPromise<Result<TalkForm>>;

  /**
   * 添加说说
   * @param talkForm 说说信息
   */
  addTalk(talkForm: TalkForm): AxiosPromise<Result<null>>;

  /**
   * 修改说说
   * @param talkForm 说说信息
   */
  updateTalk(talkForm: TalkForm): AxiosPromise<Result<null>>;

  /**
   * 删除说说
   * @param talkId 说说id
   */
  deleteTalk(talkId: number): AxiosPromise<Result<null>>;
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
   * 说说状态 (1公开 2私密)
   */
  status: number;
  /**
   * 发布时间
   */
  createTime: string;
}

/**
 * 说说查询参数
 */
export interface TalkQuery extends PageQuery {
  /**
   * 状态
   */
  status?: number;
}

/**
 * 说说表单
 */
export interface TalkForm {
  /**
   * 说说id
   */
  id?: number;
  /**
   * 说说内容
   */
  talkContent: string;
  /**
   * 说说图片
   */
  images: string;
  /**
   * 图片列表
   */
  imgList?: string[];
  /**
   * 是否置顶 (0否 1是)
   */
  isTop: number;
  /**
   * 说说状态 (1公开 2私密)
   */
  status: number;
}