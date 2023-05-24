<!--
 * @description EmailDialog
 * @author juliy
 * @date 22:06
-->
<template>
  <n-modal class="bg" title="邮箱" v-model:show="dialogVisible" preset="dialog" :show-icon="false"
           transform-origin="center" style="padding-bottom: 2rem;" :block-scroll="false">
    <n-form ref="formRef" :rules="rules" :model="emailForm" label-placement="left" class="mt20">
      <n-form-item label="邮箱" path="email">
        <n-input class="dialog-input" placeholder="" v-model:value="emailForm.email" />
      </n-form-item>
      <n-form-item label="验证码" path="code">
        <n-input-group>
          <n-input class="dialog-input" placeholder="" v-model:value="emailForm.code" />
          <n-button type="success" secondary :disabled="flag" @click="sendCode">
            {{ timer == 0 ? '发送' : `${timer}s` }}
          </n-button>
        </n-input-group>
      </n-form-item>
      <n-form-item>
        <n-button ref="registerRef" secondary type="error" style="width:100%" :loading="loading" @click="handleUpdate">
          绑定
        </n-button>
      </n-form-item>
    </n-form>
  </n-modal>
</template>

<script setup lang="ts">
import loginApi from '@/api/login';
import userApi from '@/api/user';
import type { EmailForm } from '@/api/user/types';
import useStore from '@/stores';
import { useIntervalFn } from '@vueuse/core';
import { modal } from '@/utils/modal';
import { ref } from 'vue';
import type { FormInst } from 'naive-ui';

const formRef = ref<FormInst | null>(null);
const rules = {
  email: {
    required: true,
    message: '请输入邮箱',
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
const emailForm = ref<EmailForm>({
  email: '',
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
  if (!reg.test(emailForm.value.email)) {
    modal.msgWarning('邮箱格式不正确');
    return;
  }
  start(60);
  loginApi.sendCode(emailForm.value.email).then(() => {
    modal.msgSuccess('发送成功');
  }).catch(() => {});
};

const dialogVisible = computed({
  get: () => appStore.emailFrame,
  set: (value) => appStore.emailFrame = value
});

const handleUpdate = () => {
  formRef.value?.validate(errors => {
    if (!errors) {
      loading.value = true;
      userApi.updateEmail(emailForm.value).then(() => {
        modal.msgSuccess('修改成功');
        userStore.email = emailForm.value.email;
        emailForm.value = {
          email: '',
          code: ''
        };
        appStore.emailFrame = false;
        loading.value = false;
      }).catch(() => {loading.value = false;});
    }
  });
};
</script>

<style scoped></style>
