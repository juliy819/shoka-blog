import type { AxiosPromise } from 'axios';
import type { PageQuery, PageResult, Result } from '@/model';

/**
 * 用户api接口
 */
export interface UserApi {
  /**
   * 修改管理员密码
   * @param password 密码
   */
  updateAdminPassword(password: Password): AxiosPromise<Result<null>>;

  /**
   * 获取用户列表
   * @param userQuery 查询条件
   * @return 用户列表
   */
  getUserList(userQuery: UserQuery): AxiosPromise<Result<PageResult<User[]>>>;

  /**
   * 获取用户角色选项
   * @return 用户角色选项
   */
  getUserRoleList(): AxiosPromise<Result<UserRole[]>>;

  /**
   * 修改用户
   * @param userForm 用户信息
   */
  updateUser(userForm: UserForm): AxiosPromise<Result<null>>;

  /**
   * 修改用户状态
   * @param userStatus 用户状态
   */
  updateUserStatus(userStatus: UserStatus): AxiosPromise<Result<null>>;
}

/**
 * 后台用户信息
 */
export interface AdminUserInfo {
  /**
   * 用户id
   */
  id: number;

  /**
   * 头像
   */
  avatar: string;

  /**
   * 角色集合
   */
  roleList: string[];

  /**
   * 权限集合
   */
  permissionList: string[];
}

/**
 * 用户角色
 */
export interface UserRole {
  /**
   * 角色id
   */
  id: number;
  /**
   * 角色名
   */
  roleName: string;
}

/**
 * 用户
 */
export interface User {
  /**
   * 用户id
   */
  id: number;
  /**
   * 用户昵称
   */
  nickname: string;
  /**
   * 用户头像
   */
  avatar: string;
  /**
   * 登录ip
   */
  ipAddress: string;
  /**
   * 登录地址
   */
  ipSource: string;
  /**
   * 登录方式
   */
  loginType: number;
  /**
   * 用户角色
   */
  roleList: UserRole[];
  /**
   * 是否禁用 (0否 1是)
   */
  isDisable: number;
  /**
   * 登录时间
   */
  loginTime: string;
  /**
   * 创建时间
   */
  createTime: string;
}

/**
 * 用户查询参数
 */
export interface UserQuery extends PageQuery {
  /**
   * 关键词
   */
  keywords?: string;
  /**
   * 登录方式
   */
  loginType: number;
}

/**
 * 用户表单
 */
export interface UserForm {
  /**
   * 用户id
   */
  id: number;
  /**
   * 昵称
   */
  nickname: string;
  /**
   * 角色id
   */
  roleIdList: number[];
}

/**
 * 用户状态
 */
export interface UserStatus {
  /**
   * 用户id
   */
  id: number;
  /**
   * 是否禁用 (0否 1是)
   */
  isDisable: number;
}

/**
 * 修改密码
 */
export interface Password {
  /**
   * 旧密码
   */
  oldPassword: string;

  /**
   * 新密码
   */
  newPassword: string;

  /**
   * 检查新密码
   */
  checkPassword: string;
}
