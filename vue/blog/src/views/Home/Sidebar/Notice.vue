<!--
 * @description 首页侧边栏公告
 * @author juliy
 * @date 2023/4/5 17:05
-->
<template>
  <div class="side-card">
    <div class="card-title">
      <svg-icon class="trumpet" icon-class="trumpet" size="1.1875rem" color="red" />
      公告
    </div>
    <div v-if="blogStore.siteConfig.id" class="notice-content" v-html="blogStore.siteConfig.siteNotice" />
    <n-skeleton v-else text round repeat="3" />
  </div>
</template>

<script setup lang="ts">

import useStore from '@/stores';

const { blogStore } = useStore();
</script>

<style scoped lang="scss">
.notice-content {
  font-size: 0.9rem;
  line-height: 1.75rem;
}
</style>