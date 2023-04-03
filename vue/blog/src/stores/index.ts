import useAppStore from './modules/app';
import useBlogStore from '@/stores/modules/blog';
import useUserStore from '@/stores/modules/user';

const useStore = () => ({
  appStore: useAppStore(),
  blogStore: useBlogStore(),
  userStore: useUserStore()
});

export default useStore;