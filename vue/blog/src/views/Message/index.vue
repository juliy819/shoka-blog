<!--
 * @description 留言页面
 * @author juliy
 * @date 2023/4/1 16:03
-->
<template>
  <!-- 弹幕输入框 -->
  <div class="message-container">
    <h1 class="message-title">留言板</h1>
    <div class="message-input">
      <input class="input" v-model="messageContent" @keyup.enter="send" placeholder="说点什么吧" />
      <n-button class="send" @click="send">发送</n-button>
    </div>
  </div>
  <!-- 弹幕列表 -->
  <div class="danmaku-container">
    <vue-danmaku ref="danmaku" class="danmaku" v-model:danmus="messageList" use-slot is-suspend random-channel loop>
      <template v-slot:dm="{danmu}">
        <span class="danmaku-item">
          <n-avatar circle size="medium" :src="danmu.avatar" />
          <span class="ml5">{{ danmu.nickname }} :</span>
          <span class="ml5">{{ danmu.messageContent }}</span>
        </span>
      </template>
    </vue-danmaku>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import messageApi from '@/api/message';
import type { Message } from '@/api/message/types';
import useStore from '@/stores';
import VueDanmaku from 'vue3-danmaku';
import { modal } from '@/utils/modal';

const { blogStore, userStore } = useStore();
const messageContent = ref('');
const danmaku = ref();
const messageList = ref<Message[]>([]);

const send = () => {
  if (messageContent.value.trim() == '') {
    modal.msgWarning('留言内容不能为空');
    return false;
  }
  const userAvatar = userStore.avatar ? userStore.avatar : blogStore.siteConfig.touristAvatar;
  const userNickname = userStore.nickname ? userStore.nickname : '游客';
  let message = {
    avatar: userAvatar,
    nickname: userNickname,
    messageContent: messageContent.value
  };
  messageApi.addMessage(message).then(() => {
    if (blogStore.siteConfig.messageCheck) {
      modal.msgWarning('留言成功，正在审核中');
    } else {
      danmaku.value.push(message);
      modal.msgSuccess('留言成功');
    }
    messageContent.value = '';
  }).catch(() => {});
};

onMounted(async () => {
  await messageApi.getMessageList().then(({ data }) => {
    messageList.value = data.data;
  }).catch(() => {});
});

</script>

<style lang="scss" scoped>
@import '@/assets/styles/mixin';

.message-container {
  position: fixed;
  top: 35%;
  left: 0;
  right: 0;
  width: 22.5rem;
  margin: 0 auto;
  text-align: center;
  z-index: 5;
}

.message-title {
  letter-spacing: 0.625rem;
  color: #fff;
  animation: titleScale 1s;
}

.message-input {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  //height: 3rem;
  margin-top: 2rem;

  .input {
    width: 70%;
    height: 2.5rem;
    margin-bottom: 1rem;
    border-radius: 1.25rem;
    padding: 0 1.25rem;
    outline: none;
    background-color: transparent;
    color: #eee;
    border: #fff 0.125rem solid;

    &::-webkit-input-placeholder {
      color: #eee;
    }
  }

  .send {
    height: 2.5rem;
    padding: 0 1.25rem;
    background-color: transparent;
    color: #eee;
    border: #fff 0.125rem solid;
    border-radius: 1.25rem;
    outline: none;
    animation: slideUpIn 0.3s;
  }
}

.danmaku-container {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: var(--color-blue) url("http://static.juliy.top/site-imgs/message-bg.jpg") center center/cover no-repeat;
  animation: slideDownIn 1s;
}

.danmaku {
  position: fixed;
  top: 3.125rem;
  width: 100%;
  height: 100%;

  .danmaku-item {
    display: flex;
    align-items: center;
    padding: 0 0.625rem 0 0.3125rem;
    border-radius: 6.25rem;
    background-color: rgba(0, 0, 0, 0.3);
    color: #fff;
  }
}
</style>