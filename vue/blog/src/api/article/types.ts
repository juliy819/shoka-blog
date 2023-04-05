import type { CategoryVO } from '@/api/category/types';
import type { TagVO } from '@/api/tag/types';
import type { PageQuery, PageResult, Result } from '@/model';
import type { AxiosPromise } from 'axios';

/**
 * 文章api
 */
export interface ArticleApi {
  /**
   * 获取文章列表
   * @param pageQuery 查询条件
   * @returns 文章列表
   */
  getArticleList(pageQuery: PageQuery): AxiosPromise<Result<PageResult<Article[]>>>;

  /**
   * 获取文章信息
   * @param articleId 文章id
   */
  getArticle(articleId: number): AxiosPromise<Result<ArticleInfo>>;

  /**
   * 查看推荐文章
   * @returns 推荐文章
   */
  getArticleFeatured(): AxiosPromise<Result<ArticleFeatured[]>>;

  /**
   * 点赞文章
   * @param articleId 文章id
   */
  likeArticle(articleId: number): AxiosPromise<Result<null>>;
}

/**
 * 文章
 */
export interface Article {

  /**
   * 文章id
   */
  id: number;

  /**
   * 文章缩略图
   */
  articleCover: string;

  /**
   * 文章标题
   */
  articleTitle: string;

  /**
   * 文章内容
   */
  articleContent: string;

  /**
   * 文章分类
   */
  category: CategoryVO;

  /**
   * 文章标签
   */
  tagVOList: TagVO[];

  /**
   * 是否置顶 (0否 1是)
   */
  isTop: number;

  /**
   * 发表时间
   */
  createTime: string;
}

/**
 * 文章上下篇
 */
export interface ArticleNavigation {

  /**
   * 文章id
   */
  id: number;

  /**
   * 文章缩略图
   */
  articleCover: string;

  /**
   * 文章标题
   */
  articleTitle: string;
}

/**
 * 文章信息
 */
export interface ArticleInfo {
  /**
   * 文章id
   */
  id: number;
  /**
   * 文章缩略图
   */
  articleCover: string;
  /**
   * 文章标题
   */
  articleTitle: string;
  /**
   * 文章内容
   */
  articleContent: string;
  /**
   * 文章类型 (1原创 2转载 3翻译)
   */
  articleType: number;
  /**
   * 浏览量
   */
  viewCount: number;
  /**
   * 点赞量
   */
  likeCount: number;
  /**
   * 文章分类
   */
  category: CategoryVO;
  /**
   * 文章标签
   */
  tagVOList: TagVO[];
  /**
   * 发表时间
   */
  createTime: string;
  /**
   * 上一篇文章
   */
  lastArticle: ArticleNavigation;
  /**
   * 下一篇文章
   */
  nextArticle: ArticleNavigation;
  /**
   * 更新时间
   */
  updateTime: string;
}

/**
 * 推荐文章
 */
export interface ArticleFeatured {

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

/**
 * 分类、标签下显示的文章信息
 */
export interface ArticleCondition {

  /**
   * 文章id
   */
  id: number;

  /**
   * 文章缩略图
   */
  articleCover: string;

  /**
   * 文章标题
   */
  articleTitle: string;

  /**
   * 文章分类
   */
  category: CategoryVO;

  /**
   * 文章标签
   */
  tagVOList: TagVO[];

  /**
   * 发布时间
   */
  createTime: string;
}

/**
 * 文章条件列表
 */
export interface ArticleConditionList {

  /**
   * 文章列表
   */
  articleConditionVOList: ArticleCondition[];

  /**
   * 条件名
   */
  name: string;
}

/**
 * 文章查询参数
 */
export interface ArticleQuery extends PageQuery {

  /**
   * 分类id
   */
  categoryId: number;

  /**
   * 标签id
   */
  tagId: number;
}