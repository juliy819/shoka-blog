<!--
 * @description 侧边栏
 * @author juliy
 * @date 2023/4/3 19:58
-->
<template>
  <n-drawer v-model:show="appStore.sideDrawerOpen"></n-drawer>
</template>

<script setup lang="ts">
import useStore from '@/stores';

const { appStore } = useStore();

</script>

<style scoped lang="scss">

</style>