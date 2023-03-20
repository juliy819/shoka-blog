<!--
 * @description 布局页
 * @author juliy
 * @date 2023-03-12 12:00
-->
<template>
  <div :class="classObj" class="app-wrapper">
    <!-- 菜单背景 -->
    <div
      v-if="device === 'mobile' && appStore.sidebar.opened"
      class="drawer-bg"
      @click="handleClickOutside" />
    <!-- 侧边栏 -->
    <SideBar class="sidebar-container"></SideBar>
    <div class="main-container">
      <NavBar @setLayout="setLayout"></NavBar>
      <!-- 主页面 -->
      <AppMain></AppMain>
    </div>
  </div>
</template>

<script lang="ts" setup>
import AppMain from './components/AppMain/index.vue';
import NavBar from './components/NavBar/index.vue';
import SideBar from './components/SideBar/index.vue';
import { useWindowSize } from '@vueuse/core';
import useStore from '@/store';
import { ElMessage } from 'element-plus';

const { appStore } = useStore();
const { width } = useWindowSize();
const WIDTH = 992;
// const settingRef = ref();

const device = computed(() => appStore.device);

// const needTagView = computed(() => setting.tagView);
// const fixedHeader = computed(() => setting.fixedHeader);
const classObj = computed(() => ({
  hideSidebar: !appStore.sidebar.opened,
  openSidebar: appStore.sidebar.opened,
  withoutAnimation: appStore.sidebar.withoutAnimation,
  mobile: device.value === 'mobile'
}));

watchEffect(() => {
  // if (device.value === 'mobile' && appStore.sidebar.opened) {
  //   appStore.closeSidebar(false);
  // }

  // 根据浏览器宽度判断是电脑还是手机
  if (width.value - 1 < WIDTH) {
    appStore.toggleDevice('mobile');
    appStore.closeSidebar(true);
  } else {
    appStore.toggleDevice('desktop');
  }
});

const handleClickOutside = () => {
  appStore.closeSidebar(false);
};

const setLayout = () => {
  ElMessage.info('setLayout');
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

//
//.fixed-header {
//  position: fixed;
//  top: 0;
//  right: 0;
//  z-index: 40;
//  width: calc(100% - #{$sideBarWidth});
//  transition: width 0.28s;
//}
//
//.hideSidebar .fixed-header {
//  width: calc(100% - 64px);
//}
//
//.sidebarHide .fixed-header {
//  width: 100%;
//}
//
//.mobile .fixed-header {
//  width: 100%;
//}
</style>
