import request from '@/utils/request';
import type { Tag, TagForm, TagQuery } from '@/api/tag/types';
import type { AxiosPromise } from 'axios';
import type { PageResult, Result } from '@/model';

/**
 * 获取标签列表
 * @param params 查询条件
 * @returns 标签列表
 */
export const getTagList = (
    params: TagQuery
): AxiosPromise<Result<PageResult<Tag[]>>> => {
  return request({
    url: '/tag/admin/list',
    method: 'get',
    params
  });
};

/**
 * 添加标签
 * @param data 标签信息
 */
export const addTag = (
    data: TagForm
): AxiosPromise<Result<null>> => {
  return request({
    url: '/tag',
    method: 'post',
    data
  });
};

/**
 * 修改标签
 * @param data 标签信息
 */
export const updateTag = (
    data: TagForm
): AxiosPromise<Result<null>> => {
  return request({
    url: '/tag',
    method: 'put',
    data
  });
};

/**
 * 删除标签
 * @param tagIds 标签id列表
 */
export const deleteTags = (
    tagIds: number[]
): AxiosPromise<Result<null>> => {
  return request({
    url: '/tag/' + tagIds,
    method: 'delete'
  });
};