import type { ArticleApi, Top } from '@/api/article/types';
import request from '@/utils/request';

/**
 * 文章api
 */
export const articleApi: ArticleApi = {

  getCategoryOptions: () => request({
    url: '/category/admin/listOptions',
    method: 'get'
  }),

  getTagOptions: () => request({
    url: '/tag/admin/listOptions',
    method: 'get'
  }),

  getArticleList: (queryParams) => request({
    url: 'article/admin/list',
    method: 'get',
    params: queryParams
  }),

  addArticle: (articleForm) => request({
    url: '/article',
    method: 'post',
    data: articleForm
  }),

  deleteArticles: (articleIds) => request({
    url: '/article/' + articleIds,
    method: 'delete'
  }),

  recycleArticles: (articleIds) => request({
    url: '/article/recycle',
    method: 'put',
    data: articleIds

  }),

  recoverArticles: (articleIds) => request({
    url: '/article/recover',
    method: 'put',
    data: articleIds
  }),

  updateArticle: (articleForm) => request({
    url: '/article',
    method: 'put',
    data: articleForm
  }),

  editArticle: (articleId) => request({
    url: '/article/edit/' + articleId,
    method: 'get'
  }),

  updateArticleTop: (topInfo: Top) => request({
    url: '/article/top',
    method: 'put',
    data: topInfo
  }),

  updateArticleFeatured: (featuredInfo) => request({
    url: '/article/featured',
    method: 'put',
    data: featuredInfo
  }),

  uploadImages: (image) => request({
    url: '/article/upload',
    headers: { 'content-type': 'multipart/form-data' },
    method: 'post',
    data: image
  })
};