import request from '@/utils/request';
import type { LoginApi } from './types';

/**
 * 登录api
 */
export const loginApi: LoginApi = {

  login: (loginForm) => request({
    url: '/login',
    method: 'post',
    data: loginForm
  }),

  logout: () => request({
    url: '/logout',
    method: 'get'
  }),

  getAdminUserInfo: () => request({
    url: '/admin/getAdminUserInfo',
    method: 'get'
  }),

  getRouters: () => request({
    url: '/admin/getRouters',
    method: 'get'
  })

};



