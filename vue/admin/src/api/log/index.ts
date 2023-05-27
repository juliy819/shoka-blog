import type { LogApi } from '@/api/log/types';
import request from '@/utils/request';

const logApi: LogApi = {
  getOperationLogList: (logQuery) => {
    return request({
      url: '/log/operation',
      method: 'get',
      params: logQuery
    });
  },

  deleteOperation: (ids) => {
    return request({
      url: '/log/operation',
      method: 'delete',
      data: ids
    });
  },

  getExceptionLogList: (logQuery) => {
    return request({
      url: '/log/exception',
      method: 'get',
      params: logQuery
    });
  },

  deleteException: (ids) => {
    return request({
      url: '/log/exception',
      method: 'delete',
      data: ids
    });
  },

  getVisitLogList: (visitQuery) => {
    return request({
      url: '/log/visit',
      method: 'get',
      params: visitQuery
    });
  },

  deleteVisit: (ids) => {
    return request({
      url: '/log/visit',
      method: 'delete',
      data: ids
    });
  }
};

export default logApi;