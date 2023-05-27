import request from '@/utils/request';
import type { CommentApi } from '@/api/comment/types';

const commentApi: CommentApi = {
  getCommentList(commentQuery) {
    return request({
      url: '/comment/admin/list',
      method: 'get',
      params: commentQuery
    });
  },

  deleteComment(commentIds) {
    return request({
      url: '/comment',
      method: 'delete',
      data: commentIds
    });
  },

  updateCommentCheck(checkDTO) {
    return request({
      url: '/comment/admin/check',
      method: 'put',
      data: checkDTO
    });
  }

};

export default commentApi;