<!--
 * @description 首页侧边栏最近评论
 * @author juliy
 * @date 2023/4/5 17:05
-->
<template>
  <div class="side-card">
    <div class="card-title">
      <svg-icon icon-class="comment" size="1.1875rem" />
      最新评论
    </div>
    <div v-if="commentList.length > 0">
      <div class="comment-item" v-for="comment in commentList" :key="comment.id">
        <n-avatar circle :size="55" :src="comment.avatar" alt="" />
        <div class="comment-content">
          <div class="info">
            <div class="nickname">{{ comment.nickname }}</div>
            <div>{{ formatDate(comment.createTime) }}</div>
          </div>
          <n-ellipsis class="content" :line-clamp="2">
            {{ comment.commentContent }}
          </n-ellipsis>
        </div>
      </div>
    </div>

    <div v-else>
      <div v-for="item in [1, 2, 3, 4, 5]" :key="item">
        <div class="comment-item">
          <n-skeleton circle width="55px" />
          <div class="comment-content">
            <div class="info">
              <n-skeleton class="mb5" text width="5rem" height="0.8rem" />
              <n-skeleton class="mb8" width="2rem" />
            </div>
            <n-skeleton class="content" />
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import type { RecentComment } from '@/api/comment/types';
import commentApi from '@/api/comment';
import { formatDate } from '@/utils/date';

const commentList = ref<RecentComment[]>([]);

onMounted(() => {
  commentApi.getRecentComments().then(({ data }) => {
    commentList.value = data.data;
  }).catch(() => {});
});

</script>

<style scoped lang="scss">
@use '@/assets/styles/mixin';

.comment-item {
  @include mixin.card-shadow;
  display: flex;
  align-items: center;
  padding: 0.375rem 0 0.375rem 0.25rem;
  transition: all 0.2s ease-in-out;
  border-radius: 0.5rem;
  margin-top: 0.25rem;

  &:hover {
    .nickname {
      color: var(--primary-color);
      transition: color 0.3s ease-in-out;
    }
  }
}

.comment-content {
  width: calc(100% - 4.2rem);
  padding-left: 0.625rem;

  .info {
    font-size: 0.5rem;
    line-height: 1rem;
    color: var(--grey-6);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  :deep(.content) {
    line-height: 1.5;
  }
}
</style>