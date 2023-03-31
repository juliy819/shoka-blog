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

<style lang="scss" scoped>
.scroll-container {
  white-space: nowrap;
  position: relative;
  overflow: hidden;
  width: 100%;

  :deep(.el-scrollbar__bar) {
    bottom: 0;
  }

  :deep(.el-scrollbar__wrap) {
    overflow-x: auto;
    height: calc(100% + 20px);
  }

  :deep(.el-scrollbar__wrap .el-scrollbar__view) {
    white-space: nowrap;
    display: inline-block;
  }
}
</style>
