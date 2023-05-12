<!--
 * @description 登录窗口
 * @author juliy
 * @date 2023/4/28 15:04
-->
<template>
  <n-modal v-model:show="showLoginDialog" :show-icon="false" class="bg" preset="dialog" transform-origin="center"
           :block-scroll="false">
    <!-- todo 登录弹窗待实现 -->
    <n-form ref="formRef" :rules="rules" :model="loginForm" label-placement="left" :show-label="false" class="mt30">
      <n-form-item label="账号" path="username">
        <n-input v-model:value="loginForm.username" placeholder="账号" />
      </n-form-item>
      <n-form-item label="密码" path="password">
        <n-input v-model:value="loginForm.password" placeholder="密码" type="password" />
      </n-form-item>
      <n-form-item>
        <n-button style="width: 100%" :loading="loading" @click="login()">登录</n-button>
      </n-form-item>
    </n-form>
  </n-modal>
</template>

<script setup lang="ts">
import useStore from '@/stores';
import { ref } from 'vue';
import type { FormInst } from 'naive-ui';
import type { LoginForm } from '@/api/login/types';
import { modal } from '@/utils/modal';

const { blogStore, appStore, userStore } = useStore();
const loading = ref(false);
const formRef = ref<FormInst | null>(null);
const rules = {
  username: {
    required: true,
    message: '请输入用户名',
    trigger: ['blur', 'input']
  },
  password: {
    required: true,
    message: '请输入密码',
    trigger: ['blur', 'input']
  }
};
const loginForm = ref<LoginForm>({
  username: '',
  password: ''
});

const showLoginDialog = computed({
  get: () => appStore.loginFrame,
  set: () => appStore.hideLoginFrame()
});

const login = () => {
  let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
  if (!reg.test(loginForm.value.username)) {
    modal.msgError('邮箱格式不正确');
    return;
  }
  if (loginForm.value.password === '') {
    modal.msgError('密码不能为空');
    return;
  }
  loading.value = true;
  userStore.login(loginForm.value).then(() => {
    loginForm.value = {
      username: '',
      password: ''
    };
    appStore.hideLoginFrame();
    loading.value = false;
  }).catch(() => loading.value = false);
};
</script>

<style scoped lang="scss">

</style>