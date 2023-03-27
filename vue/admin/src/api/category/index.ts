import type { Category, CategoryForm, CategoryQuery } from './types';
import type { AxiosPromise } from 'axios';
import type { PageResult, Result } from '@/model';
import request from '@/utils/request';

/**
 * 获取分类列表
 * @param params 查询条件
 * @return 分类列表
 */
export const getCategoryList =
    (params: CategoryQuery): AxiosPromise<Result<PageResult<Category[]>>> => {
      return request({
        url: '/admin/categories',
        method: 'get',
        params
      });
    };


/**
 * 添加分类
 * @param data 分类信息
 */
export const addCategory =
    (data: CategoryForm): AxiosPromise<Result<null>> => {
      return request({
        url: '/admin/categories',
        method: 'post',
        data
      });
    };

/**
 * 修改分类
 * @param data 分类信息
 */
export const updateCategory =
    (data: CategoryForm): AxiosPromise<Result<null>> => {
      return request({
        url: '/admin/categories',
        method: 'put',
        data
      });
    };

/**
 * 删除分类
 * @param data 分类id列表
 */
export const deleteCategories =
    (data: number[]): AxiosPromise<Result<null>> => {
      return request({
        url: '/admin/categories',
        method: 'delete',
        data
      });
    };
