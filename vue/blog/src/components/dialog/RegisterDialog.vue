<!--
 * @description 注册窗口
 * @author juliy
 * @date 22:06
-->
<template>
  <n-modal class="bg" v-model:show="dialogVisible" preset="dialog" :show-icon="false" title="注册"
           transform-origin="center" :block-scroll="false">
    <n-form ref="formRef" :rules="rules" :model="registerForm" label-placement="left" class="mt20">
      <n-form-item label="邮箱" path="username">
        <n-input class="dialog-input" placeholder="" v-model:value="registerForm.username" />
      </n-form-item>
      <n-form-item label="密码" path="password">
        <n-input class="dialog-input" type="password" placeholder="" v-model:value="registerForm.password" />
      </n-form-item>
      <n-form-item label="验证码" path="code">
        <n-input-group>
          <n-input class="dialog-input" placeholder="" v-model:value="registerForm.code" />
          <n-button type="success" secondary :disabled="flag" @click="sendCode">
            {{ timer == 0 ? '发送' : `${timer}s` }}
          </n-button>
        </n-input-group>
      </n-form-item>
      <n-form-item>
        <n-button ref="registerRef" secondary type="error" style="width:100%" :loading="loading"
                  @click="handleRegister">
          注册
        </n-button>
      </n-form-item>
    </n-form>
    已有账号？
    <n-button text @click="handleLogin">立即登录</n-button>
  </n-modal>
</template>

<script setup lang="ts">
import loginApi from '@/api/login';
import type { LoginForm } from '@/api/login/types';
import type { UserForm } from '@/api/user/types';
import useStore from '@/stores';
import { useIntervalFn } from '@vueuse/core';
import { modal } from '@/utils/modal';
import { ref } from 'vue';
import type { FormInst } from 'naive-ui';

const formRef = ref<FormInst | null>(null);
const rules = {
  username: {
    required: true,
    message: '请输入邮箱',
    trigger: ['blur', 'input']
  },
  password: {
    required: true,
    message: '请输入密码',
    trigger: ['blur', 'input']
  },
  code: {
    required: true,
    message: '请输入6位验证码',
    trigger: ['blur', 'input']
  }
};
const { appStore, userStore } = useStore();
const timer = ref(0);
const flag = ref(false);
const loading = ref(false);
const registerForm = ref<UserForm>({
  username: '',
  password: '',
  code: ''
});

const { pause, resume } = useIntervalFn(() => {
  timer.value--;
  if (timer.value <= 0) {
    // 停止定时器
    pause();
    flag.value = false;
  }
}, 1000, { immediate: false });

const start = (time: number) => {
  flag.value = true;
  timer.value = time;
  // 启动定时器
  resume();
};

const sendCode = () => {
  let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
  if (!reg.test(registerForm.value.username)) {
    modal.msgWarning('邮箱格式不正确');
    return;
  }
  start(60);
  loginApi.sendCode(registerForm.value.username).then(() => {
    modal.msgSuccess('发送成功');
  });
};

const handleRegister = () => {
  formRef.value?.validate(errors => {
    if (!errors) {
      loading.value = true;
      loginApi.register(registerForm.value).then(() => {
        let loginForm: LoginForm = {
          username: registerForm.value.username,
          password: registerForm.value.password
        };
        userStore.login(loginForm).then(() => {
          registerForm.value = {
            username: '',
            password: '',
            code: ''
          };
          appStore.registerFrame = false;
        });
        loading.value = false;
      }).catch(() => {loading.value = false;});
    }
  });
};

const dialogVisible = computed({
  get: () => appStore.registerFrame,
  set: (value) => appStore.registerFrame = value
});

const handleLogin = () => {
  appStore.registerFrame = false;
  appStore.showLoginFrame();
};
</script>

<style scoped></style>
