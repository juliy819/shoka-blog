<!--
 * @description 修改密码组件
 * @author juliy
 * @date 2023/3/20 20:09
-->
<template>
  <div>
    <svg-icon
      icon-class="password"
      size="1.2rem"
      @click="openDialog"></svg-icon>
    <el-dialog
      v-model="open"
      :before-close="handleClose"
      title="修改密码"
      width="500px">
      <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="rules"
        label-width="120px"
        status-icon>
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input
            v-model="ruleForm.oldPassword"
            autocomplete="false"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            v-model="ruleForm.newPassword"
            autocomplete="false"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input
            v-model="ruleForm.checkPassword"
            autocomplete="false"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="open = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue';
import type { FormInstance, FormRules } from 'element-plus';
import type { Password } from '@/api/user/types';
import { updateAdminPassword } from '@/api/user';
import { messageConfirm, notifySuccess } from '@/utils/modal';

const open = ref(false);
const ruleFormRef = ref<FormInstance>();
const ruleForm = ref<Password>({
  oldPassword: '',
  newPassword: '',
  checkPassword: ''
});

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入新密码'));
  } else if (value !== ruleForm.value.newPassword) {
    callback(new Error('两次输入的密码不一致'));
  } else {
    callback();
  }
};

const rules = reactive<FormRules>({
  oldPassword: [{
    required: true, message: '请输入密码', trigger: 'blur'
  }, { min: 6, message: '密码不能少于6位', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码不能少于6位', trigger: 'blur' }],
  checkPassword: [
    { required: true, validator: validatePass, trigger: 'blur' },
    { min: 6, message: '密码不能少于6位', trigger: 'blur' }]
});

const openDialog = () => {
  ruleForm.value = {
    oldPassword: '',
    newPassword: '',
    checkPassword: ''
  };
  ruleFormRef.value?.resetFields();
  open.value = true;
};

const handleClose = (done: () => void) => {
  messageConfirm('确定要关闭窗口吗')
      .then(() => {
        done();
      })
      .catch(() => {
      });
};

const submitForm = () => {
  ruleFormRef.value?.validate((valid) => {
    if (valid) {
      updateAdminPassword(ruleForm.value).then(({ data }) => {
        if (data.flag) {
          notifySuccess(data.msg);
        }
        open.value = false;
      });
    }
  });
};
</script>

<style lang="scss" scoped>
.el-input {
  width: 250px;
}

:deep(.el-overlay) {
  cursor: default;
}
</style>