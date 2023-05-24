import type { MessageApi } from '@/api/message/types';
import request from '@/utils/request';

const messageApi: MessageApi = {
  getMessageList() {
    return request({
      url: '/message/list',
      method: 'get'
    });
  },

  addMessage(messageForm) {
    return request({
      url: '/message',
      method: 'post',
      data: messageForm
    });
  }
};

export default messageApi;