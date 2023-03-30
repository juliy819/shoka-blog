import type { RouteRecordRaw } from 'vue-router';
import { createRouter, createWebHistory } from 'vue-router';
import Layout from '@/layout/index.vue';

declare module 'vue-router' {
  // noinspection JSUnusedGlobalSymbols
  interface RouteMeta {
    /**
     * 是否隐藏
     */
    hidden?: boolean;

    /**
     * 图标
     */
    icon?: string;

    /**
     * 标题
     */
    title?: string;

    /**
     * 是否固定
     */
    affix?: boolean;
  }
}

export const constantRoutes: RouteRecordRaw[] = [
  {
    path: '/redirect',
    component: Layout,
    meta: {
      hidden: true
    },
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index.vue')
      }
    ]
  },
  {
    path: '/login',
    name: '登录',
    component: () => import('@/views/login/index.vue'),
    meta: {
      hidden: true
    }
  },
  {
    path: '/:pathMatch(.*)*',
    component: () => import('@/views/404NotFound/index.vue'),
    meta: {
      hidden: true
    }
  },
  {
    path: '',
    component: Layout,
    redirect: '/index',
    children: [
      {
        path: '/index',
        name: '首页',
        meta: {
          title: '首页',
          icon: 'dashboard',
          affix: true
        },
        component: () => import('@/views/index/index.vue')
      }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
  }
});

export default router;
