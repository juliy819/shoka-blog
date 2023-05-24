<!--
 * @description 分类对应的文章列表
 * @author juliy
 * @date 2023/4/1 16:33
-->
<template>
  <div class="page-header">
    <h1 class="page-title">{{ name }}</h1>
    <img class="page-cover" src="http://static.juliy.top/site-imgs/def-bg.png" alt="">
    <waves />
  </div>
  <div class="bg">
    <div class="page-container">
      <load-viewer :status="status" no-data-msg="该分类下暂时还没有文章哦~" failed-msg="文章列表加载失败">
        <template #data>
          <n-grid x-gap="20" y-gap="20" cols="1 s:2 m:3" responsive="screen">
            <n-grid-item class="article-item" v-for="article of articleList" :key="article.id">
              <div class="article-cover">
                <router-link :to="`/article/${article.id}`">
                  <my-image :src="article.articleCover" />
                </router-link>
              </div>
              <div class="article-info">
                <div class="article-title">
                  <router-link :to="`/article/${article.id}`">{{ article.articleTitle }}</router-link>
                </div>
                <div class="article-time">
              <span>
                <svg-icon icon-class="calendar" size="0.95rem" />
                {{ formatDate(article.createTime) }}
              </span>
                </div>
                <div class="tag-info">
                  <router-link :to="`/tag/${tag.id}`" class="article-tag-category" v-for="tag in article.tagList"
                               :key="tag.id">
                    <svg-icon icon-class="tag" size="0.8rem"></svg-icon>
                    {{ tag.tagName }}
                  </router-link>
                </div>
              </div>
            </n-grid-item>
          </n-grid>
        </template>
        <template #loading>
          <n-grid x-gap="20" y-gap="20" cols="1 s:2 m:3" responsive="screen">
            <n-grid-item class="article-item" v-for="index of [1,2,3,4,5,6]" :key="index">
              <n-skeleton class="article-cover" />
              <div class="article-info">
                <n-skeleton round class="article-title" style="width: 12rem;" />
                <n-skeleton round class="article-time mb10" style="width: 6rem;" />
                <n-space justify="start">
                  <n-skeleton round style="width: 4rem;" />
                  <n-skeleton round style="width: 4rem;" />
                  <n-skeleton round style="width: 4rem;" />
                </n-space>
              </div>
            </n-grid-item>
          </n-grid>
        </template>
      </load-viewer>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import type { ArticleCondition, ArticleQuery } from '@/api/article/types';
import categoryApi from '@/api/category';
import { formatDate } from '@/utils/date';
import MyImage from '@/components/MyImage.vue';

const route = useRoute();
const status = ref<number>(0);
const name = ref('');
const articleList = ref<ArticleCondition[]>([]);
const articleQuery = ref<ArticleQuery>({
  current: 1,
  size: 5,
  categoryId: Number(route.params.id),
  tagId: 0
});

onMounted(() => {
  categoryApi.getCategoryArticleList(articleQuery.value).then(({ data }) => {
    articleList.value = data.data.articleConditionList;
    name.value = data.data.name;
    if (articleList.value.length > 0) {
      status.value = 1;
    } else {
      status.value = 2;
    }
  }).catch(() => {status.value = -1;});
});
</script>

<style lang="scss" scoped>
@use '@/assets/styles/mixin';

.article-item {
  display: flex;
  flex-direction: column;
  box-shadow: 0 0.625rem 1.875rem -0.9375rem var(--box-bg-shadow);
  transition: all 0.2s ease-in-out 0s;
  animation: zoomIn 1s both;

  &:hover {
    box-shadow: 0 0 2rem var(--box-bg-shadow);
  }
}

.article-cover {
  width: 100%;
  height: 12rem;
  overflow: hidden;
  border-radius: 0.625rem 0.625rem 0 0;
}

.article-item:hover .cover {
  transform: scale(1.1);
}

.article-info {
  padding: 0.6rem 0.8rem 1rem;
}

.article-title {
  font-size: 1.25rem;
  font-weight: 400;
  line-height: 1.25;

  a {
    color: var(--grey-7);
    transition: color 0.2s ease-in-out 0s;

    &:hover {
      color: var(--primary-color);
    }
  }
}

.article-time {
  margin-top: 0.625rem;
  font-size: 0.875rem;
  color: var(--grey-5);
}
</style>