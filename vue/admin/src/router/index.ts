import type { RouteRecordRaw } from "vue-router";
import { createRouter, createWebHistory } from "vue-router";
import index from "@/views/IndexView.vue";

export const constantRoutes: RouteRecordRaw[] = [
  {
    path: "/redirect",
    component: index,
    children: [
      {
        path: "/redirect/:path(.*)",
        component: () => import("@/views/RedirectView.vue")
      }
    ]
  },
  {
    path: "/login",
    name: "登录",
    component: () => import("@/views/LoginView.vue")
  },
  {
    path: "/",
    name: "主页",
    component: () => import("@/views/IndexView.vue"),
    meta: {
      requireAuth: true
    }
  },
  {
    path: "/:pathMatch(.*)*",
    component: () => import("@/views/404NotFound.vue"),
    meta: {
      hidden: true
    }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes
});

export default router;
