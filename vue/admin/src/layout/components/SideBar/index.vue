<!--
 * @description 侧边栏
 * @author juliy
 * @date 2023-03-12 12:07
-->
<template>
  <div :class="{ 'has-logo': showLogo }">
    <!-- 网站Logo -->
    <logo v-if="showLogo" :collapse="!isOpened" />
    <!-- 侧边栏 -->
    <el-scrollbar>
      <el-menu
        :active-text-color="variables.menuActiveText"
        :background-color="variables.menuBg"
        :collapse="!isOpened"
        :collapse-transition="false"
        :default-active="activePath"
        :text-color="variables.menuText">
        <SidebarItem
          v-for="route1 in routes"
          :key="route1.path"
          :base-path="route1.path"
          :item="route1" />
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
const isOpened = computed(() => appStore.sidebar.opened);
const showLogo = computed(() => settingStore.sidebarLogo);
const routes = computed(() => permissionStore.routes);
const activePath = computed(() => route.path);
</script>

<style scoped></style>
