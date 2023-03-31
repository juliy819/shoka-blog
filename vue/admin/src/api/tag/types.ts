import type { PageQuery, PageResult, Result } from '@/model';
import type { AxiosPromise } from 'axios';

/**
 * 标签api接口
 */
export interface TagApi {
  /**
   * 获取标签列表
   * @param tagQuery 查询条件
   * @returns 标签列表
   */
  getTagList(tagQuery: TagQuery): AxiosPromise<Result<PageResult<Tag[]>>>;

  /**
   * 添加标签
   * @param tagForm 标签信息
   */
  addTag(tagForm: TagForm): AxiosPromise<Result<null>>;

  /**
   * 修改标签
   * @param tagForm 标签信息
   */
  updateTag(tagForm: TagForm): AxiosPromise<Result<null>>;

  /**
   * 删除标签
   * @param tagIds 标签id列表
   */
  deleteTags(tagIds: number[]): AxiosPromise<Result<null>>;
}

/**
 * 标签
 */
export interface Tag {
  /**
   * 标签id
   */
  id: number;

  /**
   * 标签名
   */
  tagName: string;

  /**
   * 文章数量
   */
  articleCount: number;

  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 标签查询参数
 */
export interface TagQuery extends PageQuery {
  /**
   * 关键词
   */
  keywords?: string;
}

/**
 * 标签表单
 */
export interface TagForm {
  /**
   * 标签id
   */
  id?: number;
  /**
   * 标签名
   */
  tagName: string;
}