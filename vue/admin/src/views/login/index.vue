<template>
  <div class="login-view">
    <el-form ref="ruleFormRef" :model="loginForm" :rules="rules" class="login-form">
      <h3 class="title">博客后台管理系统</h3>

      <el-form-item prop="username">
        <el-input v-model="loginForm.username" class="form-input" placeholder="账号" size="large" type="text">
          <template #prefix>
            <svg-icon icon-class="user"></svg-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" class="form-input" placeholder="密码" show-password size="large"
                  type="password" @keyup.enter="handleLogin(ruleFormRef)">
          <template #prefix>
            <el-icon :size="20">
              <svg-icon icon-class="password"></svg-icon>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loading" class="login-button" type="primary" @click.prevent="handleLogin(ruleFormRef)">
          <span v-if="!loading">登 录</span> <span v-else>登 录 中 ...</span>
        </el-button>
      </el-form-item>
    </el-form>

    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2022 - {{ new Date().getFullYear() }} By juliy</span>
    </div>
  </div>
</template>

<script lang="ts" setup>
import type { FormInstance, FormRules } from 'element-plus';
import { ElInput } from 'element-plus';
import { reactive, ref } from 'vue';
import useStore from '@/stores';
import router from '@/router';

const { userStore } = useStore();
const loading = ref(false);
const ruleFormRef = ref<FormInstance>();
const loginForm = reactive({
  username: 'juliy@qq.com',
  password: '123456'
});
const rules = reactive<FormRules>({
  username: [
    {
      required: true,
      message: '请输入用户名',
      trigger: 'blur'
    }
  ],
  password: [
    {
      required: true,
      message: '请输入密码',
      trigger: 'blur'
    },
    {
      min: 6,
      message: '密码不能少于6位',
      trigger: 'blur'
    }
  ]
});

const handleLogin = async (formEl: FormInstance | undefined) => {
  if (!formEl) {
    return;
  }
  await formEl.validate((valid) => {
    if (valid) {
      loading.value = true;
      userStore
        .login(loginForm)
        .then(() => {
          router.push({ path: '/' });
          loading.value = false;
        })
        .catch(() => {
          loading.value = false;
        });
    } else {
      return false;
    }
  });
};
</script>

<style lang="scss" scoped>
input:-webkit-autofill {
  -webkit-text-fill-color: #ffffff !important;
  transition: background-color 5000s ease-in-out 10s !important;
}

.login-view {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: #ecf0f3;
  // background-image: url('https://static.ttkwsd.top/config/0d7d8d691e644989b72ddda5f695aca2.jpg');
  // background-size: cover;
  user-select: none;
}

.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  background-color: #ecf0f3;
  box-shadow: 10px 10px 10px #d1d9e6, -10px -10px 10px #f9f9f9;
  border-radius: 12px;
  width: 400px;
  padding: 25px 25px 5px 25px;
}

.form-input {
  &:deep(.el-input__wrapper) {
    border: none;
    outline: none;
    background-color: #ecf0f3;
    transition: 0.25s ease;
    border-radius: 8px;
    box-shadow: inset 2px 2px 4px #d1d9e6, inset -2px -2px 4px #f9f9f9;

    &:has(.el-input__inner:focus) {
      box-shadow: inset 4px 4px 4px #d1d9e6, inset -4px -4px 4px #f9f9f9;
    }

    &:-webkit-autofill {
      height: 10px;
    }
  }

  // 设置浏览器自动填充账号后不改变背景色
  &:deep(:-webkit-autofill) {
    -webkit-transition: color 99999s ease-out, background-color 99999s ease-out;
  }

  &:deep(::selection) {
    color: #ecf0f3;
    background-color: #4b70e2;
  }
}

.login-button {
  width: 150px;
  height: 40px;
  border: none;
  outline: none;
  margin: auto;
  border-radius: 25px;
  font-weight: 700;
  font-size: 15px;
  letter-spacing: 1.2px;
  background-color: #4b70e2;
  color: #f9f9f9;
  box-shadow: 8px 8px 16px #d1d9e6, -8px -8px 16px #f9f9f9;
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  font-family: Arial, sans-serif;
  font-size: 12px;
  letter-spacing: 1px;
  transition: color 99999s ease-out, background-color 99999s ease-out;
  -webkit-transition: color 99999s ease-out, background-color 99999s ease-out;
}
</style>