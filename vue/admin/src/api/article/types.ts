import type { PageQuery, PageResult, Result } from '@/model';
import type { AxiosPromise } from 'axios';

/**
 * 文章api接口
 */
export interface ArticleApi {

  /**
   * 获取分类选项
   * @return 分类选项
   */
  getCategoryOptions(): AxiosPromise<Result<CategoryOption[]>>;

  /**
   * 获取标签选项
   * @return 标签选项
   */
  getTagOptions(): AxiosPromise<Result<TagOption[]>>;

  /**
   * 获取文章列表
   * @param queryParams 查询条件
   * @return 文章列表
   */
  getArticleList(queryParams: ArticleQuery): AxiosPromise<Result<PageResult<Article[]>>>;

  /**
   * 添加文章
   * @param articleForm 文章信息
   */
  addArticle(articleForm: ArticleForm): AxiosPromise<Result<null>>;

  /**
   * 物理删除文章
   * @param articleIds 文章id列表
   */
  deleteArticles(articleIds: number[]): AxiosPromise<Result<null>>;

  /**
   * 回收文章
   * @param articleIds 文章id列表
   */
  recycleArticles(articleIds: number[]): AxiosPromise<Result<null>>;

  /**
   * 恢复文章
   * @param articleIds 文章id列表
   */
  recoverArticles(articleIds: number[]): AxiosPromise<Result<null>>;

  /**
   * 修改文章
   * @param articleForm 文章信息
   */
  updateArticle(articleForm: ArticleForm): AxiosPromise<Result<null>>;

  /**
   * 编辑文章
   * @param articleId 文章id
   * @return Article
   */
  editArticle(articleId: number): AxiosPromise<Result<ArticleForm>>;

  /**
   * 修改文章置顶状态
   * @param topInfo 置顶信息
   */
  updateArticleTop(topInfo: Top): AxiosPromise<Result<null>>;

  /**
   * 修改文章推荐状态
   * @param featuredInfo 推荐信息
   */
  updateArticleFeatured(featuredInfo: Featured): AxiosPromise<Result<null>>;

  /**
   * 上传文章图片
   * @param image 图片
   * @return 图片链接
   */
  uploadImages(image: FormData): AxiosPromise<Result<string>>;
}

/**
 * 文章查询参数
 */
export interface ArticleQuery extends PageQuery {
  /**
   * 关键字
   */
  keywords?: string;
  /**
   * 是否删除 (0否 1是)
   */
  isDelete: number;
  /**
   * 状态 (1公开 2私密 3草稿)
   */
  status?: number;
  /**
   * 分类id
   */
  categoryId?: number;
  /**
   * 文章类型
   */
  articleType?: number;
  /**
   * 标签id
   */
  tagId?: number;
}

/**
 * 文章表单
 */
export interface ArticleForm {
  /**
   * 文章id
   */
  id?: number;

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
   * 分类名
   */
  categoryName: string;

  /**
   * 标签名
   */
  tagNameList: string[];

  /**
   * 是否置顶 (0否 1是)
   */
  isTop: number;

  /**
   * 是否推荐 (0否 1是)
   */
  isFeatured: number;

  /**
   * 状态 (1公开 2私密 3草稿)
   */
  status: number;
}

/**
 * 分类
 */
export interface CategoryOption {
  /**
   * 分类id
   */
  id: number;

  /**
   * 分类名
   */
  categoryName: string;
}

/**
 * 标签
 */
export interface TagOption {
  /**
   * 标签id
   */
  id: number;
  /**
   * 标签名
   */
  tagName: string;
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
   * 文章类型 (1原创 2转载 3翻译)
   */
  articleType: number;

  /**
   * 是否置顶 (0否 1是)
   */
  isTop: number;

  /**
   * 是否推荐 (0否 1是)
   */
  isFeatured: number;

  /**
   * 是否删除 (0否 1是)
   */
  isDelete: number;

  /**
   * 状态 (1公开 2私密 3草稿)
   */
  status: number;

  /**
   * 点赞量
   */
  likeCount: number;

  /**
   * 浏览量
   */
  viewCount: number;

  /**
   * 文章分类名
   */
  categoryName: string;

  /**
   * 文章标签
   */
  tagList: TagOption[];

  /**
   * 发表时间
   */
  createTime: string;
}

/**
 * 置顶状态
 */
export interface Top {
  /**
   * 文章id
   */
  id: number;
  /**
   * 是否置顶 (0否 1是)
   */
  isTop: number;
}

/**
 * 推荐状态
 */
export interface Featured {
  /**
   * 文章id
   */
  id: number;
  /**
   * 是否推荐 (0否 1是)
   */
  isFeatured: number;
}