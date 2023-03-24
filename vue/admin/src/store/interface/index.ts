import type { RouteLocationNormalized, RouteRecordRaw } from 'vue-router';

export interface TagsView extends Partial<RouteLocationNormalized> {
  title?: string;
}

export interface TagViewState {
  visitedViews: TagsView[];
}

/**
 * 应用
 */
export interface AppState {
  /**
   * 侧边栏
   */
  sidebar: {
    /**
     * true表示打开，false表示关闭
     */
    opened: boolean;

    /**
     *  true表示不显示动画，false表示显示动画
     */
    withoutAnimation: boolean;

    /**
     * true表示隐藏，false表示显示
     */
    hide: boolean;
  };

  /**
   *  响应式
   */
  device: 'desktop' | 'mobile';

  /**
   * 大小
   */
  size: string;
}

/**
 * 设置
 */
export interface SettingState {

  /**
   * 网页标题
   */
  title: string;

  /**
   * 网页主题
   */
  theme: string;

  /**
   * 侧边栏主题
   */
  sideTheme: string;

  /**
   * 是否显示 tagsView
   */
  tagsView: boolean;

  /**
   * 是否固定头部
   */
  fixedHeader: boolean;

  /**
   * 是否显示Logo
   */
  sidebarLogo: boolean;

  /**
   * 是否显示动态标题
   */
  dynamicTitle: boolean;
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

/**
 * 权限
 */
export interface PermissionState {
  /**
   * 路由
   */
  routes: RouteRecordRaw[];
}
