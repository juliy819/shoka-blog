<!--
 * @description 首页说说轮播
 * @author juliy
 * @date 2023/4/4 21:04
-->
<template>
  <div class="talk-swiper">
    <svg-icon v-if="talkList.length > 0" icon-class="horn" size="1.25rem" />
    <n-skeleton v-else circle width="20px" />
    <swiper v-if="talkList.length > 0" class="swiper-container" direction="vertical" :modules="modules" :speed="2000"
            :loop="true" :slides-per-view="1" :autoplay="{ delay: 3000, disableOnInteraction: false, }">
      <swiper-slide v-for="(talk, index) in talkList" :key="index">
        <n-button text @click="toTalk(talk.id)">
          <div class="slide-content" v-html="talk.talkContent" />
        </n-button>
      </swiper-slide>
    </swiper>
    <n-skeleton v-else round width="90%" />
    <n-popover v-if="talkList.length > 0" trigger="hover">
      <template #trigger>
        <n-button text @click="toTalk">
          <svg-icon icon-class="right-arrow" class="arrow" />
        </n-button>
      </template>
      前往说说页面
    </n-popover>
    <n-skeleton v-else circle width="20px" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { Autoplay } from 'swiper';
import { Swiper, SwiperSlide } from 'swiper/vue';
import router from '@/router';
import type { TalkHome } from '@/api/talk/types';
import talkApi from '@/api/talk';
// 自动播放
const modules = [Autoplay];

const talkList = ref<TalkHome[]>([]);

const toTalk = (id?: number): void => {
  let path = '/talk';
  if (typeof id === 'number') {
    path += `/${id}`;
  }
  router.push({ path: path });
};

onMounted(() => {
  talkApi.getTalkHomeList().then(({ data }) => {
    talkList.value = data.data;
  }).catch(() => {
    // todo 删除
    setTimeout(() => talkList.value.push({ id: 1, talkContent: '测试1' }), 1000);

  });
});

</script>

<style scoped lang="scss">
@use "@/assets/styles/mixin";

.talk-swiper {
  @include mixin.card-shadow;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 0.5rem 0.5rem 0;
  padding: 0.6rem 1rem;
  font-size: 0.9375rem;
  border-radius: 0.5rem;
  transition: all 0.2s ease-in-out 0s;
}

.swiper-container {
  height: 1.5625rem;
  line-height: 1.5625rem;
}

.slide-content {
  text-align: center;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.arrow {
  animation: 1.5s passing infinite;
}

@keyframes passing {
  0% {
    transform: translateX(-50%);
    opacity: 0;
  }

  50% {
    transform: translateX(0);
    opacity: 1;
  }

  100% {
    transform: translateX(50%);
    opacity: 0;
  }
}
</style>