<!--
 * @description 首页
 * @author juliy
 * @date 2023/4/1 16:02
-->
<template>
  <image-carousel />
  <div class="page-header">
    <brand />
    <waves />
    <!-- 向下按钮 -->
    <svg-icon class="arrow-down" icon-class="down" size="32px" @click="scrollDown" />
  </div>

  <n-card>
    <n-button>123</n-button>
  </n-card>

</template>

<script setup lang="ts">
import ImageCarousel from '@/views/Home/ImageCarousel.vue';
import Brand from '@/views/Home/Brand.vue';

const scrollDown = () => {
  console.log(123);
  window.scrollTo({
    behavior: 'smooth',
    top: document.documentElement.clientHeight
  });
};

</script>

<style scoped lang="scss">
.page-header {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  position: relative;
  width: 100%;
  height: 100vh;
  min-height: 10rem;
  color: var(--header-text-color);

  .arrow-down {
    position: absolute;
    bottom: 100px;
    cursor: pointer;
    z-index: 8;
    animation: arrowShake 1.5s ease-out infinite;
    -webkit-animation: arrowShake 1.5s ease-out infinite;
  }
}

// 箭头上下跳动
@keyframes arrowShake {
  0% {
    opacity: 1;
    transform: translateY(0);
  }

  30% {
    opacity: .5;
    transform: translateY(25px);
  }

  100% {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>