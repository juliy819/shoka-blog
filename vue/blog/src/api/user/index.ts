import type { UserApi } from '@/api/user/types';
import request from '@/utils/request';

const userApi: UserApi = {
  updatePassword(userForm) {
    return request({
      url: '/user/password',
      method: 'put',
      data: userForm
    });
  },

  updateEmail(emailForm) {
    return request({
      url: '/user/email',
      method: 'put',
      data: emailForm
    });
  },

  updateAvatar(avatar) {
    return request({
      url: '/user/avatar',
      method: 'post',
      headers: { 'content-type': 'multipart/form-data' },
      data: avatar
    });
  },

  updateUserInfo(userInfo) {
    return request({
      url: '/user/info',
      method: 'put',
      data: userInfo
    });
  }
};

export default userApi;