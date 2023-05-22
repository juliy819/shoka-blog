<!--
 * @description 标签页面
 * @author juliy
 * @date 2023/4/1 16:04
-->
<template>
  <div class="page-header">
    <h1 class="page-title">标签</h1>
    <img class="page-cover" src="http://static.juliy.top/site-imgs/def-bg.png" alt="">
    <waves />
  </div>
  <div class="bg">
    <div class="page-container">
      <load-viewer :status="status" no-data-msg="暂时还没有标签哦~" failed-msg="标签加载失败">
        <template #data>
          <div class="tag-cloud">
            <router-link :to="`/tag/${tag.id}`" class="tag-item" v-for="tag in tagList" :key="tag.id"
                         :style="{ 'font-size': getSize(tag.articleCount), 'color': getRandomColor() }">
              {{ tag.tagName }}
              <sup>{{ tag.articleCount }}</sup>
            </router-link>
          </div>
        </template>
        <template #loading>
          <n-skeleton text round :repeat="5" />
        </template>
      </load-viewer>
    </div>
  </div>
</template>

<script setup lang="ts">
import tagApi from '@/api/tag';
import type { Tag } from '@/api/tag/types';
import { ref } from 'vue';

const status = ref<number>(0);
const tagList = ref<Tag[]>([]);

const getSize = (freq: number) => {
  return 18 + freq * 3 + 'px';
};
const getRandomColor = () => {
  return 'rgb(' + Math.round(Math.random() * 255) + ',' + Math.round(Math.random() * 255) + ',' + Math.round(Math.random() * 255) + ')';
};

onMounted(() => {
  tagApi.getTagList().then(({ data }) => {
    tagList.value = data.data;
    if (tagList.value.length > 0) {
      status.value = 1;
    } else {
      status.value = 2;
    }
  }).catch(() => {status.value = -1;});
});
</script>

<style lang="scss" scoped>
.tag-cloud {
  text-align: center;
}

.tag-item {
  display: inline-block;
  padding: 0 0.5rem;
  transition: all 0.3s;

  &:hover {
    transform: scale(1.1);
  }
}
</style>