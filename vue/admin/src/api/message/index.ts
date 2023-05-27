import request from '@/utils/request';
import type { MessageApi } from '@/api/message/types';

const messageApi: MessageApi = {
  getMessageList(messageQuery) {
    return request({
      url: '/message/admin/list',
      method: 'get',
      params: messageQuery
    });
  },

  deleteMessage(messageIds) {
    return request({
      url: '/message',
      method: 'delete',
      data: messageIds
    });
  },

  updateMessageCheck(checkDTO) {
    return request({
      url: '/message/admin/check',
      method: 'put',
      data: checkDTO
    });
  }

};

export default messageApi;