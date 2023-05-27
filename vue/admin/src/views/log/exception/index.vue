<!--
 * @description 异常日志
 * @author juliy
 * @date 23:18
-->
<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form :model="logQuery" :inline="true" v-show="showSearch">
      <el-form-item label="异常模块">
        <el-input @keyup.enter="queryExceptionLogs" v-model="logQuery.optModule" style="width: 200px"
                  placeholder="请输入异常模块" clearable />
      </el-form-item>
      <el-form-item label="操作描述">
        <el-input @keyup.enter="queryExceptionLogs" v-model="logQuery.keywords" style="width: 200px"
                  placeholder="请输入操作描述" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="queryExceptionLogs">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain :disabled="logIdList.length === 0" icon="Delete"
                   @click="deleteExceptionLogs(undefined)">批量删除
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getExceptionLogList"></right-toolbar>
    </el-row>
    <!-- 表格展示 -->
    <el-table border :data="logList" @selection-change="handleSelectionChange" v-loading="loading">
      <el-table-column type="selection" align="center" width="55"></el-table-column>
      <el-table-column prop="module" label="异常模块" align="center" width="100"></el-table-column>
      <el-table-column prop="description" label="操作描述" align="center" width="160"></el-table-column>
      <el-table-column prop="method" label="请求方式" align="center" width="100">
        <template #default="scope">
          <el-tag :type="tagType(scope.row.requestMethod)">
            {{ scope.row.requestMethod }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="ipAddress" label="操作ip" align="center" width="150"></el-table-column>
      <el-table-column prop="ipSource" label="操作地点" align="center" width="150"></el-table-column>
      <el-table-column prop="os" label="操作系统" align="center" width="150"></el-table-column>
      <el-table-column prop="browser" label="浏览器" align="center"></el-table-column>
      <el-table-column prop="createTime" label="操作日期" align="center" width="140">
        <template #default="scope">
          <div class="create-time">
            <el-icon>
              <clock />
            </el-icon>
            <span style="margin-left: 10px">{{ formatDate(scope.row.createTime) }}</span>
          </div>
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column label="操作" align="center" width="150">
        <template #default="scope">
          <el-button type="primary" icon="View" link @click="showDetails(scope.row)">详细</el-button>
          <el-button type="danger" icon="Delete" link @click="deleteExceptionLogs(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination v-if="count > 0" :total="count" v-model:page="logQuery.current" v-model:limit="logQuery.size"
                @pagination="getExceptionLogList" />
    <!-- 操作日志详细 -->
    <el-dialog title="操作日志详细" v-model="open" width="80%" append-to-body>
      <el-form :model="exceptionLog" label-width="100px">
        <el-form-item label="异常名称：">
          <el-text truncated>
            {{ exceptionLog.name }}
          </el-text>
        </el-form-item>
        <el-form-item label="请求地址：">
          <el-text truncated>
            {{ exceptionLog.uri }}
          </el-text>
        </el-form-item>
        <el-form-item label="异常方法：">
          <el-text truncated>
            {{ exceptionLog.errorMethod }}
          </el-text>
        </el-form-item>
        <preview-code :code="exceptionLog.message" type="java" />
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
import type { ExceptionLog, LogQuery } from '@/api/log/types';
import { formatDate } from '@/utils/date';
import { modal } from '@/utils/modal';
import { onMounted, ref } from 'vue';
import PreviewCode from '@/components/PreviewCode.vue';
import { Clock } from '@element-plus/icons-vue';

const count = ref(0);
const open = ref(false);
const showSearch = ref(true);
const loading = ref(false);
const logQuery = ref<LogQuery>({
  current: 1,
  size: 10
} as LogQuery);
const logIdList = ref<number[]>([]);
const logList = ref<ExceptionLog[]>([]);
const exceptionLog = ref<ExceptionLog>({} as ExceptionLog);

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

const showDetails = (log: ExceptionLog) => {
  exceptionLog.value = log;
  open.value = true;
};

const handleSelectionChange = (selection: ExceptionLog[]) => {
  logIdList.value = selection.map((item) => item.id);
};

const deleteExceptionLogs = (id?: number) => {
  let ids: number[] = [];
  if (id == undefined) {
    ids = logIdList.value;
  } else {
    ids = [id];
  }
  modal.messageConfirm('确认删除已选中的数据项?').then(() => {
    logApi.deleteException(ids).then(() => {
      modal.notifySuccess('删除成功');
      getExceptionLogList();
    });
  }).catch(() => { });
};

const getExceptionLogList = () => {
  loading.value = true;
  logApi.getExceptionLogList(logQuery.value).then(({ data }) => {
    logList.value = data.data.recordList;
    count.value = data.data.count;
    loading.value = false;
  });
};

const queryExceptionLogs = () => {
  logQuery.value.current = 1;
  getExceptionLogList();
};

onMounted(() => {
  getExceptionLogList();
});

</script>

<style scoped>

</style>