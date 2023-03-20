import type { Result } from '@/model';
import request from '@/utils/request';
import type { AxiosPromise } from 'axios';
import type { LoginForm } from './types';
import type { BackendUserInfo } from '@/api/user/types';
import type { RouteRecordRaw } from 'vue-router';

/**
 * 用户登录
 * @param data 登录信息
 * @returns Token
 */
export function login(data: LoginForm): AxiosPromise<Result<string>> {
  return request({
    url: '/login',
    method: 'post',
    data
  });
}

/**
 * 用户退出登录
 */
export function logout(): AxiosPromise<Result<null>> {
  return request({
    url: '/logout',
    method: 'get'
  });
}

/**
 * 获取后台用户信息
 * @returns 后台用户信息
 */
export function getBackendUserInfo(): AxiosPromise<Result<BackendUserInfo>> {
  return request({
    url: '/admin/getBackendUserInfo',
    method: 'get'
  });
}

/**
 * 获取路由
 * @returns 路由信息
 * */
export function getRouters(): AxiosPromise<Result<RouteRecordRaw[]>> {
  return request({
    url: '/admin/getRouters',
    method: 'get'
  });
}