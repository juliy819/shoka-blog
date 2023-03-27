<!--
 * @description 标签管理页面
 * @author juliy
 * @date 2023/3/24 22:11
-->
<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form :inline="true" v-show="showSearch">
      <el-form-item label="标签名称">
        <el-input v-model="queryParams.keywords" @keyup.enter="handleQuery"
                  style="width: 200px"
                  placeholder="请输入标签名称" clearable/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd">
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete(undefined)">
          删除
        </el-button>
      </el-col>
      <!-- 右侧工具栏 -->
      <right-tool-bar v-model:show-search="showSearch" @queryTable="getList"/>
    </el-row>
    <!-- 表格 -->
    <el-table :data="tagList" v-loading="loading"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center"/>
      <el-table-column prop="tagName" label="标签名" min-width="150"
                       align="center"/>
      <el-table-column prop="articleCount" label="文章数量" min-width="100" align="center"/>
      <el-table-column prop="createTime" label="创建时间" min-width="150"
                       align="center">
        <template #default="scope">
          <div class="create-time">
            <el-icon>
              <clock/>
            </el-icon>
            <span style="margin-left: 10px">
              {{ formatDate(scope.row.createTime) }}
            </span>
          </div>
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column label="操作" align="center" min-width="150">
        <template #default="scope">
          <el-button type="primary" icon="Edit" link
                     @click="handleUpdate(scope.row)">
            修改
          </el-button>
          <el-button type="danger" icon="Delete" link
                     @click="handleDelete(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页工具 -->
    <pagination v-if="count > 0" :total="count" v-model:page="queryParams.current" v-model:limit="queryParams.size"
                @pagination="getList"/>
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" v-model="open" width="500px"
               append-to-body>
      <el-form ref="tagFormRef" label-width="100px" :model="tagForm"
               :rules="rules"
               @submit.native.prevent>
        <el-form-item label="标签名称" prop="tagName">
          <el-input placeholder="请输入标签名称" @keyup.enter="submitForm"
                    v-model="tagForm.tagName" style="width: 250px;"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, toRefs } from 'vue';
import { Clock } from '@element-plus/icons-vue';
import { formatDate } from '@/utils/date';
import type { FormInstance, FormRules } from 'element-plus';
import { messageConfirm, notifySuccess } from '@/utils/modal';
import type { Tag, TagForm, TagQuery } from '@/api/tag/types';
import { addTag, deleteTags, getTagList, updateTag } from '@/api/tag';

const tagFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  tagName: [{
    required: true,
    message: '请输入标签名称',
    trigger: 'blur'
  }]
});
const count = ref(0);
const showSearch = ref(true);
const loading = ref(false);
const title = ref('');
const multiple = ref(false);
const open = ref(false);
const tagIdList = ref<number[]>([]);
const tagList = ref<Tag[]>([]);

const data = reactive({
  queryParams: {
    current: 1,
    size: 10
  } as TagQuery,
  tagForm: {
    id: undefined,
    tagName: ''
  } as TagForm
});
const { queryParams, tagForm } = toRefs(data);

/**
 * 添加标签
 */
const handleAdd = (): void => {
  tagFormRef.value?.clearValidate();
  title.value = '添加标签';
  tagForm.value = {
    id: undefined,
    tagName: ''
  };
  open.value = true;
};

/**
 * 修改标签
 * @param tag
 */
const handleUpdate = (tag: Tag): void => {
  tagFormRef.value?.clearValidate();
  title.value = '修改标签';
  tagForm.value = {
    id: tag.id,
    tagName: tag.tagName
  };
  open.value = true;
};

/**
 * 提交表单
 */
const submitForm = (): void => {
  tagFormRef.value?.validate(valid => {
    if (valid) {
      // 有id表示修改
      if (tagForm.value.id !== undefined) {
        updateTag(tagForm.value).then(({ data }) => {
          if (data.flag) {
            notifySuccess(data.msg);
            getList();
          }
          open.value = false;
        });
      } else {
        // 无id表示新增
        addTag(tagForm.value).then(({ data }) => {
          if (data.flag) {
            notifySuccess(data.msg);
            getList();
          }
          open.value = false;
        });
      }
    }
  });
};

/**
 * 多选处理
 * @param selection 选择项
 */
const handleSelectionChange = (selection: Tag[]): void => {
  tagIdList.value = selection.map(item => item.id);
  multiple.value = !selection.length;
};

/**
 * 删除标签 无id参数代表批量删除
 * @param id 标签id
 */
const handleDelete = (id?: number): void => {
  let idList: number[] = [];
  if (id == undefined) {
    idList = tagIdList.value;
  } else {
    idList.push(id);
  }
  messageConfirm('确认删除已选中的数据项?').then(() => {
    deleteTags(idList).then(({ data }) => {
      if (data.flag) {
        notifySuccess(data.msg);
        getList();
      }
    });
  }).catch(() => {
  });
};

/**
 * 获取标签数据
 */
const getList = (): void => {
  loading.value = true;
  getTagList(queryParams.value).then(({ data }) => {
    tagList.value = data.data.recordList;
    count.value = data.data.count;
    loading.value = false;
  });
};

/**
 * 搜索
 */
const handleQuery = (): void => {
  queryParams.value.current = 1;
  getList();
};

/**
 * 重置搜索
 */
const resetQuery = (): void => {
  queryParams.value.keywords = '';
  queryParams.value.current = 1;
  getList();
};

// 初始化时加载数据
onMounted(() => {
  getList();
});

</script>

<style scoped lang='scss'>

</style>