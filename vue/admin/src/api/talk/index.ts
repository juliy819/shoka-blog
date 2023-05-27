import type { TalkApi } from '@/api/talk/types';
import request from '@/utils/request';

const talkApi: TalkApi = {
  getTalkList: (talkQuery) => {
    return request({
      url: '/talk/admin/list',
      method: 'get',
      params: talkQuery
    });
  },

  uploadTalkCover: (image) => {
    return request({
      url: '/talk/upload',
      method: 'post',
      data: image
    });
  },

  editTalk: (talkId) => {
    return request({
      url: `/talk/edit/${talkId}`,
      method: 'get'
    });
  },

  addTalk: (talkForm) => {
    return request({
      url: '/talk',
      method: 'post',
      data: talkForm
    });
  },

  updateTalk: (talkForm) => {
    return request({
      url: '/talk',
      method: 'put',
      data: talkForm
    });
  },

  deleteTalk: (talkId) => {
    return request({
      url: `/talk/${talkId}`,
      method: 'delete'
    });
  }
};

export default talkApi;