<!--
 * @description 页脚
 * @author juliy
 * @date 2023/4/2 22:17
-->
<template>
  <footer class="footer-wrapper" v-if="!isMessage">
    <p>
      copyright ©
      {{ formatDate(blogStore.siteConfig.createSiteTime, 'YYYY') }}
      -
      {{ new Date().getFullYear() }}
      by
      <b>
        {{ blogStore.siteConfig.siteAuthor?.toUpperCase() }}
      </b>
    </p>

    <a class="underline-a" href="https://beian.miit.gov.cn/" target="_blank">
      {{ blogStore.siteConfig.recordNumber }}
    </a>
  </footer>
</template>

<script setup lang="ts">
import useStore from '@/stores';
import { formatDate } from '@/utils/date';

const { blogStore } = useStore();
const isMessage = computed(() => useRoute().name === 'message');


</script>

<style scoped lang="scss">
.footer-wrapper {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 8rem;
  padding: 2.5rem 0;
  font-size: 0.875rem;
  text-align: center;
  box-shadow: 0 0 10px var(--box-bg-shadow), 0 0 10px var(--box-bg-shadow);
  border-radius: 10px 10px 0 0;
  color: var(--grey-0);
  background: var(--footer-bg);
  background-size: 300% 300%;
  animation: gradientBG 10s ease infinite;
  -webkit-animation: gradientBG 10s ease infinite;
}

// 背景渐变动画
@keyframes gradientBG {
  0% {
    background-position: 0 50%;
  }

  50% {
    background-position: 100% 50%;
  }

  100% {
    background-position: 0 50%;
  }
}

</style>