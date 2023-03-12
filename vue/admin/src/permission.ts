import router from "@/router";
import { getToken } from "@/utils/token";
import NProgress from "nprogress";

NProgress.configure({
  easing: "ease",
  speed: 500,
  showSpinner: false,
  trickleSpeed: 200,
  minimum: 0.3
});

// 白名单路由
const whiteList = ["/login"];

router.beforeEach((to, from, next) => {
  NProgress.start();

  if (getToken()) {
    if (to.path === "/login") {
      next({ path: "/" });
    } else {
      next();
    }
    // else {
    //   if (user.roleList.length === 0) {
    //     isRelogin.show = true;
    //     // 判断当前用户是否已拉取完user_info信息
    //     user
    //       .GetInfo()
    //       .then(() => {
    //         isRelogin.show = false;
    //         permission.generateRoutes().then((accessRoutes) => {
    //           accessRoutes.forEach((route) => {
    //             router.addRoute(route);
    //           });
    //           next({ ...to, replace: true });
    //         });
    //       })
    //       .catch((err) => {
    //         user.LogOut().then(() => {
    //           ElMessage.error(err);
    //           next({ path: "/login" });
    //         });
    //       });
    //   } else {
    //     next();
    //   }
    // }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next();
    } else {
      next({ path: "/login" });
    }
  }

  // if (to.matched.some(record => record.meta.requireAuth)) {
  //   // 若需要认证，则判断是否已登录，若未登录则跳转至登录页面
  //   if (getToken()) {
  //     next();
  //     NProgress.done();
  //   } else {
  //     next({ path: "/login" });
  //     NProgress.done();
  //   }
  // } else {
  //   // 若不需要认证，则直接进入
  //   next();
  //   NProgress.done();
  // }
});

router.afterEach(() => {
  NProgress.done();
});