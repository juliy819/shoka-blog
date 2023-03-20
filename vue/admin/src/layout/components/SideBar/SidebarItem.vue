<!--
* @description 侧边栏菜单项
* @author juliy
* @date 2023/03/20 11:09
!-->
<template>
  <!-- 若item没有meta属性(如登录页)或设置了hidden，则不渲染 -->
  <div v-if="!item.meta || !item.meta.hidden">
    <!-- 若没有子路由，则渲染为一级菜单 -->
    <template
      v-if="hasOneShowingChild(item, item.children) &&
        (!onlyOneChild.children || onlyOneChild.noShowingChildren)">
      <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
        <el-menu-item :index="resolvePath(onlyOneChild.path)">
          <el-icon>
            <svg-icon
              v-if="onlyOneChild.meta && onlyOneChild.meta.icon"
              :icon-class="onlyOneChild.meta.icon"
            />
          </el-icon>
          <template #title>
            {{ onlyOneChild.meta.title }}
          </template>
        </el-menu-item>
      </app-link>
    </template>
    <!-- 若有子路由则渲染为多级菜单 -->
    <el-sub-menu v-else :index="resolvePath(item.path)">
      <template #title>
        <el-icon>
          <svg-icon v-if="item.meta && item.meta.icon"
                    :icon-class="item.meta.icon" />
        </el-icon>
        <span v-if="item.meta && item.meta.title"
              class="menu-title">{{ item.meta.title }}</span>
      </template>
      <!-- 添加子菜单 -->
      <sidebar-item
        v-for="child in item.children"
        :key="child.path"
        :base-path="resolvePath(child.path)"
        :is-nest="true"
        :item="child"
        class="nest-menu"
      />
    </el-sub-menu>
  </div>
</template>

<script lang="ts" setup>
import type { RouteRecordRaw } from 'vue-router';
import AppLink from './Link.vue';
import type { UnwrapRef } from 'vue';

const onlyOneChild = ref();
const props = defineProps({
  item: {
    type: Object as () => UnwrapRef<RouteRecordRaw>,
    required: true
  },
  basePath: {
    type: String,
    required: true
  },
  isNest: {
    type: Boolean,
    required: false
  }
});

/**
 * @description 判断是否只有一个子菜单
 * @param parent
 * @param children
 * @return {*}
 */
const hasOneShowingChild = (parent: any, children?: Array<UnwrapRef<RouteRecordRaw>>): boolean => {
  if (!children) {
    children = [];
  }
  const showingChildren = children.filter((item) => {
    if (item.meta && item.meta.hidden) {
      return false;
    } else {
      // 暂设，只有一个子路由的情况下就不用再设置了
      onlyOneChild.value = item;
      return true;
    }
  });
  // 只有一个子路由时，直接显示子路由
  if (showingChildren.length === 1) {
    return true;
  }
  // 若没有子路由则显示父路由本身
  if (showingChildren.length === 0) {
    onlyOneChild.value = { ...parent, path: '', noShowingChildren: true };
    return true;
  }
  return false;
};

const resolvePath = (routePath: string) => {
  return getNormalPath(props.basePath + '/' + routePath);
};

const getNormalPath = (p: string) => {
  if (p.length === 0 || !p || p == 'undefined') {
    return p;
  }
  let res = p.replace('//', '/');
  if (res[res.length - 1] === '/') {
    return res.slice(0, res.length - 1);
  }
  return res;
};
</script>

<style scoped></style>
