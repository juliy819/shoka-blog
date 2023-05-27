import type { BlogApi } from '@/api/blog/types';
import request from '@/utils/request';

const blogApi: BlogApi = {
  getBlogInfo() {
    return request({
      url: '/admin/info',
      method: 'get'
    });
  },

  report() {
    return request({
      url: '/report',
      method: 'post'
    });
  }
};

export default blogApi;