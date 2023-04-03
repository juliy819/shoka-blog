/**
 * 应用
 */
export interface AppState {
  /**
   * 侧边栏是否打开
   */
  sideDrawerOpen: boolean;

  /**
   * 是否正在加载
   */
  loading: boolean;

  /**
   * 进度条间隔
   */
  npTimeout: number;

  /**
   * 加载间隔
   */
  loadingTimeout: number;
}

export interface ArticleState {

}

/**
 * 用户
 */
export interface UserState {
  /**
   * 用户id
   */
  id: number | null;

  /**
   * token
   */
  token: string | undefined;

  /**
   * 用户头像
   */
  avatar: string;

  /**
   * 角色
   */
  roles: string[];

  /**
   * 权限
   */
  permissions: string[];
}



