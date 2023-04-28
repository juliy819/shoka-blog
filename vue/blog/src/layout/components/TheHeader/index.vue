<!--
 * @description 页面顶部
 * @author juliy
 * @date 2023/4/3 19:01
-->
<template>
  <header :class="fixedClass" class="header-wrapper">
    <hamburger />
    <!-- 这里不加这一层div会报警告 -->
    <div :class="{ sub: y > 0 }">
      <navbar />
    </div>
    <ul class="right">
      <li class="right-btn">
        <svg-icon :icon-class="isDark ? 'moon' : 'sun'" @click="toggle()" />
      </li>
      <li class="right-btn">
        <svg-icon icon-class="search" @click="" />
      </li>
    </ul>
  </header>
</template>

<script lang="ts" setup>
import Navbar from '@/layout/components/TheHeader/Navbar.vue';
import Hamburger from '@/layout/components/TheHeader/Hamburger.vue';
import { useDark, useScroll } from '@vueuse/core';
import { useToggle } from '@vueuse/shared';
import useStore from '@/stores';

const { appStore } = useStore();
const { y } = useScroll(window);
const isDark = useDark({
  selector: 'html',
  attribute: 'theme',
  valueDark: 'dark',
  valueLight: 'light'
});
const toggle = useToggle(isDark);
const show = ref(false);
const up = ref(true);

const fixedClass = computed(() => ({
  show: show.value,
  up: up.value,
  down: !up.value
}));

const maxChangeHeight = computed(() => appStore.headerChangeHeight);

// 监听滚动
// todo 这里有个bug，在触发show的边界处，向下滚动时，是先变色再收起，很突兀，主要是由于背景为渐变，无法设置transition
watch(y, (newValue, oldValue) => {
  show.value = newValue > maxChangeHeight.value;
  up.value = newValue < maxChangeHeight.value && newValue < oldValue;
});

</script>

<style lang="scss" scoped>
.header-wrapper {
  position: fixed;
  display: flex;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 3.125rem;
  padding: 0 1rem;
  text-shadow: 0 0.2rem 0.3rem rgb(0 0 0 / 50%);
  color: var(--header-text-color);
  transition: all 0.2s ease-in-out 0s;
  z-index: 9;
}

.show {
  background: var(--nav-bg);
  box-shadow: 0.1rem 0.1rem 0.2rem var(--grey-9-a1);
  text-shadow: 0 0 0.625rem var(--grey-9-a1);
  color: var(--text-color);
}

.up {
  transform: translateY(0);
}

.down {
  transform: translateY(-100%);
}

.right {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;

  .right-btn {
    padding: 0.625rem 0.5rem;
    cursor: pointer;
  }
}

@media (max-width: 991px) {
  .header-wrapper {
    padding: 0;
  }
}

</style>