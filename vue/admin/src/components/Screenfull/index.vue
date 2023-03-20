<!--
 * @description 网页全屏
 * @author juliy
 * @date 2023/3/20 21:15
-->
<template>
  <div>
    <svg-icon size="1.1rem"
              :icon-class="isFullscreen ? 'exit-fullscreen' : 'fullscreen'"
              @click="toggle" />
  </div>
</template>

<script setup lang="ts">
import { useFullscreen } from '@vueuse/core';

const { isFullscreen, toggle } = useFullscreen();
</script>

<style scoped lang="scss">

</style>