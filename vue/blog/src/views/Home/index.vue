<!--
 * @description 首页
 * @author juliy
 * @date 2023/4/1 16:02
-->
<template>
  <div class="imgs">
    <img class="item" src="@/assets/images/bg.jpg" alt="123" />
  </div>

</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.imgs {
  //position: fixed;
  //top: 0;
  //left: 0;
  width: 100%;
  height: 500vh;
  z-index: -9;
  background-color: #363636;
  overflow: hidden;

  .item {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    //background: no-repeat 50% 50% / cover;
    //opacity: 0;
  }
}

</style>