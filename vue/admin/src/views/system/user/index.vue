<!--
 * @description 用户管理
 * @author juliy
 * @date 13:20
-->
<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form :model="userQuery" :inline="true">
      <el-form-item label="用户昵称">
        <el-input @keyup.enter="queryUser" v-model="userQuery.keywords" style="width: 200px"
                  placeholder="请输入用户昵称" clearable />
      </el-form-item>
      <el-form-item label="登录方式">
        <el-select v-model="userQuery.loginType" placeholder="请选择登录方式" clearable style="width: 200px">
          <el-option v-for="item in typeList" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="queryUser">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格展示 -->
    <el-table border :data="userList" v-loading="loading">
      <!-- 用户头像 -->
      <el-table-column prop="avatar" label="头像" align="center" width="100">
        <template #default="scope">
          <img :src="scope.row.avatar" width="40" height="40" alt="" />
        </template>
      </el-table-column>
      <!-- 昵称 -->
      <el-table-column prop="nickname" label="昵称" align="center" width="140"></el-table-column>
      <!-- 登录方式 -->
      <el-table-column prop="loginType" label="登录方式" align="center" width="100">
        <template #default="scope">
          <el-tag type="success" v-if="scope.row.loginType == 1">邮箱</el-tag>
          <el-tag v-if="scope.row.loginType == 2">QQ</el-tag>
          <el-tag type="danger" v-if="scope.row.loginType == 3">Gitee</el-tag>
          <el-tag type="info" v-if="scope.row.loginType == 4">Github</el-tag>
        </template>
      </el-table-column>
      <!-- 用户角色 -->
      <el-table-column prop="roleList" label="用户角色" align="center">
        <template #default="scope">
          <el-tag v-for="item in scope.row.roleList" :key="item.id" style="margin-right:4px;margin-top:4px">
            {{ item.roleName }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 状态 -->
      <el-table-column prop="status" label="状态" align="center" width="100">
        <template #default="scope">
          <el-switch v-model="scope.row.isDisable" active-color="#13ce66" inactive-color="#ff4949" :active-value="0"
                     :inactive-value="1" @change="changeUserStatus(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <!-- 登录ip -->
      <el-table-column prop="ipAddress" label="登录ip" align="center" width="140"></el-table-column>
      <!-- 登录地址 -->
      <el-table-column prop="ipSource" label="登录地址" align="center" width="140"></el-table-column>
      <!-- 创建时间 -->
      <el-table-column prop="createTime" label="创建时间" align="center" width="130">
        <template #default="scope">
          <div class="create-time">
            <el-icon>
              <clock />
            </el-icon>
            <span style="margin-left: 10px">{{ formatDate(scope.row.createTime) }}</span>
          </div>
        </template>
      </el-table-column>
      <!-- 登录时间 -->
      <el-table-column prop="loginTime" label="登录时间" align="center" width="130">
        <template #default="scope">
          <div class="create-time">
            <el-icon>
              <clock />
            </el-icon>
            <span style="margin-left: 10px">{{ formatDate(scope.row.loginTime) }}</span>
          </div>
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column label="操作" align="center" width="100">
        <template #default="scope">
          <el-button type="primary" icon="Edit" link @click="openModel(scope.row)">
            编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination v-if="count > 0" :total="count" v-model:page="userQuery.current" v-model:limit="userQuery.size"
                @pagination="getUserList" />
    <!-- 添加或修改对话框 -->
    <el-dialog title="修改用户" v-model="update" width="500px" append-to-body>
      <el-form ref="userFormRef" label-width="100px" :model="userForm" :rules="rules">
        <el-form-item label="昵称" prop="nickname">
          <el-input placeholder="请输入昵称" v-model="userForm.nickname" style="width: 250px;" />
        </el-form-item>
        <el-form-item label="角色" prop="roleIdList">
          <el-checkbox-group v-model="roleIdList">
            <el-checkbox v-for="item in userRoleList" :key="item.id" :label="item.id">
              {{ item.roleName }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="update = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import userApi from '@/api/user';
import type { User, UserForm, UserQuery, UserRole } from '@/api/user/types';
import { formatDate } from '@/utils/date';
import { modal } from '@/utils/modal';
import type { FormInstance, FormRules } from 'element-plus';
import { ref } from 'vue';
import { Clock } from '@element-plus/icons-vue';

const typeList = [
  {
    value: 1,
    label: '邮箱'
  },
  {
    value: 2,
    label: 'QQ'
  },
  {
    value: 3,
    label: 'Gitee'
  },
  {
    value: 4,
    label: 'Github'
  }
];

const userFormRef = ref<FormInstance>();
const rules = ref<FormRules>({
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  roleIdList: [{ required: true, message: '角色不能为空', trigger: 'click' }]
});
const count = ref(0);
const update = ref(false);
const loading = ref(false);
const userQuery = ref<UserQuery>({
  current: 1,
  size: 10
} as UserQuery);
const userList = ref<User[]>([]);
const userRoleList = ref<UserRole[]>([]);
const roleIdList = ref<number[]>([]);
const userForm = ref<UserForm>({} as UserForm);

const openModel = (user: User) => {
  roleIdList.value = [];
  userForm.value.id = user.id;
  userForm.value.nickname = user.nickname;
  user.roleList.forEach(item => {
    roleIdList.value.push(item.id);
  });
  userFormRef.value?.clearValidate();
  update.value = true;
};

const changeUserStatus = (user: User) => {
  let text = user.isDisable === 0 ? '解封' : '封禁';
  modal.messageConfirm('确定要' + text + user.nickname + '吗?').then(() => {
    userApi.updateUserStatus({ id: user.id, isDisable: user.isDisable }).then(() => {
      modal.notifySuccess(text + '成功');
    });
  }).catch(() => { user.isDisable = user.isDisable === 0 ? 1 : 0; });
};

const submitForm = () => {
  userForm.value.roleIdList = roleIdList.value;
  userFormRef.value?.validate((valid) => {
    if (valid) {
      userApi.updateUser(userForm.value).then(() => {
        modal.notifySuccess('修改成功');
        getUserList();
        update.value = false;
      });
    }
  });
};

const getUserList = () => {
  loading.value = true;
  userApi.getUserList(userQuery.value).then(({ data }) => {
    userList.value = data.data.recordList;
    count.value = data.data.count;
    loading.value = false;
  });
};

const queryUser = () => {
  userQuery.value.current = 1;
  getUserList();
};

onMounted(() => {
  getUserList();
  userApi.getUserRoleList().then(({ data }) => {
    userRoleList.value = data.data;
  });
});

</script>

<style scoped></style>

