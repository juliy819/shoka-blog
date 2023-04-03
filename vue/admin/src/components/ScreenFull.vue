<!--
 * @description 网页全屏
 * @author juliy
 * @date 2023/3/20 21:15
-->
<template>
  <div>
    <svg-icon :icon-class="isFullscreen ? 'exit-fullscreen' : 'fullscreen'" size="1.1rem" @click="toggle" />
  </div>
</template>

<script lang="ts" setup>
import { useFullscreen } from '@vueuse/core';

const { isFullscreen, toggle } = useFullscreen();
</script>

<style lang="scss" scoped></style>