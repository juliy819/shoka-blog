<!--
 * @description 评论回复框
 * @author juliy
 * @date 2023/5/12 15:04
-->
<template>
  <div class="reply-box" v-if="show">
    <div class="box-normal">
      <div class="reply-avatar">
        <n-avatar size="large" round :src="userStore.avatar ? userStore.avatar : blogStore.siteConfig.touristAvatar" />
      </div>
      <div class="reply-box-wrap">
        <textarea class="reply-box-textarea" v-model="commentContent" :style="isContentEmpty ? '' : inputActiveClass"
                  :placeholder="placeholderText" />
      </div>
      <n-button size="large" class="reply-box-send" :class="isContentEmpty ? '' : 'send-active'" @click="handleAdd">
        评论
      </n-button>
    </div>
    <div class="box-extend">
      <emoji @add-emoji="handleEmoji" />
    </div>
  </div>
</template>

<script setup lang="ts">
import type { CommentForm } from '@/api/comment/types';
import useStore from '@/stores';
import emojiList from '@/utils/emoji';
import { modal } from '@/utils/modal';
import Emoji from '@/components/Emoji.vue';
import { ref } from 'vue';
import commentApi from '@/api/comment';

const entrys = Object.entries(emojiList);
const { userStore, blogStore, appStore } = useStore();
const inputActiveClass = {
  lineHeight: 'normal',
  borderColor: '#ed6ea0',
  backgroundColor: 'var(--grey-0)'
};
const emit = defineEmits(['reload']);
const props = defineProps({
  commentType: {
    type: Number
  },
  show: {
    type: Boolean,
    default: true
  },
  typeId: {
    type: Number
  }
});
const nickname = ref('');
const show = ref(props.show);
const commentContent = ref('');
const commentForm = ref<CommentForm>({
  typeId: props.typeId,
  commentType: props.commentType as number,
  parentId: undefined,
  replyId: undefined,
  toUid: undefined,
  commentContent: ''
});
const placeholderText = computed(() =>
    nickname.value ? `回复 @${nickname.value}：` : '发一条友善的评论'
);
const isContentEmpty = computed(() => commentContent.value.length === 0);

const handleEmoji = (key: string) => {
  commentContent.value += key;
};
const handleAdd = () => {
  if (!userStore.id) {
    modal.msgError('请先登录！');
    return;
  }
  if (commentContent.value.trim() == '') {
    modal.msgError('评论不能为空');
    return;
  }
  // 解析表情
  commentForm.value.commentContent = commentContent.value.replace(/\[.+?]/g, (str) => {
    if (str in emojiList) {
      return (
          '<img src= \'' +
          emojiList[str as keyof typeof emojiList] +
          '\' width=\'21\' height=\'21\' style=\'margin: 0 1px;vertical-align: text-bottom\' alt=""/>'
      );
    }
    return str;
  });
  commentApi.addComment(commentForm.value).then(() => {
    commentContent.value = '';
    if (blogStore.siteConfig.commentCheck) {
      modal.msgWarning('评论成功，正在审核中');
    } else {
      modal.msgSuccess('评论成功');
    }
    // 重新加载评论列表
    emit('reload');
  }).catch(() => {});
};

const setReply = (flag: boolean) => {
  show.value = flag;
};

defineExpose({ commentForm, nickname, setReply });
</script>

<style scoped lang="scss">
@use '@/assets/styles/mixin';

.reply-box {
  display: flex;
  flex-direction: column;
}

.box-normal {
  display: flex;
  height: 50px;
  transition: 0.2s;

  .reply-box-wrap {
    flex: auto;
    margin-left: 0.6rem;
  }

  .reply-box-send {
    height: 100%;
    margin-left: 10px;
    background-color: var(--primary-color-darker);
    color: var(--grey-0);
  }

  .send-active {
    background-color: var(--color-pink);
  }
}

.reply-avatar {
  @include mixin.flex;
  width: 3rem;
  height: 3.125rem;
}

.reply-box-textarea {
  width: 100%;
  height: 100%;
  padding: 5px 10px;
  border: 1px solid var(--grey-3);
  border-radius: 6px;
  background-color: var(--comment-color);
  font-size: 12px;
  line-height: 38px;
  color: var(--text-color);
  resize: none;
  outline: none;
}

.box-extend {
  display: flex;
  align-items: center;
  margin-left: 3.6rem;
  margin-top: 0.3125rem;
}
</style>
