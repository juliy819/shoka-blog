import type { AxiosPromise } from 'axios';
import type { Result } from '@/model';

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


