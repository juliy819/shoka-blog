import type { CategoryApi } from '@/api/category/types';
import request from '@/utils/request';

const categoryApi: CategoryApi = {
  getCategoryList() {
    return request({
      url: '/category/list',
      method: 'get'
    });
  },

  getCategoryArticleList(articleQuery) {
    return request({
      url: '/category/article',
      method: 'get',
      params: articleQuery
    });
  }
};

export default categoryApi;