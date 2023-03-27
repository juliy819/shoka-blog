<!--
 * @description 分类管理页面
 * @author juliy
 * @date 2023/3/24 22:10
-->
<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form :inline="true" v-show="showSearch">
      <el-form-item label="分类名称">
        <el-input v-model="queryParams.keywords" @keyup.enter="handleSearch"
                  style="width: 200px"
                  placeholder="请输入分类名称" clearable/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleSearch">
          搜索
        </el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb15">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus"
                   @click="openModel(undefined)">
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" icon="Delete" plain
                   :disabled="categoryIdList.length === 0"
                   @click="handleDelete(undefined)">
          批量删除
        </el-button>
      </el-col>
      <right-tool-bar v-model:show-search="showSearch" @queryTable="getList"/>
    </el-row>
    <!-- 表格 -->
    <el-table :data="categoryList" v-loading="loading"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center"/>
      <el-table-column prop="categoryName" label="分类名"
                       align="center"/>
      <el-table-column prop="articleCount" label="文章数量" align="center"/>
      <el-table-column prop="createTime" label="创建时间"
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
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" icon="Edit" link
                     @click="openModel(scope.row)">
            编辑
          </el-button>
          <el-button type="danger" icon="Delete" link
                     @click="handleDelete(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" v-model="addOrUpdate" width="500px"
               append-to-body>
      <el-form ref="categoryFormRef" label-width="100px" :model="categoryForm"
               :rules="rules"
               @submit.native.prevent>
        <el-form-item label="分类名称" prop="categoryName">
          <el-input placeholder="请输入分类名称"
                    v-model="categoryForm.categoryName" style="width: 250px;"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="addOrUpdate = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { reactive, toRefs } from 'vue';
import type { Category, CategoryForm, CategoryQuery } from '@/api/category/types';
import { Clock } from '@element-plus/icons-vue';
import { formatDate } from '@/utils/date';
import type { FormInstance, FormRules } from 'element-plus';
import { addCategory, deleteCategories, getCategoryList, updateCategory } from '@/api/category';
import { messageConfirm, notifySuccess } from '@/utils/modal';

const categoryFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  categoryName: [{
    required: true,
    message: '请输入分类名称',
    trigger: 'blur'
  }]
});
const data = reactive({
  count: 0,
  showSearch: true,
  loading: false,
  title: '',
  addOrUpdate: false,
  queryParams: {
    current: 1,
    size: 10
  } as CategoryQuery,
  categoryForm: {
    id: undefined,
    categoryName: ''
  } as CategoryForm,
  categoryIdList: [] as number[],
  categoryList: [] as Category[]
});
const {
  count,
  showSearch,
  loading,
  title,
  addOrUpdate,
  queryParams,
  categoryForm,
  categoryIdList,
  categoryList
} = toRefs(data);

/**
 * 打开编辑/新增页面
 */
const openModel = (category?: Category): void => {
  categoryFormRef.value?.clearValidate();
  if (category !== undefined) {
    title.value = '修改分类';
    categoryForm.value = {
      id: category.id,
      categoryName: category.categoryName
    };
  } else {
    title.value = '添加分类';
    categoryForm.value = {
      id: undefined,
      categoryName: ''
    };
  }
  addOrUpdate.value = true;
};

/**
 * 提交表单
 */
const submitForm = (): void => {
  categoryFormRef.value?.validate(valid => {
    if (valid) {
      // 有id表示修改
      if (categoryForm.value.id !== undefined) {
        updateCategory(categoryForm.value).then(({ data }) => {
          if (data.flag) {
            notifySuccess(data.msg);
            getList();
          }
          addOrUpdate.value = false;
        });
      } else {
        // 无id表示新增
        addCategory(categoryForm.value).then(({ data }) => {
          if (data.flag) {
            notifySuccess(data.msg);
            getList();
          }
          addOrUpdate.value = false;
        });
      }
    }
  });
};

const handleSelectionChange = (selection: Category[]) => {
  categoryIdList.value = selection.map(item => item.id);
};

/**
 * 删除分类 无id参数代表批量删除
 * @param id 分类id
 */
const handleDelete = (id?: number): void => {
  let idList: number[] = [];
  if (id == undefined) {
    idList = categoryIdList.value;
  } else {
    idList.push(id);
  }
  messageConfirm('确认删除已选中的数据项?').then(() => {
    deleteCategories(idList).then(({ data }) => {
      if (data.flag) {
        notifySuccess(data.msg);
        getList();
      }
    });
  }).catch(() => {
  });
};

/**
 * 获取分类数据
 */
const getList = (): void => {
  loading.value = true;
  getCategoryList(queryParams.value).then(({ data }) => {
    categoryList.value = data.data.recordList;
    count.value = data.data.count;
    loading.value = false;
  });
};

/**
 * 搜索
 */
const handleSearch = (): void => {
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