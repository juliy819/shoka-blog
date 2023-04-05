import type { ArticleApi } from '@/api/article/types';
import request from '@/utils/request';

/**
 * 文章api
 */
const articleApi: ArticleApi = {
  getArticleList: (pageQuery) => request({
    url: '/article/list',
    method: 'get',
    params: pageQuery
  }),

  getArticle: (articleId) => request({
    url: `/article/${articleId}`,
    method: 'get'
  }),

  getArticleFeatured: () => request({
    url: '/article/featured',
    method: 'get'
  }),

  likeArticle: (articleId) => request({
    url: `/article/${articleId}/like`,
    method: 'post'
  })

};

export default articleApi;