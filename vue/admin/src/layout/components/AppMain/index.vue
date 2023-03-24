<template>
  <section class="app-main">
    <router-view v-slot="{ Component, route }">
      <transition mode="out-in" name="fade-transform">
        <keep-alive>
          <component :is="Component" :key="route.fullPath" />
        </keep-alive>
      </transition>
    </router-view>
  </section>
</template>

<script lang="ts" setup></script>

<style lang="scss" scoped>
.app-main {
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}

.fixed-header + .app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    min-height: calc(100vh - 84px);
  }

  .fixed-header + .app-main {
    padding-top: 84px;
  }
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
//.el-popup-parent--hidden {
//  .fixed-header {
//    padding-right: 100px;
//  }
//}
</style>
