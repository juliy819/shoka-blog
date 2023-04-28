<!--
 * @description 目录
 * @author juliy
 * @date 2023/4/21 12:47
-->
<template>
  <div class="catalog-header">
    <svg-icon icon-class="category" />
    目录
  </div>
  <div class="catalog-content">
    <div class="catalog-item" v-for="(anchor, index) of titleList" :key="index"
         :class="currentIndex === index ? 'active' : ''" :style="{ paddingLeft: `${5 + anchor.indent * 15}px` }"
         @click="handleAnchorClick(anchor, index)">
      <a>{{ anchor.title }}</a>
    </div>
  </div>
</template>

<script setup lang="ts">

import { useScroll, watchThrottled } from '@vueuse/core';

const { y } = useScroll(window);
const titleList = ref<any>([]);
const currentIndex = ref(0);
const props = defineProps({
  domRef: {
    type: Object,
    default: null
  }
});

/**
 * 从文章dom节点解析出目录
 */
const getTitlesFromDom = () => {
  const anchors = props.domRef.$el.querySelectorAll('h1,h2,h3');
  const titles = Array.from(anchors).filter((t: any) => !!t.innerText.trim());
  if (!titles.length) {
    titleList.value = [];
  }
  const hTags = Array.from(new Set(titles.map((t: any) => t.tagName))).sort();
  titleList.value = titles.map((el: any, idx: number) => {
    return {
      title: el.innerText,
      lineIndex: el.getAttribute('data-v-md-line'),
      indent: hTags.indexOf(el.tagName)
    };
  });
};

const handleAnchorClick = (anchor: any, index: number) => {
  const heading = props.domRef.$el.querySelector(`[data-v-md-line="${anchor.lineIndex}"]`);
  if (heading) {
    window.scrollTo({
      behavior: 'smooth',
      top: heading.offsetTop - 20
    });
    // setTimeout(() => currentIndex.value = index, 400);
  }
};

onMounted(() => {
  nextTick(() => {
    // 这里不延时就无法加载，不清楚原因，已经用了nextTick了
    setTimeout(getTitlesFromDom, 500);
  });
});

// 监听滚动距离，与每个目录的lineIndex比较，判断当前位于哪个目录
watchThrottled(y, () => {
  titleList.value.forEach((e: any, index: number) => {
    const heading = props.domRef.$el.querySelector(`[data-v-md-line="${e.lineIndex}"]`);
    if (y.value >= heading.offsetTop - 30) {
      currentIndex.value = index;
    }
  });
}, { throttle: 100 });

</script>

<style scoped lang="scss">
.catalog-content {
  max-height: calc(100vh - 100px);
  overflow: auto;
  margin-right: -16px;
  padding-right: 16px;
}

.catalog-item {
  margin: 5px 0;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  font-size: 14px;
  padding: 2px 6px;
  overflow: hidden;
  text-overflow: ellipsis;

  &:hover {
    color: var(--primary-color);
  }
}

.active {
  background-color: var(--primary-color);
  color: var(--grey-0);
  font-weight: 700;

  &:hover {
    //background-color: var(--color-blue);
    color: var(--grey-0);
  }
}
</style>