import useStore from '@/store';
import defaultSettings from '@/settings';

/**
 * 动态修改标题
 */
export const useDynamicTitle = (): void => {
  const { settingStore } = useStore();
  if (settingStore.dynamicTitle) {
    document.title = settingStore.title + ' - ' + defaultSettings.title;
  } else {
    document.title = defaultSettings.title;
  }
};