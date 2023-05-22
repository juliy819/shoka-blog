import type { TagApi } from '@/api/tag/types';
import request from '@/utils/request';

const tagApi: TagApi = {
  getTagList() {
    return request({
      url: '/tag/list',
      method: 'get'
    });
  },

  getTagArticleList(articleQuery) {
    return request({
      url: '/tag/article',
      method: 'get',
      params: articleQuery
    });
  }
};

export default tagApi;