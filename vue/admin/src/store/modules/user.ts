import { getAdminUserInfo, login, logout } from '@/api/login';
import type { LoginForm } from '@/api/login/types';
import { getToken, removeToken, setToken } from '@/utils/token';
import type { UserState } from '../interface';
import { defineStore } from 'pinia';
import defAva from '@/assets/images/avatar.jpg';

const useUserStore = defineStore('useUserStore', {
  state: (): UserState => ({
    token: getToken(),
    id: null,
    avatar: '',
    roles: [],
    permissions: []
  }),
  actions: {
    /** 登录 */
    login(loginForm: LoginForm) {
      return new Promise((resolve, reject) => {
        login(loginForm)
            .then(({ data }) => {
              if (data.flag) {
                setToken(data.data);
                this.token = data.data;
                resolve(data);
              } else {
                reject(data.msg);
              }
            })
            .catch(error => {
              reject(error);
            });
      });
    },
    /** 获取后台用户信息 */
    getAdminUserInfo() {
      return new Promise((resolve, reject) => {
        getAdminUserInfo()
            .then(({ data }) => {
              if (data.flag) {
                const userInfo = data.data;
                this.id = userInfo.id;
                this.avatar = (userInfo.avatar === '' || userInfo.avatar === null) ? defAva : userInfo.avatar;
                this.roles = userInfo.roleList;
                this.permissions = userInfo.permissionList;
              }
              resolve(data);
            })
            .catch(error => {
              reject(error);
            });
      });
    },
    /** 注销登录 */
    logout() {
      return new Promise((resolve, reject) => {
        logout()
            .then(() => {
              this.id = null;
              this.token = '';
              this.avatar = '';
              this.roles = [];
              this.permissions = [];
              removeToken();
              resolve(null);
            })
            .catch(error => {
              reject(error);
            });
      });
    }
  },
  getters: {}
});

export default useUserStore;
