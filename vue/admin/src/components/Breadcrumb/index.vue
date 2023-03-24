<!--
 * @description 面包屑
 * @author JuLiy
 * @date 2023/3/20 13:57
-->
<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item, index) in levelList" :key="item.path">
        <span
          v-if="item.redirect === 'noRedirect' || index === levelList.length - 1"
          class="no-redirect">
          {{ item.meta.title }}
        </span>
        <a v-else @click.prevent="handleLink(item)">
          {{ item.meta.title }}
        </a>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script lang="ts" setup>

import type {
  RouteLocationMatched,
  RouteLocationRaw,
  RouteRecordRaw
} from 'vue-router';
import router from '@/router';

const route = useRoute();
const levelList = ref<RouteRecordRaw[]>([]);

const getBreadcrumb = () => {
  let matched = route.matched.filter(item => item.meta && item.meta.title) as any;
  const first = matched[0];
  // 若不是首页则添加一个可以跳转的首页标记
  if (!isDashboard(first)) {
    matched = [{ path: '/index', meta: { title: '首页' } }].concat(matched);
  }
  levelList.value = matched.filter((item: RouteRecordRaw) => item.meta && item.meta.title && item.meta.breadcrumb !== false);
};

/**
 * 判断是否为首页
 * @param route 路由项
 */
const isDashboard = (route: RouteLocationMatched): boolean => {
  const name = route && route.name;
  if (!name) {
    return false;
  }
  return name.toString().trim() === '首页';
};

/**
 * 点击后进行页面跳转
 * @param item 面包屑菜单
 */
const handleLink = (item: RouteRecordRaw) => {
  const { redirect, path } = item;
  if (redirect) {
    router.push(redirect as RouteLocationRaw);
  }
  router.push(path);
};

watchEffect(() => {
  if (route.path.startsWith('/redirect/')) {
    return;
  }
  getBreadcrumb();
});
</script>

<style lang="scss" scoped>
.app-breadcrumb {
  display: inline-block;
  line-height: 50px;
  margin-left: 8px;

  .no-redirect {
    color: #97a8be;
    cursor: text;
  }
}
</style>