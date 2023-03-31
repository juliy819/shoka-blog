<!--
 * @description 布局页
 * @author juliy
 * @date 2023-03-12 12:00
-->
<template>
  <div :class="classObj" class="app-wrapper">
    <!-- 菜单背景 -->
    <div v-if="device === 'mobile' && appStore.sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
    <!-- 侧边栏 -->
    <sidebar class="sidebar-container" />
    <div :class="{ 'hasTagsView': needTagsView }" class="main-container">
      <div :class="{ 'fixed-header': fixedHeader }">
        <!-- 导航栏 -->
        <navbar @setLayout="setLayout" />
        <!-- 标签栏 -->
        <tags-view v-if="needTagsView" />
      </div>
      <!-- 主页面 -->
      <app-main />
      <!-- 设置 -->
      <settings ref="settingsRef" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import AppMain from './components/AppMain/index.vue';
import Navbar from '@/layout/components/Navbar/index.vue';
import Sidebar from '@/layout/components/Sidebar/index.vue';
import TagsView from './components/TagsView/index.vue';
import { useWindowSize } from '@vueuse/core';
import useStore from '@/store';

const { appStore, settingStore } = useStore();
const { width } = useWindowSize();
const WIDTH = 992;
const settingsRef = ref();

const device = computed(() => appStore.device);
const needTagsView = computed(() => settingStore.tagsView);
const fixedHeader = computed(() => settingStore.fixedHeader);
const classObj = computed(() => ({
  hideSidebar: !appStore.sidebar.opened,
  openSidebar: appStore.sidebar.opened,
  withoutAnimation: appStore.sidebar.withoutAnimation,
  mobile: device.value === 'mobile'
}));

watchEffect(() => {
  // 根据浏览器宽度判断是电脑还是手机
  if (width.value - 1 < WIDTH) {
    appStore.toggleDevice('mobile');
    appStore.closeSidebar(true);
  } else {
    appStore.toggleDevice('desktop');
  }
});

/**
 * 手机模式下点击菜单外区域时收起菜单
 */
const handleClickOutside = (): void => {
  appStore.closeSidebar(false);
};

/**
 * 打开设置页面
 */
const setLayout = (): void => {
  settingsRef.value.openSettings();
};
</script>

<style lang="scss" scoped>
@import '@/assets/styles/mixin.scss';
@import '@/assets/styles/variables.module.scss';

.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;

  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }
}

.drawer-bg {
  background: #000;
  opacity: 0.3;
  width: 100%;
  top: 0;
  height: 100%;
  position: absolute;
  z-index: 999;
}

.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 40;
  width: calc(100% - #{$base-sidebar-width});
  transition: width 0.28s;
}


.hideSidebar .fixed-header {
  width: calc(100% - #{$base-sidebar-width-closed});
}

.mobile .fixed-header {
  width: 100%;
}
</style>