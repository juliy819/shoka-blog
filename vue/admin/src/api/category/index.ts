import type { CategoryApi } from './types';
import request from '@/utils/request';

/**
 * 分类api
 */
export const categoryApi: CategoryApi = {

  getCategoryList: (categoryQuery) => request({
    url: '/category/admin/list',
    method: 'get',
    params: categoryQuery
  }),

  addCategory: (categoryForm) => request({
    url: '/category',
    method: 'post',
    data: categoryForm
  }),
  updateCategory: (categoryForm) => request({
    url: '/category',
    method: 'put',
    data: categoryForm
  }),

  deleteCategories: (categoryIds) => request({
    url: '/category/' + categoryIds,
    method: 'delete'
  })

};