import defaultSettings from '@/settings';
import type { SettingState } from '@/store/interface';
import { defineStore } from 'pinia';
import { useDynamicTitle } from '@/utils/dynamicTitle';

const {
  sideTheme,
  tagsView,
  fixedHeader,
  sidebarLogo,
  dynamicTitle
} = defaultSettings;

const storageSetting = JSON.parse(localStorage.getItem('layout-setting') || '{}');

const useSettingStore = defineStore('useSettingStore', {
  state: (): SettingState => ({
    title: '',
    theme: storageSetting.theme || '#409EFF',
    sideTheme: storageSetting.sideTheme || sideTheme,
    tagsView: storageSetting.tagsView === undefined ? tagsView : storageSetting.tagsView,
    fixedHeader: storageSetting.fixedHeader === undefined ? fixedHeader : storageSetting.fixedHeader,
    sidebarLogo: storageSetting.sidebarLogo === undefined ? sidebarLogo : storageSetting.sidebarLogo,
    dynamicTitle: storageSetting.dynamicTitle === undefined ? dynamicTitle : storageSetting.dynamicTitle
  }),
  actions: {
    /**
     * 修改布局设置
     * @param data 设置项
     */
    changeSetting(data: { key: string; value: any }): void {
      const { key, value } = data;
      switch (key) {
        case 'theme':
          this.theme = value;
          break;
        case 'sideTheme':
          this.sideTheme = value;
          break;
        case 'tagsView':
          this.tagsView = value;
          break;
        case 'fixedHeader':
          this.fixedHeader = value;
          break;
        case 'sidebarLogo':
          this.sidebarLogo = value;
          break;
        case 'dynamicTitle':
          this.dynamicTitle = value;
          break;
        default:
          break;
      }
    },

    setTitle(title: string): void {
      this.title = title;
      useDynamicTitle();
    }

  },
  getters: {},
  persist: {
    enabled: true,
    strategies: [{ storage: localStorage }]
  }
});

export default useSettingStore;
