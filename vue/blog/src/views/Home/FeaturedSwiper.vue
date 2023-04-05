<!--
 * @description 推荐文章
 * @author juliy
 * @date 2023/4/4 23:27
-->
<template>
  <swiper v-if="featuredList.length > 0" :autoplay="{ delay: 5000, disableOnInteraction: false, }" :loop="true"
          :modules="modules" :pagination="{ clickable: true }" :slides-per-view="1" class="swiper-container" mousewheel
          navigation>
    <swiper-slide v-for="article in featuredList" :key="article.id">
      <div :style="articleCover(article.articleCover)" class="slide-content">
        <router-link :to="`/article/${article.id}`" class="slide-title">
          {{ article.articleTitle }}
        </router-link>
        <span class="slide-time">发布时间:{{ formatDate(article.createTime) }}</span>
      </div>
    </swiper-slide>
  </swiper>
  <!-- 骨架屏 -->
  <div v-else class="swiper-container bg-gray">
    <div class="slide-content">
      <n-skeleton round class="slide-title" width="200px" />
      <n-skeleton round class="slide-time" width="200px" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { Autoplay, Mousewheel, Navigation, Pagination } from 'swiper';
import { Swiper, SwiperSlide } from 'swiper/vue';
import type { ArticleFeatured } from '@/api/article/types';
import { ref } from 'vue';
import articleApi from '@/api/article';
import { formatDate } from '@/utils/date';

const modules = [Pagination, Navigation, Mousewheel, Autoplay];
const featuredList = ref<ArticleFeatured[]>([]);

const articleCover = computed(() => (cover: string) => 'background:url(' + cover + ')');

onMounted(() => {
  articleApi.getArticleFeatured().then(({ data }) => {
    featuredList.value = data.data;
  }).catch(() => {
    // todo 删除
    setTimeout(() => {
      featuredList.value.push(
        {
          id: 1,
          createTime: '2023:01:01',
          articleTitle: '测试1',
          articleCover: 'https://img.timelessq.com/images/2022/07/26/a11995a8254fd4a4038ba59f6bcf5a89.jpg'
        },
        {
          id: 2,
          createTime: '2023:01:02',
          articleTitle: '测试2',
          articleCover: 'https://img.timelessq.com/images/2022/07/26/626f157a4ff74c7984f4110e38031524.jpg'
        }
      );
    }, 1000);
  });
});
</script>

<style lang="scss" scoped>
@use '@/assets/styles/mixin';

.swiper-container {
  height: 14rem;
  margin: 1rem 0.5rem;
  border-radius: 0.75rem;

  // 左上角推荐标记
  &::before {
    content: '推荐';
    position: absolute;
    left: 0.625rem;
    top: -2rem;
    z-index: 2;
    color: var(--grey-0);
    background: linear-gradient(90deg, var(--color-yellow), var(--color-orange));
    letter-spacing: 0.2rem;
    font-size: 1rem;
    width: 4rem;
    text-align: center;
    display: flex;
    justify-content: center;
    border-radius: 0 0 0.75rem 0.75rem;
    transition: transform 0.3s ease-in-out;
  }

  &:hover::before {
    transform: translateY(2rem);
  }
}

.slide-content {
  @include mixin.flex;
  flex-direction: column;
  //width: 100%;
  height: 100%;
  padding: 0 3rem 1rem;
  background-position: center !important;
  background-size: cover !important;

  .slide-title {
    font-size: 2.5rem;
    font-weight: 800;
  }

  .slide-title,
  .slide-time {
    text-align: center;
    line-height: 1.5;
    margin: 0.125rem 0;
    color: #fff;
    z-index: 1;
  }

  &::after {

  }
}

.swiper-slide .slide-content::after {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.1);
  left: 0;
  top: 0;
}

// 底部分页效果
:deep(.swiper-pagination) {
  .swiper-pagination-bullet {
    display: inline-block;
    width: 0.6875rem;
    height: 0.6875rem;
    margin: 0 0.25rem;
    border-radius: 6.1875rem;
    background: var(--grey-0);
    opacity: 0.8;
    transition: all 0.3s;

    &.swiper-pagination-bullet-active {
      opacity: 1;
      background-color: var(--primary-color);
      width: 1.875rem;
    }
  }
}

:deep(.swiper-button-next),
:deep(.swiper-button-prev) {
  width: 2.75rem;
  color: var(--primary-color);
  transition: all 0.3s ease-in-out;

  &:after {
    font-size: 1.5rem !important;
  }

  &:hover {
    background: rgba(255, 255, 255, .4);
    border-radius: 100%;
  }
}

</style>