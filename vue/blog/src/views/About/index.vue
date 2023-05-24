<!--
 * @description 关于页面
 * @author juliy
 * @date 2023/4/1 16:04
-->
<template>
  <div class="page-header">
    <h1 class="page-title">关于</h1>
    <img class="page-cover" src="http://static.juliy.top/site-imgs/def-bg.png" alt="" />
    <waves />
  </div>
  <div class="bg">
    <div class="page-container">
      <div class="author-info">
        <n-avatar class="author-avatar" round :size="150" :src="blogStore.siteConfig.authorAvatar" />
        <p class="author-name">{{ blogStore.siteConfig.siteAuthor }}</p>
      </div>
      <v-md-preview class="md" :text="blogStore.siteConfig.aboutMe" />
    </div>
  </div>
</template>

<script setup lang="ts">
import useStore from '@/stores';

const { blogStore } = useStore();
</script>

<style scoped></style>
