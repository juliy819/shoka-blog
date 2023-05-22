import type { PageQuery, PageResult, Result } from '@/model';
import type { AxiosPromise } from 'axios';

export interface ArchivesApi {
  /**
   * 查看文章归档
   * @param pageQuery 查询条件
   */
  getArchivesList(pageQuery: PageQuery): AxiosPromise<Result<PageResult<Archives[]>>>;
}

/**
 * 归档
 */
export interface Archives {
  /**
   * 文章id
   */
  id: number;
  /**
   * 文章标题
   */
  articleTitle: string;
  /**
   * 文章缩略图
   */
  articleCover: string;
  /**
   * 发布时间
   */
  createTime: string;
}
