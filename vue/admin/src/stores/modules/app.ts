import type { AppState } from '../interface';
import { defineStore } from 'pinia';
import Cookies from 'js-cookie';

const useAppStore = defineStore('useAppStore', {
  state: (): AppState => ({
    sidebar: {
      opened: true,
      withoutAnimation: false,
      hide: false
    },
    device: 'desktop',
    size: 'default'
  }),
  actions: {
    /**
     * @description 控制侧边栏打开与关闭
     * @param withoutAnimation 是否启用动画 true表示不启用
     */
    toggleSidebar(withoutAnimation: boolean) {
      if (this.sidebar.hide) {
        return false;
      }
      this.sidebar.opened = !this.sidebar.opened;
      this.sidebar.withoutAnimation = withoutAnimation;
    },

    /**
     * @description 控制侧边栏隐藏状态
     * @param status 是否隐藏 true表示隐藏
     */
    toggleSidebarHide(status: boolean) {
      this.sidebar.hide = status;
    },

    /**
     * @description 关闭侧边栏
     * @param withoutAnimation 是否启用动画 true表示不启用
     */
    closeSidebar(withoutAnimation: boolean) {
      this.sidebar.opened = false;
      this.sidebar.withoutAnimation = withoutAnimation;
    },

    /**
     * @description 控制设备类型
     * @param device 设备类型
     */
    toggleDevice(device: 'desktop' | 'mobile') {
      this.device = device;
    },

    /**
     * @description 设置大小
     * @param size 大小
     */
    setSize(size: string) {
      this.size = size;
      Cookies.set('size', size);
    }
  },
  getters: {},
  //持久化存储
  persist: {
    enabled: true,
    strategies: [{ storage: localStorage }]
  }
});

export default useAppStore;
