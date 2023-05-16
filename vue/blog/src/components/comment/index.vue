<!--
 * @description 评论组件
 * @author juliy
 * @date 2023/5/12 14:18
-->
<template>
  <div id="comment-wrap">
    <div class="comment-title">
      <svg-icon icon-class="comment" size="1.4rem" style="margin-right: 5px;"></svg-icon>
      评论
    </div>
    <reply-box @reload="reloadComments" :comment-type="commentType" :type-id="typeId" />
    <div v-if="count > 0 && refresh">
      <div class="reply-item" v-for="(comment, index) of commentList" :key="comment.id">
        <div class="reply-box-avatar">
          <n-image class="comment-avatar" :src="comment.avatar" />
          <!--          <img class="shoka-avatar" :src="comment.avatar" alt="">-->
        </div>
        <div class="content-warp">
          <div class="user-info">
            <div class="user-name">{{ comment.fromNickname }}</div>
            <svg-icon v-if="comment.fromUid == 1" icon-class="badge"></svg-icon>
          </div>
          <div class="reply-content" v-html="comment.commentContent"></div>
          <div class="reply-info">
            <span class="reply-time">{{ formatDateTime(comment.createTime) }}</span>
            <span class="reply-like" @click="like(comment)">
              <svg-icon class="like" icon-class="like" size="0.8rem" :class="isLike(comment.id)"
                        style="margin-right: 5px"></svg-icon>
              <span v-show="comment.likeCount">{{ comment.likeCount }}</span>
            </span>
            <span class="reply-btn" @click="handleReply(index, comment)">回复</span>
          </div>
          <div class="sub-reply-item" v-for="reply of comment.replyList" :key="reply.id">
            <div class="sub-user-info">
              <img class="sub-reply-avatar" :src="reply.avatar" alt="" />
              <div class="sub-user-name">{{ reply.fromNickname }}</div>
              <svg-icon v-if="reply.fromUid == 1" icon-class="badge" style="margin-left: 5px;"></svg-icon>
            </div>
            <span class="reply-content">
              <template v-if="reply.fromUid !== reply.toUid">回复 <span style="color: #008ac5">@{{
                  reply.toNickname
                }}</span> :</template>
              <span v-html="reply.commentContent"></span>
            </span>
            <div class="reply-info">
              <span class="reply-time">{{ formatDateTime(reply.createTime) }}</span>
              <span class="reply-like" @click="like(reply)">
                <svg-icon class="like" icon-class="like" size="0.8rem" :class="isLike(reply.id)"
                          style="margin-right: 5px"></svg-icon>
                <span v-show="reply.likeCount > 0">{{ reply.likeCount }}</span>
              </span>
              <span class="reply-btn" @click="handleReply(index, reply)">回复</span>
            </div>
          </div>
          <div ref="readMoreRef" class="view-more" v-show="comment.replyCount > 3">
            <span>共{{ comment.replyCount }}条回复, </span>
            <span class="view-more-btn" @click="readMoreComment(index, comment)">点击查看</span>
          </div>
          <Paging ref="pageRef" :total="comment.replyCount" :index="index" :commentId="comment.id"
                  @get-current-page="getCurrentPage"></Paging>
          <ReplyBox ref="replyRef" class="mt-4" :show="false" :comment-type="commentType" :type-id="typeId"
                    @reload="reloadReplies(index)"></ReplyBox>
          <div class="bottom-line"></div>
        </div>
      </div>
      <div class="loading-warp" v-if="count > commentList.length">
        <n-button class="btn" color="#e9546b" @click="getList">
          加载更多...
        </n-button>
      </div>
    </div>
    <div v-else style="padding: 1.25rem; text-align: center">来发评论吧~</div>
  </div>
</template>

<script setup lang="ts">
import commentApi from '@/api/comment';
import type { Comment, CommentQuery, Reply } from '@/api/comment/types';
import useStore from '@/stores';
import { formatDateTime } from '@/utils/date';
import { modal } from '@/utils/modal';
import { ref } from 'vue';

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
const typeId = computed(() => Number(useRoute().params.id) ? Number(useRoute().params.id) : undefined);
const isLike = computed(() => (id: number) => userStore.commentLikeSet.indexOf(id) != -1 ? 'like-flag' : '');
const commentList = ref<Comment[]>([]);
const refresh = ref(true);
const count = ref(0);
const commentQuery = ref<CommentQuery>({
  current: 1,
  size: 5,
  commentType: props.commentType as number,
  typeId: typeId.value
});

const like = (comment: Comment | Reply) => {
  if (!userStore.id) {
    modal.msgError('请先登录！');
    return;
  }
  let id = comment.id;
  commentApi.likeComment(id).then(({ data }) => {
    if (data.flag) {
      if (userStore.commentLikeSet.indexOf(id) !== -1) {
        comment.likeCount -= 1;
      } else {
        comment.likeCount += 1;
      }
      userStore.commentLike(id);
    }
  });
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

// 查看更多评论
const readMoreComment = (index: number, comment: Comment) => {
  commentApi.getReplyList(comment.id, { current: 1, size: 5 }).then(({ data }) => {
    comment.replyList = data.data;
    if (comment.replyCount > 5) {
      pageRef.value[index].setPaging(true);
    }
    // 隐藏'查看更多'
    readMoreRef.value[index].style.display = 'none';
  });
};

// 查看当前页的回复评论
const getCurrentPage = (current: number, index: number, commentId: number) => {
  commentApi.getReplyList(commentId, { current: current, size: 5 }).then(({ data }) => {
    commentList.value[index].replyList = data.data;
  });
};

const handleReply = (index: number, target: Comment | Reply) => {
  replyRef.value.forEach((element: any) => {
    element.setReply(false);
  });
  const currentReply = replyRef.value[index];
  currentReply.nickname = target.fromNickname;
  currentReply.commentForm.replyId = target.id;
  currentReply.commentForm.toUid = target.fromUid;
  currentReply.commentForm.parentId = commentList.value[index].id;
  currentReply.setReply(true);
};

const getList = () => {
  commentApi.getCommentList(commentQuery.value).then(({ data }) => {
    if (commentQuery.value.current == 1) {
      commentList.value = data.data.recordList;
    } else {
      commentList.value.push(...data.data.recordList);
    }
    commentQuery.value.current++;
    count.value = data.data.count;
    emit('getCommentCount', count.value);
  });
};

// 重新加载评论列表
const reloadComments = () => {
  commentQuery.value.current = 1;
  getList();
};

// 重新加载回复评论
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
  });
};

onMounted(() => {
  getList();
});
</script>

<style lang="scss" scoped>
.comment-title {
  display: flex;
  align-items: center;
  margin: 22px 0;
  padding-left: 10px;
  font-size: 20px;
  font-weight: 600;
  line-height: 40px;
}

.sub-reply-avatar {
  position: absolute;
  left: 0;
  width: 1.5rem;
  height: 1.5rem;
  border-radius: 50%;
}

.reply-item {
  display: flex;
  padding-top: 1rem;

  .content-warp {
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

  .user-name {
    font-size: 0.875rem;
    font-weight: 500;
    margin-right: 0.3125rem;
  }
}

.sub-reply-item {
  position: relative;
  padding: 8px 0 8px 33px;
  font-size: 15px;
  line-height: 24px;

  .sub-user-info {
    display: inline-flex;
    align-items: center;
    margin-right: 9px;
    line-height: 24px;
  }

  .sub-user-name {
    font-size: 13px;
    line-height: 24px;
  }
}

.reply-info {
  display: flex;
  align-items: center;
  margin-top: 0.125rem;
  font-size: 0.8125rem;
  color: #9499a0;

  .reply-time {
    margin-right: 15px;
    padding-top: 2px;
  }

  .reply-like {
    display: flex;
    align-items: center;
    margin-right: 17px;
    cursor: pointer;

    &:hover .like {
      color: var(--color-pink);
    }
  }

  .reply-btn {
    cursor: pointer;

    &:hover {
      color: var(--color-pink);
    }
  }
}

.reply-content {
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
  color: #9499a0;

  .view-more-btn {
    cursor: pointer;

    &:hover {
      color: var(--color-pink);
    }
  }
}
</style>