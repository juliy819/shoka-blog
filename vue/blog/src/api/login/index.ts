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
  })
};



