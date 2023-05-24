import type { AppState } from '@/stores/types';
import nProgress from 'nprogress';
import { defineStore } from 'pinia';

nProgress.configure({
  easing: 'ease',
  speed: 1000,
  showSpinner: false,
  trickleSpeed: 200,
  minimum: 0.3
  // parent: '#loading-bar-wrapper'
});

const useAppStore = defineStore('useAppStore', {
  state: (): AppState => ({
    sideDrawer: false,
    loading: false,
    npTimeout: -1,
    loadingTimeout: -1,
    headerChangeHeight: 700,
    rightContainer: true,
    loginFrame: false,
    registerFrame: false,
    emailFrame: false,
    passwordFrame: false
  }),
  actions: {

    /**
     * 打开侧边栏
     */
    showSideDrawer() {
      this.sideDrawer = true;
    },

    /**
     * 显示登录窗口
     */
    showLoginFrame() {
      this.loginFrame = true;
    },

    /**
     * 显示注册窗口
     */
    showRegisterFrame() {
      this.registerFrame = true;
    },

    /**
     * 显示邮箱窗口
     */
    showEmailFrame() {
      this.emailFrame = true;
    },

    /**
     * 显示修改密码窗口
     */
    showPasswordFrame() {
      this.passwordFrame = true;
    },

    /**
     * 开始加载
     */
    startLoading() {
      if (this.loading) {
        return;
      }
      if (this.npTimeout !== -1) {
        clearTimeout(this.npTimeout);
      }
      if (this.loadingTimeout !== -1) {
        clearTimeout(this.loadingTimeout);
      }
      nProgress.start();
      this.loading = true;
    },

    /**
     * 结束加载
     */
    endLoading() {
      this.npTimeout = setTimeout(() => {
        nProgress.done();
      }, 100);
      this.loadingTimeout = setTimeout(() => {
        this.loading = false;
      }, 300);
    }
  }
});

export default useAppStore;