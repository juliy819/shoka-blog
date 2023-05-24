<!--
 * @description 个人信息
 * @author juliy
 * @date 2023/4/1 16:37
-->
<template>
  <div class="page-header">
    <h1 class="page-title">个人中心</h1>
    <img class="page-cover" src="http://static.juliy.top/site-imgs/def-bg.png" alt="">
    <waves />
  </div>
  <div class="bg">
    <div class="page-container">
      <div class="title">基本信息</div>
      <div class="info-container">
        <user-avatar class="avatar" />
        <div class="info mt30">
          <n-form label-align="left" :label-width="80" :model="userForm">
            <n-form-item label="昵称" path="nickname">
              <n-input placeholder="输入您的昵称" v-model:value="userForm.nickname" clearable />
            </n-form-item>
            <n-form-item label="个人网站" path="website">
              <n-input placeholder="没有的话就空着叭" v-model:value="userForm.webSite" clearable />
            </n-form-item>
            <n-form-item label="简介" path="intro">
              <n-input placeholder="介绍一下自己吧" v-model:value="userForm.intro" clearable />
            </n-form-item>
            <n-form-item label="邮箱" path="email">
              <n-input-group>
                <n-input placeholder="请输入邮箱" disabled v-model:value="userStore.email"></n-input>
                <n-button v-if="userStore.email" @click="appStore.showEmailFrame()">
                  修改邮箱
                </n-button>
                <n-button v-else @click="appStore.showEmailFrame()">
                  绑定邮箱
                </n-button>
              </n-input-group>
            </n-form-item>
          </n-form>
          <n-space justify="center">
            <n-button style="width: 5rem" round @click="handleUpdate">修改</n-button>
          </n-space>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import userApi from '@/api/user';
import type { UserInfo } from '@/api/user/types';
import useStore from '@/stores';
import { modal } from '@/utils/modal';
import UserAvatar from '@/views/User/UserAvatar.vue';

const { userStore, appStore } = useStore();
const router = useRouter();
const userForm = ref<UserInfo>({
  nickname: userStore.nickname,
  intro: userStore.intro,
  webSite: userStore.webSite
});

const handleUpdate = () => {
  userApi.updateUserInfo(userForm.value).then(() => {
    userStore.updateUserInfo(userForm.value);
    modal.msgSuccess('修改成功');
  }).catch(() => {});
};

onMounted(() => {
  if (!userStore.id) {
    router.push('/').then(() => { modal.msgError('请先登录');});
  }
});
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.n-button {
  background-color: var(--primary-color);
  color: var(--grey-1);
}

.title {
  font-size: 1.25rem;
  font-weight: 700;
}

.info-container {
  @include flex;
  flex-wrap: wrap;
  margin-top: 1rem;

  .avatar {
    display: inline-flex;
    width: 230px;
    height: 140px;
  }

  .info {
    width: 530px;
  }
}

@media (max-width: 850px) {
  .avatar {
    justify-content: center;
  }
}
</style>