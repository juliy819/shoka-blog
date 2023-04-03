import router from '@/router';
import useStore from '@/stores';

router.beforeEach(async (to, from, next) => {
  const { appStore } = useStore();
  appStore.startLoading();
  next();
});

router.afterEach(() => {
  const { appStore } = useStore();
  appStore.endLoading();
});