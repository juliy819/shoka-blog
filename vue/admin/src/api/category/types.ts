import type { PageQuery, PageResult, Result } from '@/model';
import type { AxiosPromise } from 'axios';

/**
 * 分类api接口
 */
export interface CategoryApi {
  /**
   * 获取分类列表
   * @param categoryQuery 查询条件
   * @return 分类列表
   */
  getCategoryList(categoryQuery: CategoryQuery): AxiosPromise<Result<PageResult<Category[]>>>;

  /**
   * 添加分类
   * @param categoryForm 分类信息
   */
  addCategory(categoryForm: CategoryForm): AxiosPromise<Result<null>>;

  /**
   * 修改分类
   * @param categoryForm 分类信息
   */
  updateCategory(categoryForm: CategoryForm): AxiosPromise<Result<null>>;

  /**
   * 删除分类
   * @param categoryIds 标签id列表
   */
  deleteCategories(categoryIds: number[]): AxiosPromise<Result<null>>;

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
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 分类查询参数
 */
export interface CategoryQuery extends PageQuery {
  /**
   * 关键词
   */
  keywords?: string;
}

/**
 * 分类表单
 */
export interface CategoryForm {
  /**
   * 分类id
   */
  id?: number;
  /**
   * 分类名
   */
  categoryName: string;
}