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
   * 用户注册
   * @param registerForm 注册表单
   */
  register(registerForm: RegisterForm): AxiosPromise<Result<null>>;

  /**
   * 用户注销登录
   */
  logout(): AxiosPromise<Result<null>>;

  /**
   * 获取用户信息
   * @return 用户信息
   */
  getUserInfo(): AxiosPromise<Result<UserInfo>>;

  /**
   * 发送邮箱验证码
   * @param username
   */
  sendCode(username: string): AxiosPromise<Result<null>>;
}

/**
 * 登录表单
 */
export interface LoginForm {

  /**
   * 用户名
   */
  username: string;

  /**
   * 密码
   */
  password: string;
}

/**
 * 注册表单
 */
export interface RegisterForm {

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

/**
 * 用户信息
 */
export interface UserInfo {

  /**
   * 用户id
   */
  id: number;

  /**
   * 头像
   */
  avatar: string;

  /**
   * 昵称
   */
  nickname: string;

  /**
   * 用户名
   */
  username: string;

  /**
   * 邮箱
   */
  email: string;

  /**
   * 个人网站
   */
  webSite: string;

  /**
   * 个人简介
   */
  intro: string;

  /**
   * 登录方式
   */
  loginType: number;

  /**
   * 文章点赞集合
   */
  articleLikeSet: number[];

  /**
   * 评论点赞集合
   */
  commentLikeSet: number[];

  /**
   * 说说点赞集合
   */
  talkLikeSet: number[];
}

