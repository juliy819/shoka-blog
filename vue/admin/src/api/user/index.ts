import type { UserApi } from '@/api/user/types';
import request from '@/utils/request';

/**
 * 用户api
 */
export const userApi: UserApi = {

  updateAdminPassword: (password) => request({
    url: '/admin/password',
    method: 'put',
    data: password
  })
};
