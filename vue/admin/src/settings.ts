import type { SettingState } from '@/store/interface';

const defaultSettings: SettingState = {
  dynamicTitle: false,
  fixedHeader: true,
  sideTheme: 'theme-dark',
  sidebarLogo: true,
  tagsView: true,
  theme: '',
  title: import.meta.env.VITE_APP_TITLE
};

export default defaultSettings;
