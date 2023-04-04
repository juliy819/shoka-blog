<!--
 * @description 首页图片轮播
 * @author juliy
 * @date 2023/4/1 16:02
-->
<template>
  <div class="imgs">
    <ul>
      <li class="item" v-for="(item, index) in imageList" :key="index"
          :style="{ 'background-image': 'url(' + item + ')' }" />
    </ul>
  </div>
</template>

<script setup lang="ts">
const imageList = [
  'https://img.timelessq.com/images/2022/07/26/1fca97d4bee5434e2dec3c0a4a75bc64.jpg',
  'https://img.timelessq.com/images/2022/07/26/a11995a8254fd4a4038ba59f6bcf5a89.jpg',
  'https://img.timelessq.com/images/2022/07/26/626f157a4ff74c7984f4110e38031524.jpg',
  'https://img.timelessq.com/images/2022/07/26/545e6f6f8db4a66e412bf2c5213d3d10.jpg',
  'https://img.timelessq.com/images/2022/07/26/a645009f08917c96e3c883e675f5aa58.jpg',
  'https://img.timelessq.com/images/2022/07/26/f99aff0036302f62ec222c046d3e953b.jpg'
];

</script>

<style scoped lang="scss">
.imgs {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  z-index: -9;
  background-color: #363636;
  overflow: hidden;

  .item {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: no-repeat 50% 50% / cover;
    opacity: 0;
    animation: imageAnimation 36s linear infinite 0s;
    backface-visibility: hidden;
    transform-style: preserve-3d;

    &:nth-child(2) {
      animation-delay: 6s;
    }

    &:nth-child(3) {
      animation-delay: 12s;
    }

    &:nth-child(4) {
      animation-delay: 18s;
    }

    &:nth-child(5) {
      animation-delay: 24s;
    }

    &:nth-child(6) {
      animation-delay: 30s;
    }

    // 变暗的遮罩层
    &::before {
      content: '';
      display: block;
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, .2);
      transition: all .2s ease-in-out 0s;
    }
  }
}

// 轮播动画
@keyframes imageAnimation {
  0% {
    opacity: 0;
    animation-timing-function: ease-in;
  }

  2% {
    opacity: 1;
  }

  8% {
    opacity: 1;
    transform: scale(1.05);
    animation-timing-function: ease-out;
  }

  17% {
    opacity: 1;
    transform: scale(1.1);
  }

  25% {
    opacity: 0;
    transform: scale(1.1);
  }

  100% {
    opacity: 0;
  }
}

</style>