import router from '@/router';
import { getToken } from '@/utils/token';
import NProgress from 'nprogress';
import { isReLogin } from '@/utils/request';
import useStore from '@/store';
import { modal } from '@/utils/modal';

NProgress.configure({
  easing: 'ease',
  speed: 500,
  showSpinner: false,
  trickleSpeed: 200,
  minimum: 0.3
});

// 白名单路由
const whiteList = ['/login'];

router.beforeEach((to, from, next) => {
  NProgress.start();
  const { userStore, permissionStore } = useStore();
  // 判断是否有token
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' });
    } else {
      if (userStore.roles.length === 0) {
        isReLogin.show = true;
        // 判断当前用户是否已拉取完user_info信息
        userStore
          .getAdminUserInfo()
          .then(() => {
            isReLogin.show = false;
            permissionStore.generateRoutes().then(accessRoutes => {
              // 根据roles权限生成可访问的路由表
              accessRoutes.forEach(route => {
                // 动态添加可访问路由表
                router.addRoute(route);
              });
              // hack方法 确保addRoutes已完成
              // 原理: 若addRoute并未完成，路由守卫会一层一层的执行执行，直到addRoute完成，找到对应的路由
              // 作用: 若addRoute还未完成时就访问对应路由，则会因为找不到刚添加的路由而白屏
              next({ ...to, replace: true });
            });
          })
          .catch(err => {
            // 信息拉取失败则注销账户并重新转到登录页
            userStore.logout().then(() => {
              modal.msgError(err);
              next({ path: '/login' });
            }).catch(() => {
              next({ path: '/login' });
            });
          });
      } else {
        next();
      }
    }
  } else {
    // 未登录可以访问白名单页面(登录页面)
    if (whiteList.indexOf(to.path) !== -1) {
      next();
    }
    // 否则全部重定向到登录页
    else {
      next(`/login?redirect=${to.fullPath}`);
    }
  }
});

router.afterEach(() => {
  NProgress.done();
});
