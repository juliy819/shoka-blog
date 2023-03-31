<!--
 * @description 侧边栏
 * @author juliy
 * @date 2023-03-12 12:07
-->
<template>
  <div :class="{ 'has-logo': showLogo }"
       :style="{ backgroundColor: sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground }">
    <!-- 网站Logo -->
    <logo v-if="showLogo" :collapse="isCollapse" />
    <!-- 侧边栏 -->
    <el-scrollbar :class="sideTheme" wrap-class="scrollbar-wrapper">
      <el-menu :active-text-color="theme"
               :background-color="sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground"
               :collapse="isCollapse" :collapse-transition="false" :default-active="activePath"
               :text-color="sideTheme === 'theme-dark' ? variables.menuColor : variables.menuLightColor"
               :unique-opened="true" mode="vertical">
        <sidebar-item v-for="(route, index) in sidebarRoutes" :key="route.path + index" :base-path="route.path"
                      :item="route" />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script lang="ts" setup>
import useStore from '@/store';
import variables from '@/assets/styles/variables.module.scss';
import Logo from '@/layout/components/Sidebar/Logo.vue';
import SidebarItem from '@/layout/components/Sidebar/SidebarItem.vue';
import type { RouteRecordRaw } from 'vue-router';

const { appStore, settingStore, permissionStore } = useStore();
const route = useRoute();
const sideTheme = computed(() => settingStore.sideTheme);
const theme = computed(() => settingStore.theme);
const isCollapse = computed(() => !appStore.sidebar.opened);
const showLogo = computed(() => settingStore.sidebarLogo);
const sidebarRoutes = computed(() => permissionStore.routes as RouteRecordRaw[]);
const activePath = computed(() => route.path);
</script>

<style scoped></style>
