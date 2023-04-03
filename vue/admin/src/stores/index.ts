import useUserStore from './modules/user';
import useAppStore from '@/stores/modules/app';
import usePermissionStore from '@/stores/modules/permission';
import useSettingStore from '@/stores/modules/setting';
import useTagStore from '@/stores/modules/tag';

const useStore = () => ({
  appStore: useAppStore(),
  permissionStore: usePermissionStore(),
  settingStore: useSettingStore(),
  userStore: useUserStore(),
  tagStore: useTagStore()
});

export default useStore;
