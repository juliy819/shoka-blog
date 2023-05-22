import type { Result } from '@/model';
import type { AxiosPromise } from 'axios';
import type { ArticleConditionList, ArticleQuery } from '@/api/article/types';

export interface CategoryApi {

  /**
   * 查看文章分类
   * @returns 文章分类
   */
  getCategoryList(): AxiosPromise<Result<Category[]>>;

  /**
   * 查看分类文章
   * @returns 文章分类
   */
  getCategoryArticleList(articleQuery: ArticleQuery): AxiosPromise<Result<ArticleConditionList>>;
}

/**
 * 分类
 */
export interface Category {

  /**
   * 分类id
   */
  id: number;

  /**
   * 分类名
   */
  categoryName: string;

  /**
   * 文章数量
   */
  articleCount: number;
}

/**
 * 分类VO
 */
export interface CategoryVO {

  /**
   * 分类id
   */
  id: number;

  /**
   * 分类名
   */
  categoryName: string;
}