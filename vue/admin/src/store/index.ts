import useUserStore from './modules/user';
import useAppStore from '@/store/modules/app';
import usePermissionStore from '@/store/modules/permission';
import useSettingStore from '@/store/modules/setting';

const useStore = () => ({
  appStore: useAppStore(),
  permissionStore: usePermissionStore(),
  settingStore: useSettingStore(),
  userStore: useUserStore()
});

export default useStore;
