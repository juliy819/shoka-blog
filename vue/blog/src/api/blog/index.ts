import type { BlogApi } from '@/api/blog/types';
import request from '@/utils/request';

/**
 * 博客api
 */
const blogApi: BlogApi = {
  getBlogInfo: () => request({
    url: '/info',
    method: 'get'
  })
};

export default blogApi;