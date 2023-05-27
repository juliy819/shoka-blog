import type { Result } from '@/model';
import type { AxiosPromise } from 'axios';

export interface SiteApi {
  /**
   * 查看网站配置
   * @return 网站配置
   */
  getSiteConfig(): AxiosPromise<Result<SiteConfig>>;

  /**
   * 更新网站配置
   */
  updateSiteConfig(siteConfig: SiteConfig): AxiosPromise<Result<null>>;

  /**
   * 上传网站配置图片
   */
  uploadSiteImg(image: FormData): AxiosPromise<Result<string>>;
}

/**
 * 网站配置
 */
export interface SiteConfig {
  /**
   * 主键
   */
  id?: number;
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
   * 文章默认封面
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
