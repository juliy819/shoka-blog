<!--
 * @description 首页侧边栏网页信息
 * @author juliy
 * @date 2023/4/5 17:06
-->
<template>
  <div class="side-card">
    <div class="card-title">
      <svg-icon icon-class="web" size="1.1875rem" />
      网站信息
    </div>
    <div class="web-item">
      <div class="web-name">运行时长</div>
      <div class="web-count">{{ runTime }}</div>
    </div>
    <div class="web-item">
      <div class="web-name">总访问量</div>
      <div class="web-count">{{ blogStore.viewCount }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">

import useStore from '@/stores';
import dayjs from 'dayjs';

const { blogStore } = useStore();
const runTime = ref('');
setInterval(() => {
  const days = dayjs().diff(blogStore.siteConfig.createSiteTime, 'days');
  const now = new Date();
  let str = '';
  str += days + '天';
  str += now.getHours() + '时';
  str += now.getMinutes() + '分';
  str += now.getSeconds() + '秒';
  runTime.value = str;
}, 1000);

</script>

<style scoped lang="scss">
.web-item {
  display: flex;
  justify-content: space-between;
}
</style>