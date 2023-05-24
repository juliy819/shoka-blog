import router from '@/router';
import useStore from '@/stores';

router.beforeEach(async (to, from, next) => {
  const { appStore } = useStore();
  if (to.name === 'home') {
    appStore.headerChangeHeight = 700;
    document.title = 'SHOKA-BLOG';
  } else {
    appStore.headerChangeHeight = 480;
  }
  appStore.startLoading();
  next();
});

router.afterEach(() => {
  const { appStore } = useStore();
  appStore.endLoading();
});