import type { Result } from '@/model';
import type { AxiosPromise } from 'axios';

export interface UserApi {

  /**
   * 修改密码
   * @param userForm 表单
   */
  updatePassword(userForm: UserForm): AxiosPromise<Result<null>>;

  /**
   * 修改头像
   * @param avatar 头像
   */
  updateAvatar(avatar: FormData): AxiosPromise<Result<string>>;

  /**
   * 修改邮箱
   * @param emailForm 邮箱表单
   */
  updateEmail(emailForm: EmailForm): AxiosPromise<Result<null>>;

  /**
   * 修改用户信息
   * @param userInfo 用户信息
   */
  updateUserInfo(userInfo: UserInfo): AxiosPromise<Result<null>>;
}

/**
 * 邮箱
 */
export interface EmailForm {
  /**
   * 邮箱号
   */
  email: string;
  /**
   * 验证码
   */
  code: string;
}

/**
 * 用户信息
 */
export interface UserInfo {
  /**
   * 昵称
   */
  nickname: string;
  /**
   * 个人网站
   */
  webSite: string;
  /**
   * 个人简介
   */
  intro: string;
}

/**
 * 用户信息
 */
export interface UserForm {
  /**
   * 用户名
   */
  username: string;
  /**
   * 密码
   */
  password: string;
  /**
   * 验证码
   */
  code: string;
}
