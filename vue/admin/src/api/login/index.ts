import type { Result } from '@/model';
import request from '@/utils/request';
import type { AxiosPromise } from 'axios';
import type { LoginForm } from './types';
import type { BackendUserInfo } from '@/api/user/types';
import type { RouteRecordRaw } from 'vue-router';

/**
 * 用户登录
 * @param data 登录信息
 * @return Token
 */
export const login = (data: LoginForm): AxiosPromise<Result<string>> => request({
  url: '/login',
  method: 'post',
  data
});

/**
 * 用户退出登录
 */
export const logout = (): AxiosPromise<Result<null>> => request({
  url: '/logout',
  method: 'get'
});

/**
 * 获取后台用户信息
 * @return 后台用户信息
 */
export const getBackendUserInfo = (): AxiosPromise<Result<BackendUserInfo>> => request({
  url: '/admin/getBackendUserInfo',
  method: 'get'
});

/**
 * 获取路由
 * @return 路由信息
 * */
export const getRouters = (): AxiosPromise<Result<RouteRecordRaw[]>> => request({
  url: '/admin/getRouters',
  method: 'get'
});
