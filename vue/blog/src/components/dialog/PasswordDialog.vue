<!--
 * @description 修改密码窗口
 * @author juliy
 * @date 22:06
-->
<template>
  <n-modal title="忘记密码" class="bg" v-model:show="dialogVisible" preset="dialog" :show-icon="false"
           transform-origin="center" :block-scroll="false">
    <n-form ref="formRef" :rules="rules" :model="userForm" label-placement="left" class="mt20">
      <n-form-item label="邮箱" path="username">
        <n-input class="dialog-input" placeholder="" v-model:value="userForm.username" />
      </n-form-item>
      <n-form-item label="新密码" path="password">
        <n-input class="dialog-input" type="password" placeholder="" v-model:value="userForm.password" />
      </n-form-item>
      <n-form-item label="验证码" path="code">
        <n-input-group>
          <n-input class="dialog-input" placeholder="" v-model:value="userForm.code" />
          <n-button type="success" secondary :disabled="flag" @click="sendCode">
            {{ timer == 0 ? '发送' : `${timer}s` }}
          </n-button>
        </n-input-group>
      </n-form-item>
      <n-form-item>
        <n-button secondary type="error" style="width:100%" :loading="loading" @click="changePassword">
          确定
        </n-button>
      </n-form-item>
    </n-form>
  </n-modal>
</template>

<script setup lang="ts">
import loginApi from '@/api/login';
import userApi from '@/api/user';
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
const { appStore } = useStore();
const timer = ref(0);
const flag = ref(false);
const loading = ref(false);
const userForm = ref<UserForm>({
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
  if (!reg.test(userForm.value.username)) {
    modal.msgWarning('邮箱格式不正确');
    return;
  }
  start(60);
  loginApi.sendCode(userForm.value.username).then(() => {
    modal.msgSuccess('发送成功');
  }).catch(() => {});
};

const changePassword = () => {
  if (userForm.value.code.trim().length != 6) {
    modal.msgWarning('请输入6位验证码');
    return;
  }
  if (userForm.value.password.trim().length < 6) {
    modal.msgWarning('密码不能少于6位');
    return;
  }
  loading.value = true;
  userApi.updatePassword(userForm.value).then(() => {
    modal.msgSuccess('修改成功');
    userForm.value = {
      username: '',
      password: '',
      code: ''
    };
    appStore.passwordFrame = false;
    loading.value = false;
  }).catch(() => {});
};
const dialogVisible = computed({
  get: () => appStore.passwordFrame,
  set: (value) => appStore.passwordFrame = value
});
</script>

<style scoped></style>
