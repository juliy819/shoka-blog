import type { AxiosPromise } from 'axios';
import type { Result } from '@/model';

/**
 * 博客api接口
 */
export interface BlogApi {
  /**
   * 获取博客信息
   */
  getBlogInfo(): AxiosPromise<Result<BlogInfo>>;

  /**
   * 上传访客信息
   */
  report(): AxiosPromise<Result<null>>;
}

/**
 * 网站配置
 */
export interface SiteConfig {
  /**
   * 主键
   */
  id: number;
  /**
   * 用户头像
   */
  userAvatar: string;
  /**
   * 游客头像
   */
  touristAvatar: string;
  /**
   * 网站名称
   */
  siteName: string;
  /**
   * 网站地址
   */
  siteAddress: string;
  /**
   * 网站简介
   */
  siteIntro: string;
  /**
   * 网站公告
   */
  siteNotice: string;
  /**
   * 建站日期
   */
  createSiteTime: string;
  /**
   * 备案号
   */
  recordNumber: string;
  /**
   * 作者头像
   */
  authorAvatar: string;
  /**
   * 网站作者
   */
  siteAuthor: string;
  /**
   * 文章封面
   */
  articleCover: string;
  /**
   * 关于我
   */
  aboutMe: string;
  /**
   * Github
   */
  github: string;
  /**
   * Gitee
   */
  gitee: string;
  /**
   * 哔哩哔哩
   */
  bilibili: string;
  /**
   * QQ
   */
  qq: string;
  /**
   * 是否评论审核 (0否 1是)
   */
  commentCheck: number;
  /**
   * 是否留言审核 (0否 1是)
   */
  messageCheck: number;
  /**
   * 是否邮箱通知 (0否 1是)
   */
  emailNotice: number;
  /**
   * 社交列表
   */
  socialList: string;
  /**
   * 登录方式
   */
  loginList: string;
}

/**
 * 网站信息
 */
export interface BlogInfo {
  /**
   * 文章数量
   */
  articleCount: number;
  /**
   * 分类数量
   */
  categoryCount: number;
  /**
   * 标签数量
   */
  tagCount: number;
  /**
   * 网站访问量
   */
  viewCount: number;
  /**
   * 网站配置
   */
  siteConfig: SiteConfig;
}