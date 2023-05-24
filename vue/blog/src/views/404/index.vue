<!--
 * @description 404页面
 * @author juliy
 * @date 2023/4/1 16:04
-->
<template>
  <div class="page-header">
    <h1 class="page-title">Σ( ° △ °|||)︴404！何も見えない</h1>
    <img class="page-cover" src="http://static.juliy.top/site-imgs/def-bg.png" alt="">
    <waves />
  </div>
  <div class="bg">
    <div class="main-container">
      <div class="left-container">
        <div class="notfound">Σ( ° △ °|||)︴404！何も見えない</div>
      </div>
      <div class="right-container">
        <author class="side-card" />
        <web-info />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.notfound {
  height: 22.625rem;
  background: url("@/assets/images/404.png") no-repeat bottom;
  text-align: center;
  margin: 5rem auto;
}
</style>