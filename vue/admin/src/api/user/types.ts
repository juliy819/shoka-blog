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
