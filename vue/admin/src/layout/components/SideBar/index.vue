<!--
 * @description 侧边栏
 * @author juliy
 * @date 2023-03-12 12:07
-->
<template>
  <div :class="{ 'has-logo': showLogo }"
       :style="{ backgroundColor: sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground }">
    <!-- 网站Logo -->
    <logo v-if="showLogo" :collapse="!isOpened"/>
    <!-- 侧边栏 -->
    <el-scrollbar :class="sideTheme" wrap-class="scrollbar-wrapper">
      <el-menu
          :active-text-color="variables.menuColorActive"
          :background-color="sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground"
          :collapse="!isOpened"
          :collapse-transition="false"
          :default-active="activePath"
          :text-color="sideTheme === 'theme-dark' ? variables.menuColor : variables.menuLightColor">
        <SidebarItem
            v-for="route1 in routes"
            :key="route1.path"
            :base-path="route1.path"
            :item="route1"/>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script lang="ts" setup>
import useStore from '@/store';
import variables from '@/assets/styles/variables.module.scss';
import Logo from '@/layout/components/SideBar/Logo.vue';
import SidebarItem from '@/layout/components/SideBar/SidebarItem.vue';

const { appStore, settingStore, permissionStore } = useStore();
const route = useRoute();
const sideTheme = computed(() => settingStore.sideTheme);
const isOpened = computed(() => appStore.sidebar.opened);
const showLogo = computed(() => settingStore.sidebarLogo);
const routes = computed(() => permissionStore.routes);
const activePath = computed(() => route.path);
</script>

<style scoped></style>