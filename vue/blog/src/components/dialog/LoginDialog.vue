<!--
 * @description 登录窗口
 * @author juliy
 * @date 2023/4/28 15:04
-->
<template>
  <n-modal title="登录" v-model:show="showLoginDialog" :show-icon="false" class="bg" preset="dialog"
           transform-origin="center" :block-scroll="false">
    <n-form ref="formRef" :rules="rules" :model="loginForm" label-placement="left" class="mt20">
      <n-form-item label="账号" path="username">
        <n-input class="dialog-input" v-model:value="loginForm.username" placeholder="" type="text" />
      </n-form-item>
      <n-form-item label="密码" path="password">
        <n-input class="dialog-input" v-model:value="loginForm.password" placeholder="" type="password" />
      </n-form-item>
      <n-form-item>
        <n-button style="width: 100%" type="error" secondary :loading="loading" @click="login()">登录</n-button>
      </n-form-item>
    </n-form>
    <n-space justify="space-between" style="width: 100%;">
      <n-button text @click="toRegister()">立即注册</n-button>
      <n-button text @click="toPassword()">忘记密码?</n-button>
    </n-space>
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
    message: '请输入账号',
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
  set: value => {appStore.loginFrame = value;}
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
    appStore.loginFrame = false;
    loading.value = false;
  }).catch(() => loading.value = false);
};

const toRegister = () => {
  appStore.loginFrame = false;
  appStore.showRegisterFrame();
};

const toPassword = () => {
  appStore.loginFrame = false;
  appStore.showPasswordFrame();
};

</script>

<style scoped lang="scss"></style>

<style>
.dialog-input .n-input-wrapper {
  padding: 0;
}

.dialog-input .n-input__input-el {
  padding: 0 10px;
}
</style>