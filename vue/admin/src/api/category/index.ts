import type { Category, CategoryForm, CategoryQuery } from './types';
import type { AxiosPromise } from 'axios';
import type { PageResult, Result } from '@/model';
import request from '@/utils/request';

/**
 * 获取分类列表
 * @param params 查询条件
 * @return 分类列表
 */
export const getCategoryList = (
    params: CategoryQuery
): AxiosPromise<Result<PageResult<Category[]>>> => {
  return request({
    url: '/category/admin/list',
    method: 'get',
    params
  });
};


/**
 * 添加分类
 * @param data 分类信息
 */
export const addCategory = (
    data: CategoryForm
): AxiosPromise<Result<null>> => {
  return request({
    url: '/category',
    method: 'post',
    data
  });
};

/**
 * 修改分类
 * @param data 分类信息
 */
export const updateCategory = (
    data: CategoryForm
): AxiosPromise<Result<null>> => {
  return request({
    url: '/category',
    method: 'put',
    data
  });
};

/**
 * 删除分类
 * @param categoryIds 标签id列表
 */
export const deleteCategories = (
    categoryIds: number[]
): AxiosPromise<Result<null>> => {
  return request({
    url: '/category/' + categoryIds,
    method: 'delete'
  });
};
