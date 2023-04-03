<!--
 * @description 侧边栏
 * @author juliy
 * @date 2023/4/3 19:58
-->
<template>
  <el-drawer v-model="appStore.sideDrawerOpen"></el-drawer>
</template>

<script setup lang="ts">
import useStore from '@/stores';

const { appStore } = useStore();

</script>

<style scoped lang="scss">

</style>