import type { AxiosPromise } from 'axios';
import type { PageResult, Result } from '@/model';
import type { Article, ArticleForm, ArticleQuery, CategoryOption, Featured, TagOption, Top } from '@/api/article/types';
import request from '@/utils/request';

/**
 * 获取分类选项
 * @returns 分类选项
 */
export const getCategoryOptions = (): AxiosPromise<Result<CategoryOption[]>> => {
  return request({
    url: '/category/admin/listOptions',
    method: 'get'
  });
};

/**
 * 获取标签选项
 * @returns 标签选项
 */
export const getTagOptions = (): AxiosPromise<Result<TagOption[]>> => {
  return request({
    url: '/tag/admin/listOptions',
    method: 'get'
  });
};

/**
 * 获取文章列表
 * @param params 查询条件
 * @return 文章列表
 */
export const getArticleList = (
    params: ArticleQuery
): AxiosPromise<Result<PageResult<Article[]>>> => {
  return request({
    url: 'article/admin/list',
    method: 'get',
    params
  });
};

/**
 * 添加文章
 * @param data 文章信息
 */
export const addArticle = (
    data: ArticleForm
): AxiosPromise<Result<null>> => {
  return request({
    url: '/article',
    method: 'post',
    data
  });
};

/**
 * 物理删除文章
 * @param articleIds 文章id列表
 */
export const deleteArticle = (
    articleIds: number[]
): AxiosPromise<Result<null>> => {
  return request({
    url: '/article/' + articleIds,
    method: 'delete'
  });
};

/**
 * 修改文章
 * @param data 文章信息
 */
export const updateArticle = (
    data: ArticleForm
): AxiosPromise<Result<null>> => {
  return request({
    url: '/article',
    method: 'put',
    data
  });
};

/**
 * 修改文章置顶状态
 * @param data 置顶信息
 */
export const updateArticleTop = (
    data: Top
): AxiosPromise<Result<null>> => {
  return request({
    url: '/article/top',
    method: 'put',
    data
  });
};

/**
 * 修改文章推荐状态
 * @param data 推荐信息
 */
export const updateArticleFeatured = (
    data: Featured
): AxiosPromise<Result<null>> => {
  return request({
    url: '/article/featured',
    method: 'put',
    data
  });
};

/**
 * 上传文章图片
 * @returns 图片链接
 */
export const uploadImages = (
    data: FormData
): AxiosPromise<Result<string>> => {
  return request({
    url: '/article/upload',
    headers: { 'content-type': 'multipart/form-data' },
    method: 'post',
    data
  });
};