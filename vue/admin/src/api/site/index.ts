import request from '@/utils/request';
import type { SiteApi, SiteConfig } from '@/api/site/types';

const siteApi: SiteApi = {
  getSiteConfig() {
    return request({
      url: '/site',
      method: 'get'
    });
  },

  updateSiteConfig(siteConfig: SiteConfig) {
    return request({
      url: '/site',
      method: 'put',
      data: siteConfig
    });
  },

  uploadSiteImg(image: FormData) {
    return request({
      url: '/site/upload',
      method: 'post',
      headers: { 'content-type': 'multipart/form-data' },
      data: image
    });
  }
};

export default siteApi;