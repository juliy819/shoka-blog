<!--
 * @description 操作日志
 * @author juliy
 * @date 22:44
-->
<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form :model="queryParams" :inline="true" v-show="showSearch">
      <el-form-item label="操作模块">
        <el-input @keyup.enter="queryOperationLog" v-model="queryParams.optModule" style="width: 200px"
                  placeholder="请输入操作模块" clearable />
      </el-form-item>
      <el-form-item label="操作描述">
        <el-input @keyup.enter="queryOperationLog" v-model="queryParams.keywords" style="width: 200px"
                  placeholder="请输入操作描述" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="queryOperationLog">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain :disabled="logIdList.length === 0" icon="Delete"
                   @click="deleteOperationLogs(undefined)">批量删除
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getOperationLogList"></right-toolbar>
    </el-row>
    <!-- 表格展示 -->
    <el-table border :data="logList" @selection-change="handleSelectionChange" v-loading="loading">
      <!-- 表格列 -->
      <el-table-column type="selection" align="center" width="55"></el-table-column>
      <!-- 操作模块 -->
      <el-table-column prop="module" label="操作模块" align="center" width="120"></el-table-column>
      <!-- 操作描述 -->
      <el-table-column prop="description" label="操作描述" align="center" width="160"></el-table-column>
      <!-- 请求方式 -->
      <el-table-column prop="method" label="请求方式" align="center" width="110">
        <template #default="scope">
          <el-tag :type="tagType(scope.row.method)">
            {{ scope.row.method }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 操作人员 -->
      <el-table-column prop="nickname" label="操作人员" align="center"></el-table-column>
      <!-- 操作ip -->
      <el-table-column prop="ipAddress" label="操作ip" align="center" width="150"></el-table-column>
      <!-- 操作地点 -->
      <el-table-column prop="ipSource" label="操作地点" align="center" width="180"></el-table-column>
      <!-- 操作日期 -->
      <el-table-column prop="createTime" label="操作日期" align="center" width="190">
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
      <el-table-column label="操作" align="center" width="150">
        <template #default="scope">
          <el-button type="primary" icon="View" link @click="showDetails(scope.row)">详细</el-button>
          <el-button type="danger" icon="Delete" link @click="deleteOperationLogs(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination v-if="count > 0" :total="count" v-model:page="queryParams.current" v-model:limit="queryParams.size"
                @pagination="getOperationLogList" />
    <!-- 操作日志详细 -->
    <el-dialog title="操作日志详细" v-model="open" width="700px" append-to-body>
      <el-form :model="optLog" label-width="100px">
        <el-form-item label="操作模块：">
          {{ optLog.module }}
        </el-form-item>
        <el-form-item label="操作类型：">
          {{ optLog.type }}
        </el-form-item>
        <el-form-item label="请求地址：">
          {{ optLog.uri }}
        </el-form-item>
        <el-form-item label="请求方式：">
          <el-tag :type="tagType(optLog.method)">
            {{ optLog.method }}
          </el-tag>
        </el-form-item>
        <el-form-item label="操作方法：">
          {{ optLog.name }}
        </el-form-item>
        <el-form-item label="请求参数：">
          <div class="content">{{ optLog.params }}</div>
        </el-form-item>
        <el-form-item label="返回数据：">
          {{ optLog.data }}
        </el-form-item>
        <el-form-item label="操作耗时：">
          <el-tag> {{ optLog.times }}ms</el-tag>
        </el-form-item>
        <el-form-item label="操作人员：">
          {{ optLog.nickname }}
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="open = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import logApi from '@/api/log';
import type { LogQuery, OperationLog } from '@/api/log/types';
import { formatDateTime } from '@/utils/date';
import { modal } from '@/utils/modal';
import { ref } from 'vue';
import { Clock } from '@element-plus/icons-vue';

const count = ref(0);
const open = ref(false);
const showSearch = ref(true);
const loading = ref(false);
const queryParams = ref<LogQuery>({
  current: 1,
  size: 10
} as LogQuery);
const logIdList = ref<number[]>([]);
const logList = ref<OperationLog[]>([]);
const optLog = ref<OperationLog>({} as OperationLog);

const tagType = (type: string) => {
  switch (type) {
    case 'GET':
      return '';
    case 'POST':
      return 'success';
    case 'PUT':
      return 'warning';
    case 'DELETE':
      return 'danger';
  }
};

const handleSelectionChange = (selection: OperationLog[]) => {
  logIdList.value = selection.map((item) => item.id);
};

const showDetails = (log: OperationLog) => {
  optLog.value = log;
  open.value = true;
};

const deleteOperationLogs = (id?: number) => {
  let ids: number[] = [];
  if (id == undefined) {
    ids = logIdList.value;
  } else {
    ids = [id];
  }
  modal.messageConfirm('确认删除已选中的数据项?').then(() => {
    logApi.deleteOperation(ids).then(() => {
      modal.notifySuccess('删除成功');
      getOperationLogList();
    });
  }).catch(() => { });
};

const getOperationLogList = () => {
  loading.value = true;
  logApi.getOperationLogList(queryParams.value).then(({ data }) => {
    logList.value = data.data.recordList;
    count.value = data.data.count;
    loading.value = false;
  });
};

const queryOperationLog = () => {
  queryParams.value.current = 1;
  getOperationLogList();
};

onMounted(() => {
  getOperationLogList();
});

</script>

<style scoped>
.content {
  word-break: break-all;
}
</style>
