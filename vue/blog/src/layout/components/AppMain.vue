<!--
 * @description 主页面
 * @author juliy
 * @date 2023/4/2 22:07
-->
<template>
  <main class="main-wrapper">
    <router-view v-slot="{ Component, route }">
      <keep-alive>
        <component :is="Component" :key="route.fullPath" />
      </keep-alive>
    </router-view>
  </main>
</template>

<script setup lang="ts">

</script>

<style scoped lang="scss">
.main-wrapper {
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 0 0 8rem;
}

</style>