<!--
 * @description 评论组件
 * @author juliy
 * @date 2023/5/12 14:18
-->
<template>
  <div id="comment-container">
    <div class="comment-title">
      <svg-icon icon-class="comment" size="1.4rem" class="mr5" />
      评论
    </div>
    <reply-box @reload="reloadComments" :comment-type="commentType" :type-id="typeId" />
    <div v-if="count > 0 && refresh">
      <div class="comment-item" v-for="(comment, index) of commentList" :key="comment.id">
        <div class="comment-avatar">
          <n-avatar lazy :intersection-observer-options="{root: '#comment-container'}" size="large" round
                    :src="comment.avatar" />
        </div>
        <div class="comment-main">
          <div class="user-info">
            <div class="nickname">
              <n-ellipsis :style="{'max-width': maxEllipsisWidth}">
                {{ comment.fromNickname }}
                <template #tooltip>
                  <div :style="{'max-width': maxEllipsisWidth}">
                    {{ comment.fromNickname }}
                  </div>
                </template>
              </n-ellipsis>
            </div>
            <svg-icon v-if="comment.fromUid === 1" icon-class="badge" />
          </div>
          <div class="comment-content" v-html="comment.commentContent" />
          <div class="comment-info">
            <span class="comment-time">{{ formatDateTime(comment.createTime) }}</span>
            <span class="comment-like" @click="like(comment)">
              <svg-icon class="like mr5" icon-class="like" size="0.8rem" :class="isLike(comment.id)" />
              <span v-show="comment.likeCount">{{ comment.likeCount }}</span>
            </span>
            <n-button class="reply-btn" @click="commentOrReply(index, comment)" text>回复</n-button>
          </div>
          <div class="reply-item" v-for="reply of comment.replyList" :key="reply.id">
            <div class="reply-user-info">
              <n-avatar class="reply-avatar" lazy :intersection-observer-options="{root: '#comment-container'}"
                        size="small" round :src="comment.avatar" />
              <div class="reply-nickname">{{ reply.fromNickname }}</div>
              <svg-icon v-if="reply.fromUid == 1" class="ml5" icon-class="badge" />
            </div>
            <span class="comment-content">
              回复 <span style="color: #008ac5">@ {{ reply.toNickname }}</span> :
              <span v-html="reply.commentContent" />
            </span>
            <div class="comment-info">
              <span class="comment-time">{{ formatDateTime(reply.createTime) }}</span>
              <span class="comment-like" @click="like(reply)">
                <svg-icon class="like mr5" icon-class="like" size="0.8rem" :class="isLike(reply.id)" />
                <span v-show="reply.likeCount > 0">{{ reply.likeCount }}</span>
              </span>
              <n-button class="reply-btn" @click="commentOrReply(index, comment)" text>回复</n-button>
            </div>
          </div>
          <div ref="readMoreRef" class="view-more" v-show="comment.replyCount > 3">
            <span>共{{ comment.replyCount }}条回复, </span>
            <n-button text class="view-more-btn" @click="getMoreReplies(index, comment)">点击查看</n-button>
          </div>
          <paging ref="pageRef" :total="comment.replyCount" :index="index" :commentId="comment.id"
                  @get-current-page="getCurrentPage" />
          <reply-box ref="replyRef" class="mt-4" :show="false" :comment-type="commentType" :type-id="typeId"
                     @reload="reloadReplies(index)" />
          <div class="bottom-line"></div>
        </div>
      </div>
      <div class="loading-wrap" v-if="count > commentList.length">
        <n-button class="loading-btn" color="#e9546b" @click="getList">
          加载更多...
        </n-button>
      </div>
    </div>
    <div v-else style="padding: 1.25rem; text-align: center; color: var(--grey-5)">
      暂时还没有评论，来发表第一个评论吧~
    </div>
  </div>
</template>

<script setup lang="ts">
import commentApi from '@/api/comment';
import type { Comment, CommentQuery, Reply } from '@/api/comment/types';
import useStore from '@/stores';
import { formatDateTime } from '@/utils/date';
import { modal } from '@/utils/modal';
import { ref } from 'vue';
import Paging from '@/components/Paging.vue';

const { userStore, appStore } = useStore();
const replyRef = ref<any>([]);
const pageRef = ref<any>([]);
const readMoreRef = ref<any>([]);
const props = defineProps({
  commentType: {
    type: Number
  }
});
const emit = defineEmits(['getCommentCount']);

const screenWidth = ref(window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth);
const maxEllipsisWidth = ref('30rem');
const typeId = computed(() => Number(useRoute().params.id) ? Number(useRoute().params.id) : undefined);
const isLike = computed(() => (id: number) => userStore.commentLikeSet.indexOf(id) !== -1 ? 'like-flag' : '');
const commentList = ref<Comment[]>([]);
const refresh = ref(true);
const count = ref(0);
const commentQuery = ref<CommentQuery>({
  current: 1,
  size: 5,
  commentType: props.commentType as number,
  typeId: typeId.value
});

/**
 * 点赞评论
 * @param comment 评论
 */
const like = (comment: Comment | Reply): void => {
  if (!userStore.id) {
    modal.msgError('请先登录！');
    return;
  }
  let id = comment.id;
  commentApi.likeComment(id).then(() => {
    if (userStore.commentLikeSet.indexOf(id) !== -1) {
      comment.likeCount -= 1;
    } else {
      comment.likeCount += 1;
    }
    userStore.commentLike(id);
  }).catch(() => {});
};

/**
 * 查看更多回复
 * @param index
 * @param comment
 */
const getMoreReplies = (index: number, comment: Comment): void => {
  commentApi.getReplyList(comment.id, { current: 1, size: 5 }).then(({ data }) => {
    comment.replyList = data.data;
    if (comment.replyCount > 5) {
      pageRef.value[index].setPaging(true);
    }
    // 隐藏'查看更多'
    readMoreRef.value[index].style.display = 'none';
  }).catch(() => {});
};

/**
 * 查看当前页的回复评论
 * @param current 页码
 * @param index 索引
 * @param commentId 评论id
 */
const getCurrentPage = (current: number, index: number, commentId: number): void => {
  commentApi.getReplyList(commentId, { current: current, size: 5 }).then(({ data }) => {
    commentList.value[index].replyList = data.data;
  }).catch(() => {});
};

/**
 * 发表/回复评论
 * @param index 索引
 * @param target 评论/回复
 */
const commentOrReply = (index: number, target: Comment | Reply): void => {
  // 隐藏其它评论下方的回复框
  replyRef.value.forEach((element: any) => {
    element.setReply(false);
  });
  // 设置信息并显示
  const currentReply = replyRef.value[index];
  currentReply.nickname = target.fromNickname;
  currentReply.commentForm.replyId = target.id;
  currentReply.commentForm.toUid = target.fromUid;
  currentReply.commentForm.parentId = commentList.value[index].id;
  currentReply.setReply(true);
};

/**
 * 获取评论列表
 */
const getList = (): void => {
  commentApi.getCommentList(commentQuery.value).then(({ data }) => {
    if (commentQuery.value.current == 1) {
      commentList.value = data.data.recordList;
    } else {
      commentList.value.push(...data.data.recordList);
    }
    commentQuery.value.current++;
    count.value = data.data.count;
    emit('getCommentCount', count.value);
  }).catch(() => {});
};

/**
 * 重新加载评论列表
 */
const reloadComments = (): void => {
  commentQuery.value.current = 1;
  getList();
};

/**
 * 重新加载回复列表
 * @param index 索引
 */
const reloadReplies = (index: number) => {
  commentApi.getReplyList(commentList.value[index].id, {
    current: pageRef.value[index].current,
    size: 5
  }).then(({ data }) => {
    commentList.value[index].replyList = data.data;
    commentList.value[index].replyCount++;
    // 隐藏回复框
    replyRef.value[index].setReply(false);
    // 回复大于5条展示分页
    if (commentList.value[index].replyCount > 5) {
      pageRef.value[index].setPaging(true);
    }
    // 隐藏查看更多
    readMoreRef.value[index].style.display = 'none';
  }).catch(() => {});
};

// 刷新评论列表
watch(
    commentList,
    () => {
      refresh.value = false;
      nextTick(() => {
        refresh.value = true;
      });
    },
    { deep: false }
);

// 更新省略文本的最大宽度
watch(
    screenWidth,
    () => {
      if (screenWidth.value < 768) {
        maxEllipsisWidth.value = '15rem';
      } else {
        maxEllipsisWidth.value = '30rem';
      }
    }
);

onMounted(() => {
  getList();
  window.onresize = () => {
    return (() => {
      screenWidth.value = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    })();
  };
});
</script>

<style lang="scss" scoped>
@use '@/assets/styles/mixin.scss';

#comment-container {
  @include mixin.none-select;
  margin: 0 2rem;
}

.comment-title {
  display: flex;
  align-items: center;
  margin: 22px 0;
  padding-left: 10px;
  font-size: 20px;
  font-weight: 600;
  line-height: 40px;
}

.reply-avatar {
  position: absolute;
  left: 0;
}

.comment-item {
  display: flex;
  padding-top: 1rem;

  .comment-avatar {
    @include mixin.flex;
    width: 3rem;
    height: 3.125rem;
  }

  .comment-main {
    flex: auto;
    margin-left: 0.6rem;
  }

  .bottom-line {
    border-bottom: 1px solid var(--grey-3);
    margin-top: 0.5rem;
  }
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 4px;

  .nickname {
    @include mixin.can-select;
    font-size: 0.875rem;
    font-weight: 500;
    margin-right: 0.3125rem;
  }
}

.reply-item {
  position: relative;
  padding: 8px 0 8px 33px;
  font-size: 15px;
  line-height: 24px;

  .reply-user-info {
    display: inline-flex;
    align-items: center;
    margin-right: 9px;
    line-height: 24px;
  }

  .reply-nickname {
    font-size: 13px;
    line-height: 24px;
  }
}

.comment-info {
  display: flex;
  align-items: center;
  margin-top: 0.125rem;
  font-size: 0.8125rem;
  color: var(--grey-5);

  .comment-time {
    margin-right: 15px;
    padding-top: 2px;
  }

  .comment-like {
    display: flex;
    align-items: center;
    margin-right: 17px;
    cursor: pointer;

    &:hover .like {
      color: var(--primary-color);
    }
  }

  .reply-btn {
    font-size: 0.8125rem;
    color: var(--grey-6);

    &:hover {
      color: var(--primary-color);
    }
  }
}

.comment-content {
  @include mixin.can-select;
  overflow: hidden;
  word-wrap: break-word;
  word-break: break-word;
  white-space: pre-wrap;
  font-size: 0.9375rem;
  line-height: 1.5;
  vertical-align: baseline;
}

.view-more {
  font-size: 13px;
  color: var(--grey-5);

  .view-more-btn {
    font-size: 13px;
    color: var(--grey-6);

    &:hover {
      color: var(--primary-color);
    }
  }
}

@media (max-width: 767px) {
  #comment-container {
    margin-left: 0.5rem;
    margin-right: 0.5rem;
  }
}
</style>