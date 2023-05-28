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
  }),

  getUserList: (userQuery) => request({
    url: '/user/list',
    method: 'get',
    params: userQuery
  }),

  getUserRoleList: () => request({
    url: '/user/role',
    method: 'get'
  }),

  updateUser: (userForm) => request({
    url: '/user/admin/update',
    method: 'put',
    data: userForm
  }),

  updateUserStatus: (userStatus) => request({
    url: '/user/status',
    method: 'put',
    data: userStatus
  })
};

export default userApi;
