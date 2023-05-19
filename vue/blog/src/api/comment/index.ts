import type { CommentApi } from '@/api/comment/types';
import request from '@/utils/request';

/**
 * 评论api
 */
const commentApi: CommentApi = {
  getRecentComments: () => request({
    url: 'comment/recent',
    method: 'get'
  }),

  getCommentList: (commentQuery) => request({
    url: 'comment/list',
    method: 'get',
    params: commentQuery
  }),

  getReplyList: (commentId, pageQuery) => request({
    url: `comment/${commentId}/reply`,
    method: 'get',
    params: pageQuery
  }),

  addComment: (commentForm) => request({
    url: '/comment',
    method: 'post',
    data: commentForm
  }),

  likeComment: (commentId) => request({
    url: `/comment/${commentId}/like`,
    method: 'post'
  })
};

export default commentApi;