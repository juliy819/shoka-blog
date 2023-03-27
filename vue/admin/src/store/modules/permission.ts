import type { PermissionState } from '@/store/interface';
import type { RouteRecordRaw } from 'vue-router';
import { constantRoutes } from '@/router';
import { getRouters } from '@/api/login';
import { defineStore } from 'pinia';
import ParentView from '@/components/ParentView/index.vue';
import Layout from '@/layout/index.vue';

// 匹配views里面所有的.vue文件
const modules = import.meta.glob('../../views/**/**.vue');

const usePermissionStore = defineStore('usePermissionStore', {
  state: (): PermissionState => ({
    routes: []
  }),
  actions: {
    setRoutes(routes: RouteRecordRaw[]) {
      this.routes = constantRoutes.concat(routes);
    },
    generateRoutes(): Promise<RouteRecordRaw[]> {
      return new Promise((resolve, reject) => {
        // 向后端请求路由数据
        getRouters()
            .then(({ data }) => {
              if (data.flag) {
                const routeData = data.data;
                const asyncRoutes = filterAsyncRoutes(routeData);
                this.setRoutes(asyncRoutes);
                resolve(asyncRoutes);
              }
            })
            .catch(error => {
              reject(error);
            });
      });
    }
  }
});

/** 遍历后台传来的路由字符串，转换为组件对象 */
const filterAsyncRoutes = (routes: RouteRecordRaw[]) => {
  const res: RouteRecordRaw[] = [];
  routes.forEach(route => {
    // 这里拿到的route中的属性均为字符串，不是RawRouteComponent等ts定义的类型
    // 需要将其解构并转为any类型以进行字符串比较判断，不然ts会报错
    const tmpRoute = { ...route } as any;
    // 判断类型
    if (tmpRoute.component === 'Layout') {
      tmpRoute.component = Layout;
    } else if (tmpRoute.component === 'ParentView') {
      tmpRoute.component = ParentView;
    } else {
      tmpRoute.component = loadView(tmpRoute.component);
    }
    res.push(tmpRoute);
    // 若包含子路由，则再依次转换添加
    if (tmpRoute.children) {
      tmpRoute.children = filterAsyncRoutes(tmpRoute.children);
    }
  });
  return res;
};

/** 获取路由对应的组件实例 */
const loadView = (routeStr: string) => {
  let res;
  for (const path in modules) {
    const dir = path.split('views/')[1].split('/index.vue')[0];
    if (dir === routeStr) {
      res = () => modules[path]();
    }
  }
  return res;
};

export default usePermissionStore;
