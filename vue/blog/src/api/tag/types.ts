import type { Result } from '@/model';
import type { AxiosPromise } from 'axios';
import type { ArticleConditionList, ArticleQuery } from '@/api/article/types';

export interface TagApi {
  /**
   * 查看文章标签
   * @returns 文章标签
   */
  getTagList(): AxiosPromise<Result<Tag[]>>;

  /**
   * 查看标签文章
   * @returns 文章分类
   */
  getTagArticleList(articleQuery: ArticleQuery): AxiosPromise<Result<ArticleConditionList>>;

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
}

/**
 * 标签VO
 */
export interface TagVO {

  /**
   * 标签id
   */
  id: number;

  /**
   * 标签名
   */
  tagName: string;
}