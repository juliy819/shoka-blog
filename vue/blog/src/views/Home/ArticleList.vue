<!--
 * @description 首页文章列表
 * @author juliy
 * @date 2023/4/5 17:01
-->
<template>
  <div v-if="articleList.length > 0" v-animate="['fadeInUp']" class="article-item" v-for="article in articleList"
       :key="article.id">
    <router-link :to="`/article/${article.id}`" class="article-cover">
      <n-image class="cover" :src="article.articleCover" preview-disabled />
    </router-link>
    <div class="article-info">
      <div class="article-meta">
        <!-- 置顶 -->
        <span class="top" v-if="article.isTop === 1">
          <svg-icon icon-class="top" size="0.85rem" style="margin-right: 0.15rem" />
          置顶
        </span>
        <!-- 文章标签 -->
        <router-link class="article-tag" v-for="tag in article.tagVOList" :key="tag.id" :to="`/tag/${tag.id}`">
          <svg-icon icon-class="tag" size="0.9rem" style="margin-right: 0.15rem" />
          <n-ellipsis style="max-width: 4rem">{{ tag.tagName }}</n-ellipsis>
        </router-link>
      </div>
      <!-- 文章标题 -->
      <h3 class="article-title">
        <router-link :to="`/article/${article.id}`">
          {{ article.articleTitle }}
        </router-link>
      </h3>
      <!-- 文章内容 -->
      <div class="article-content">{{ article.articleContent }}</div>
      <div class="article-footer">
        <!-- 发表时间 -->
        <div class="create-time">
          <svg-icon icon-class="calendar" size="0.9rem" />
          <span>{{ formatDate(article.createTime) }} </span>
        </div>
        <!-- 文章分类 -->
        <router-link class="article-category" :to="`/category/${article.category.id}`">
          <svg-icon icon-class="qizhi" size="0.85rem" />
          <n-ellipsis style="max-width: 8rem">{{ article.category.categoryName }}</n-ellipsis>
        </router-link>
      </div>
      <!-- 阅读按钮 -->
      <router-link class="article-btn" :to="`/article/${article.id}`">more...</router-link>
    </div>
  </div>

  <!-- 骨架屏 -->
  <div v-else v-animate="['fadeInUp']" class="article-item" v-for="item in [1, 2, 3, 4, 5]" :key="item">
    <n-skeleton class="article-cover" />
    <div class="article-info">
      <!--      <div class="article-meta">-->
      <!--        <n-skeleton round width="3rem" height="1rem" style="margin-right: 1rem" />-->
      <!--        <n-skeleton round width="10rem" height="1rem" />-->
      <!--      </div>-->
      <n-skeleton class="article-title" width="12rem" height="2rem" round />
      <n-skeleton class="article-content" width="100%" round text :repeat="4" />
      <!--      <div class="article-footer">-->
      <!--        <n-skeleton class="create-time" round width="7rem" height="1.5rem" />-->
      <!--        <n-skeleton class="article-tag" round width="7rem" height="1.5rem" />-->
      <!--        <n-skeleton class="article-tag" round width="5rem" height="1.5rem" style="margin: 0 2rem 0 2rem" />-->
      <!--      </div>-->
    </div>
  </div>

  <pagination v-model:current="pageQuery.current" :total="Math.ceil(count / 5)" />
</template>

<script setup lang="ts">
import { ref } from 'vue';
import type { Article } from '@/api/article/types';
import type { PageQuery } from '@/model';
import articleApi from '@/api/article';
import { formatDate } from '@/utils/date';
import Pagination from '@/components/Pagination.vue';

const articleList = ref<Article[]>([]);
const pageQuery = ref<PageQuery>({ current: 1, size: 5 });
const count = ref(0);

watch(
  () => pageQuery.value.current,
  () => {
    articleApi.getArticleList(pageQuery.value).then(({ data }) => {
      articleList.value = data.data.recordList;
      count.value = data.data.count;
    }).catch(() => {
      articleList.value = [];
    });
  }
);

onMounted(() => {
  articleApi.getArticleList(pageQuery.value).then(({ data }) => {
    articleList.value = data.data.recordList;
    count.value = data.data.count;
  }).catch(() => {
    // todo 删除
    setTimeout(() => {
      articleList.value.push(
        {
          id: 1,
          isTop: 1,
          articleTitle: '测试文章1 123121231212312123123131331331313313',
          articleContent: '6547654765476547654656 12312 2312312312231231 231223 12312312231231231223123',
          articleCover: 'http://static.juliy.top/article/0934fbddd869380b36e1e01e335c7ac2.jpg',
          createTime: '2023-01-01',
          category: { id: 1, categoryName: '测试分类1231231231231231231231231231' },
          tagVOList: [{ id: 1, tagName: '测试标签1231231231231' }, { id: 2, tagName: '测试标签4375436543652' }, {
            id: 3,
            tagName: '测试标签3321213123123'
          }]
        },
        {
          id: 2,
          isTop: 0,
          articleTitle: '测试文章2 123121231212312123123131331331313313',
          articleContent: '测试文章2内容123121231212312123123131331331313313',
          articleCover: 'http://static.juliy.top/article/687f08589d9ca5c71210608a33b5b043.jpg',
          createTime: '2023-01-01',
          category: { id: 1, categoryName: '测试分类1123121231212312123123131331331313313' },
          tagVOList: [{ id: 1, tagName: '测试标签1123121231212312123123131331331313313' }]
        },
        {
          id: 3,
          isTop: 0,
          articleTitle: '测试文章3',
          articleContent: '测试文章3内容',
          articleCover: 'http://static.juliy.top/article/87d2f42092bc7340f29fe920a9398afd.jpg',
          createTime: '2023-01-01',
          category: { id: 1, categoryName: '测试分类1' },
          tagVOList: [{ id: 1, tagName: '测试标签1' }, { id: 2, tagName: '测试标签2' }]
        },
        {
          id: 4,
          isTop: 1,
          articleTitle: '测试文章4',
          articleContent: '测试文章4内容',
          articleCover: 'https://img.timelessq.com/images/2022/07/26/a11995a8254fd4a4038ba59f6bcf5a89.jpg',
          createTime: '2023-01-01',
          category: { id: 1, categoryName: '测试分类1' },
          tagVOList: []
        }
      );
      count.value = 21;
    }, 1000);
  });
});

</script>

<style scoped lang="scss">
@use '@/assets/styles/mixin';

.article-item {
  @include mixin.card-shadow;
  display: flex;
  height: 14rem;
  margin: 1.25rem 0.5rem 0;
  border-radius: 0.625rem;
  animation-duration: 0.5s;
  transition: all 0.2s ease-in-out 0s;
  overflow: hidden;

  &:hover .cover {
    transform: scale(1.05) rotate(1deg);
  }

  // 双数样式
  &:nth-child(even) {
    flex-direction: row-reverse;

    .article-cover {
      height: 100%;
      margin-right: auto;
      margin-left: 1.5rem;
      clip-path: polygon(0 0, 100% 0, 100% 100%, 8% 100%);
      border-radius: 0 0.625rem 0.625rem 0;
    }

    .article-info {
      padding: 1rem 0 3rem 1.5rem;

      .article-meta {
        justify-content: flex-start;
      }
    }

    .article-footer {
      right: 0;
      flex-direction: row-reverse;
    }

    .article-btn {
      left: 0;
      right: auto;
      border-radius: 0 1rem;
      background-image: linear-gradient(to right, var(--color-orange) 0, var(--color-pink) 100%);
    }
  }
}

.article-cover {
  width: 50%;
  height: 100%;
  margin-right: 1.5rem;
  clip-path: polygon(0 0, 92% 0, 100% 100%, 0 100%);
  border-radius: 0.625rem 0 0 0.625rem;

  .cover {
    height: 100%;
    width: 100%;
    transition: all 0.2s ease-in-out 0s;

    :deep(img) {
      width: 100%;
      height: 100%;
    }
  }
}

.article-info {
  position: relative;
  width: 50%;
  overflow: hidden;
  padding: 1rem 1.5rem 3rem 0;

  .article-meta {
    display: flex;
    justify-content: flex-end;
    font-size: 0.8rem;
    color: var(--grey-5);

    .top {
      color: var(--color-orange);
      min-width: 3.5rem;
    }

    .article-tag {
      display: flex;
      align-items: center;
      margin-left: 0.5rem;
    }
  }

  .article-title {
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    margin: 0.625rem 0;
    color: var(--primary-color);
  }

  .article-content {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
    max-height: 5rem;
    font-size: 0.875em;
    overflow: hidden;
  }
}

.article-footer {
  position: absolute;
  bottom: 0.5rem;
  font-size: 0.8125em;
  display: flex;
  color: var(--grey-5);

  .create-time, .article-category {
    margin: 0 0.5rem;
  }

  .svg-icon {
    margin-right: 0.15rem;
  }
}

.article-btn {
  position: absolute;
  bottom: 0;
  right: 0;
  padding: 0.3rem 1rem;
  border-radius: 1rem 0;
  color: var(--grey-0);
  background-image: linear-gradient(to right, var(--color-pink) 0, var(--color-orange) 100%);
  transition: scale 0.2s ease-in-out;

  &:hover {
    scale: 1.15 1.15;
    color: var(--grey-4);
  }
}

a {
  transition: color 0.2s ease-in-out;

  &:hover {
    color: var(--primary-color-darker);
  }
}

@media (max-width: 767px) {
  .article-item {
    flex-direction: column;
    height: fit-content;

    .article-cover {
      width: 100%;
      height: 10rem;
      margin: auto;
      clip-path: polygon(0 0, 100% 0, 100% 92%, 0 100%);
      border-radius: 0.625rem 0.625rem 0 0;

      .cover {
        width: 100%;
      }
    }

    .article-info {
      width: 100%;
      height: 14rem;
      padding: 0 1rem 3rem;
    }

    &:nth-child(even) {
      flex-direction: column;

      .article-cover {
        width: 100%;
        height: 10rem;
        margin: auto;
        clip-path: polygon(0 0, 100% 0, 100% 100%, 0 92%);
        border-radius: 0.625rem 0.625rem 0 0;
      }

      .article-info {
        padding: 0 1rem 3rem;
      }
    }

  }

}

</style>