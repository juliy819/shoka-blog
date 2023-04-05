<!--
 * @description 首页侧边栏
 * @author juliy
 * @date 2023/4/5 17:02
-->
<template>
  <div>
    <author />
    <notice />
    <recent-comment />
    <web-info />
  </div>
</template>

<script setup lang="ts">
import Author from '@/views/Home/Sidebar/Author.vue';
import Notice from '@/views/Home/Sidebar/Notice.vue';
import RecentComment from '@/views/Home/Sidebar/RecentComment.vue';
import WebInfo from '@/views/Home/Sidebar/WebInfo.vue';
</script>