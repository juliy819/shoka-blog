<!--
 * @description 主页
 * @author juliy
 * @date 2023-03-01 22:48
-->
<template>
  <div>
    <header>
      <img alt="Vue logo" class="logo" height="125" src="@/assets/logo.svg" width="125" />
    </header>
    <el-button @click="test = !test">
      按钮
    </el-button>
    <transition mode="out-in" name="el-fade-in-linear">
      <el-button v-if="test">true</el-button>
      <el-button v-else>false</el-button>
    </transition>
    <router-link to="/index" tag="div">link</router-link>

  </div>
</template>

<script lang="ts" setup>

const test = ref(false);

</script>

<style scoped>
header {
    line-height: 1.5;
    max-height: 100vh;
}

.logo {
    display: block;
    margin: 0 auto 2rem;
}
</style>