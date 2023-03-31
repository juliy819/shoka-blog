import type { AxiosPromise } from 'axios';
import type { Result } from '@/model';
import type { AdminUserInfo } from '@/api/user/types';
import type { RouteRecordRaw } from 'vue-router';

/**
 * 登录api接口
 */
export interface LoginApi {
  /**
   * 用户登录
   * @param loginForm 登录信息
   * @return Token
   */
  login(loginForm: LoginForm): AxiosPromise<Result<string>>;

  /**
   * 用户注销登录
   */
  logout(): AxiosPromise<Result<null>>;

  /**
   * 获取后台用户信息
   * @return 后台用户信息
   */
  getAdminUserInfo(): AxiosPromise<Result<AdminUserInfo>>;

  /**
   * 获取路由
   * @return 路由信息
   * */
  getRouters(): AxiosPromise<Result<RouteRecordRaw[]>>;

}

/**
 * 登录表单
 */
export interface LoginForm {

  /** 用户名 */
  username: string;

  /** 密码 */
  password: string;
}


