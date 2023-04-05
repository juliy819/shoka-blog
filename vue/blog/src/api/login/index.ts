import request from '@/utils/request';
import type { LoginApi } from './types';

/**
 * 登录api
 */
const loginApi: LoginApi = {

  login: (loginForm) => request({
    url: '/login',
    method: 'post',
    data: loginForm
  }),

  register: (registerForm) => request({
    url: '/register',
    method: 'post',
    data: registerForm
  }),

  logout: () => request({
    url: '/logout',
    method: 'get'
  }),

  getUserInfo: () => request({
    url: '/user/getUserInfo',
    method: 'get'
  }),

  sendCode: (username) => request({
    url: '/sendCode',
    method: 'get',
    params: {
      username: username
    }
  })
};

export default loginApi;



