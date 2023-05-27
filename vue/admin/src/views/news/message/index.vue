<!--
 * @description 留言管理
 * @author juliy
 * @date 2023/4/1 14:57
-->
<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form :model="messageQuery" :inline="true" v-show="showSearch">
      <el-form-item label="用户昵称">
        <el-input @keyup.enter="queryMessages" v-model="messageQuery.keywords" style="width: 200px"
                  placeholder="请输入用户昵称" clearable />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="messageQuery.isCheck" placeholder="留言状态" clearable style="width: 200px">
          <el-option v-for="item in status" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="queryMessages">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain :disabled="messageIdList.length === 0" icon="Delete"
                   @click="deleteMessages(undefined)">批量删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain :disabled="messageIdList.length === 0" icon="Check"
                   @click="checkMessages(undefined)">批量通过
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getMessageList"></right-toolbar>
    </el-row>
    <!-- 表格展示 -->
    <el-table border :data="messageList" @selection-change="handleSelectionChange" v-loading="loading">
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <!-- 用户头像 -->
      <el-table-column prop="avatar" label="头像" align="center" width="100">
        <template #default="scope">
          <img :src="scope.row.avatar" width="40" height="40" alt="" />
        </template>
      </el-table-column>
      <!-- 留言人 -->
      <el-table-column prop="nickname" label="留言人" align="center" width="150"
                       :show-overflow-tooltip="true"></el-table-column>
      <!-- 留言内容 -->
      <el-table-column prop="messageContent" label="留言内容" align="center"
                       :show-overflow-tooltip="true"></el-table-column>
      <!-- IP地址 -->
      <el-table-column prop="ipAddress" label="ip地址" align="center" width="120"></el-table-column>
      <!-- IP来源 -->
      <el-table-column prop="ipSource" label="ip来源" align="center" width="170"></el-table-column>
      <!-- 留言状态 -->
      <el-table-column prop="isCheck" label="状态" align="center" width="90">
        <template #default="scope">
          <el-tag v-if="scope.row.isCheck == 0" type="warning">审核中</el-tag>
          <el-tag v-if="scope.row.isCheck == 1" type="success">通过</el-tag>
        </template>
      </el-table-column>
      <!-- 留言时间 -->
      <el-table-column prop="createTime" label="留言时间" align="center" width="190">
        <template #default="scope">
          <div class="create-time">
            <el-icon>
              <clock />
            </el-icon>
            <span style="margin-left: 10px">{{ formatDateTime(scope.row.createTime) }}</span>
          </div>
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column label="操作" align="center" width="160">
        <template #default="scope">
          <el-button type="primary" icon="Finished" link v-if="scope.row.isCheck == 0"
                     @click="checkMessages(scope.row.id)">
            通过
          </el-button>
          <el-button type="danger" icon="Delete" link @click="deleteMessages(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination v-if="count > 0" :total="count" v-model:page="messageQuery.current" v-model:limit="messageQuery.size"
                @pagination="getMessageList" />
  </div>
</template>

<script setup lang="ts">
import messageApi from '@/api/message';
import type { Message, MessageQuery } from '@/api/message/types';
import type { CheckDTO } from '@/model';
import { formatDateTime } from '@/utils/date';
import { modal } from '@/utils/modal';
import { ref } from 'vue';
import { Clock } from '@element-plus/icons-vue';

const status = [
  {
    value: 0,
    label: '审核中'
  },
  {
    value: 1,
    label: '通过'
  }
];
const count = ref(0);
const showSearch = ref(true);
const loading = ref(false);
const messageQuery = ref({
  current: 1,
  size: 10
} as MessageQuery);
const messageIdList = ref([] as number[]);
const messageList = ref([] as Message[]);

const handleSelectionChange = (selection: Message[]) => {
  messageIdList.value = selection.map((item) => item.id);
};

const checkMessages = (id?: number) => {
  let ids: number[];
  if (id == undefined) {
    ids = messageIdList.value;
  } else {
    ids = [id];
  }
  let checkDTO: CheckDTO = {
    idList: ids,
    isCheck: 1
  };
  modal.messageConfirm('确认通过已选中的数据项?').then(() => {
    messageApi.updateMessageCheck(checkDTO).then(() => {
      modal.notifySuccess('审核成功');
      getMessageList();
    });
  }).catch(() => { });
};

const deleteMessages = (id?: number) => {
  let ids: number[] = [];
  if (id == undefined) {
    ids = messageIdList.value;
  } else {
    ids = [id];
  }
  modal.messageConfirm('确认删除已选中的数据项?').then(() => {
    messageApi.deleteMessage(ids).then(() => {
      modal.notifySuccess('删除成功');
      getMessageList();
    });
  }).catch(() => { });
};

const getMessageList = () => {
  loading.value = true;
  messageApi.getMessageList(messageQuery.value).then(({ data }) => {
    messageList.value = data.data.recordList;
    count.value = data.data.count;
    loading.value = false;
  });
};

const queryMessages = () => {
  messageQuery.value.current = 1;
  getMessageList();
};

onMounted(() => {
  getMessageList();
});

</script>

<style scoped>

</style>