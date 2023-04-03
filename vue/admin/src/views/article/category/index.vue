<!--
 * @description 分类管理页面
 * @author juliy
 * @date 2023/3/24 22:10
-->
<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form v-show="showSearch" :inline="true">
      <el-form-item label="分类名称">
        <el-input v-model="queryParams.keywords" clearable placeholder="请输入分类名称" style="width: 200px"
                  @keyup.enter="queryCategories" />
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" type="primary" @click="queryCategories">搜索
        </el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button icon="Plus" plain type="primary" @click="addCategory">
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button :disabled="multiple" icon="Delete" plain type="danger" @click="deleteCategories(undefined)">
          删除
        </el-button>
      </el-col>
      <!-- 右侧工具栏 -->
      <right-toolbar v-model:show-search="showSearch" @queryTable="getCategoryList" />
    </el-row>
    <!-- 表格 -->
    <el-table v-loading="loading" :data="categoryList" @selection-change="changeSelectedId">
      <el-table-column align="center" type="selection" />
      <el-table-column align="center" label="分类名" min-width="150" prop="categoryName" />
      <el-table-column align="center" label="文章数量" min-width="100" prop="articleCount" />
      <el-table-column align="center" label="创建时间" min-width="150" prop="createTime">
        <template #default="scope">
          <div class="create-time">
            <el-icon>
              <clock />
            </el-icon>
            <span style="margin-left: 10px">
              {{ formatDate(scope['row']['createTime']) }}
            </span>
          </div>
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column align="center" label="操作" min-width="150">
        <template #default="scope">
          <el-button icon="Edit" link type="primary" @click="updateCategory(scope.row)">
            修改
          </el-button>
          <el-button icon="Delete" link type="danger" @click="deleteCategories(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页工具 -->
    <pagination v-if="count > 0" v-model:limit="queryParams.size" v-model:page="queryParams.current" :total="count"
                @pagination="getCategoryList" />
    <!-- 添加或修改对话框 -->
    <el-dialog v-model="open" :title="title" append-to-body width="500px">
      <el-form ref="categoryFormRef" :model="categoryForm" :rules="rules" label-width="100px" @submit.prevent>
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="categoryForm.categoryName" placeholder="请输入分类名称" style="width: 250px;"
                    @keyup.enter="submitForm" />
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

<script lang="ts" setup>
import { reactive, ref, toRefs } from 'vue';
import type { Category, CategoryForm, CategoryQuery } from '@/api/category/types';
import { Clock } from '@element-plus/icons-vue';
import { formatDate } from '@/utils/date';
import type { FormInstance, FormRules } from 'element-plus';
import { categoryApi } from '@/api/category';
import { modal } from '@/utils/modal';

const categoryFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  categoryName: [{
    required: true,
    message: '请输入分类名称',
    trigger: 'blur'
  }]
});
const count = ref(0);
const showSearch = ref(true);
const loading = ref(false);
const title = ref('');
const multiple = ref(true);
const open = ref(false);
const categoryIdList = ref<number[]>([]);
const categoryList = ref<Category[]>([]);
const data = reactive({
  queryParams: {
    current: 1,
    size: 10
  } as CategoryQuery,
  categoryForm: {
    id: undefined,
    categoryName: ''
  } as CategoryForm
});
const { queryParams, categoryForm } = toRefs(data);

/**
 * 添加分类
 */
const addCategory = (): void => {
  categoryFormRef.value?.clearValidate();
  title.value = '添加分类';
  categoryForm.value = {
    id: undefined,
    categoryName: ''
  };
  open.value = true;
};

/**
 * 修改分类
 * @param category
 */
const updateCategory = (category: Category): void => {
  categoryFormRef.value?.clearValidate();
  title.value = '修改分类';
  categoryForm.value = {
    id: category.id,
    categoryName: category.categoryName
  };
  open.value = true;
};

/**
 * 提交表单
 */
const submitForm = (): void => {
  categoryFormRef.value?.validate(valid => {
    if (valid) {
      // 有id表示修改
      if (categoryForm.value.id !== undefined) {
        categoryApi.updateCategory(categoryForm.value).then(() => {
          modal.notifySuccess('修改成功');
          getCategoryList();
          open.value = false;
        });
      } else {
        // 无id表示新增
        categoryApi.addCategory(categoryForm.value).then(() => {
          modal.notifySuccess('添加成功');
          getCategoryList();
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
const changeSelectedId = (selection: Category[]): void => {
  categoryIdList.value = selection.map(item => item.id);
  multiple.value = !selection.length;
};

/**
 * 删除分类 无id参数代表批量删除
 * @param id 分类id
 */
const deleteCategories = (id?: number): void => {
  let idList: number[] = [];
  if (id == undefined) {
    idList = categoryIdList.value;
  } else {
    idList.push(id);
  }
  modal.messageConfirm('确认删除已选中的数据项?').then(() => {
    categoryApi.deleteCategories(idList).then(() => {
      modal.notifySuccess('删除成功');
      getCategoryList();
    });
  }).catch(() => {
  });
};

/**
 * 获取分类数据
 */
const getCategoryList = (): void => {
  loading.value = true;
  categoryApi.getCategoryList(queryParams.value).then(({ data }) => {
    categoryList.value = data.data.recordList;
    count.value = data.data.count;
    loading.value = false;
  });
};

/**
 * 搜索分类
 */
const queryCategories = (): void => {
  queryParams.value.current = 1;
  getCategoryList();
};

/**
 * 重置搜索
 */
const resetQuery = (): void => {
  queryParams.value.keywords = '';
  queryParams.value.current = 1;
  getCategoryList();
};

// 初始化时加载数据
onMounted(() => {
  getCategoryList();
});

</script>