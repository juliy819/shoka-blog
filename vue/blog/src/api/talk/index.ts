import type { TalkApi } from '@/api/talk/types';
import request from '@/utils/request';

/**
 * 说说api
 */
const talkApi: TalkApi = {
  getTalk: (talkId) => request({
    url: `/talk/${talkId}`,
    method: 'get'
  }),

  getTalkHomeList: () => request({
    url: '/talk/listHome',
    method: 'get'
  }),

  getTalkList: (pageQuery) => request({
    url: '/talk/list',
    method: 'get',
    params: pageQuery
  }),

  likeTalk: (talkId) => request({
    url: `/talk/${talkId}/like`,
    method: 'post'
  })
};

export default talkApi;