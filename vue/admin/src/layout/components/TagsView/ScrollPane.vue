<!--
 * @description 标签页的滚动容器
 * @author JuLiy
 * @date 2023/3/21 15:59
-->
<template>
  <el-scrollbar class="scroll-container">
    <slot />
  </el-scrollbar>
</template>

<script lang="ts" setup></script>

<style lang="scss" scoped>
.scroll-container {
  white-space: nowrap;
  position: relative;
  overflow: hidden;
  width: 100%;
}
</style>
