import type { TagOption } from '../article/types';
import type { Category } from '../category/types';
import type { Result } from '@/model';
import type { AxiosPromise } from 'axios';

export interface BlogApi {
  /**
   * 获取博客后台信息
   * @returns 博客后台信息
   */
  getBlogInfo(): AxiosPromise<Result<BlogInfo>>;

  /**
   * 上传访客信息
   */
  report(): AxiosPromise<Result<null>>;
}

/**
 * 文章贡献统计
 */
export interface ArticleStatisticsVO {
  /**
   * 日期
   */
  date: Date;
  /**
   * 数量
   */
  count: number;
}

/**
 * 文章浏览量排行
 */
export interface ArticleRankVO {
  /**
   * 文章标题
   */
  articleTitle: string;
  /**
   * 浏览量
   */
  viewCount: number;
}

/**
 * 用户浏览量
 */
export interface UserViewVO {
  /**
   * 日期
   */
  date: string;
  /**
   * uv
   */
  uv: number;
  /**
   * pv
   */
  pv: number;
}

/**
 * 博客后台信息
 */
export interface BlogInfo {
  /**
   * 访问量
   */
  viewCount: number;
  /**
   * 留言量
   */
  messageCount: number;
  /**
   * 用户量
   */
  userCount: number;
  /**
   * 文章量
   */
  articleCount: number;
  /**
   * 文章分类
   */
  categoryList: Category[];
  /**
   * 文章标签
   */
  tagList: TagOption[];
  /**
   * 文章贡献统计
   */
  articleStatisticsList: ArticleStatisticsVO[];
  /**
   * 文章浏览量排行
   */
  articleRankList: ArticleRankVO[];
  /**
   * 用户浏览量
   */
  userViewList: UserViewVO[];
}
