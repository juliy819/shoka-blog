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
  id?: number;

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
  loginType?: number;

  /**
   * 第三方登录之前的path
   */
  path: string;

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



