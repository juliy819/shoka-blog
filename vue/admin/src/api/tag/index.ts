import request from '@/utils/request';
import type { TagApi } from '@/api/tag/types';

/**
 * 标签api
 */
export const tagApi: TagApi = {

  getTagList: (tagQuery) => request({
    url: '/tag/admin/list',
    method: 'get',
    params: tagQuery
  }),

  addTag: (tagForm) => request({
    url: '/tag',
    method: 'post',
    data: tagForm
  }),

  updateTag: (tagForm) => request({
    url: '/tag',
    method: 'put',
    data: tagForm
  }),

  deleteTags: (tagIds) => request({
    url: '/tag/' + tagIds,
    method: 'delete'
  })
};
