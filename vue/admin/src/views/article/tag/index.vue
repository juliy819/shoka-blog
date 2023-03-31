<!--
 * @description 标签管理页面
 * @author juliy
 * @date 2023/3/24 22:11
-->
<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form v-show="showSearch" :inline="true">
      <el-form-item label="标签名称">
        <el-input v-model="queryParams.keywords" clearable placeholder="请输入标签名称" style="width: 200px"
                  @keyup.enter="queryTags" />
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" type="primary" @click="queryTags">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button icon="Plus" plain type="primary" @click="addTag">
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button :disabled="multiple" icon="Delete" plain type="danger" @click="deleteTags(undefined)">
          删除
        </el-button>
      </el-col>
      <!-- 右侧工具栏 -->
      <right-tool-bar v-model:show-search="showSearch" @queryTable="getTagList" />
    </el-row>
    <!-- 表格 -->
    <el-table v-loading="loading" :data="tagList" @selection-change="changeSelectedId">
      <el-table-column align="center" type="selection" />
      <el-table-column align="center" label="标签名" min-width="150" prop="tagName" />
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
          <el-button icon="Edit" link type="primary" @click="updateTag(scope.row)">
            修改
          </el-button>
          <el-button icon="Delete" link type="danger" @click="deleteTags(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页工具 -->
    <pagination v-if="count > 0" v-model:limit="queryParams.size" v-model:page="queryParams.current" :total="count"
                @pagination="getTagList" />
    <!-- 添加或修改对话框 -->
    <el-dialog v-model="open" :title="title" append-to-body width="500px">
      <el-form ref="tagFormRef" :model="tagForm" :rules="rules" label-width="100px" @submit.prevent>
        <el-form-item label="标签名称" prop="tagName">
          <el-input v-model="tagForm.tagName" placeholder="请输入标签名称" style="width: 250px;"
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
import { Clock } from '@element-plus/icons-vue';
import { formatDate } from '@/utils/date';
import type { FormInstance, FormRules } from 'element-plus';
import { modal } from '@/utils/modal';
import type { Tag, TagForm, TagQuery } from '@/api/tag/types';
import { tagApi } from '@/api/tag';

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
const multiple = ref(true);
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
const addTag = (): void => {
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
const updateTag = (tag: Tag): void => {
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
        tagApi.updateTag(tagForm.value).then(() => {
          modal.notifySuccess('修改成功');
          getTagList();
          open.value = false;
        });
      } else {
        // 无id表示新增
        tagApi.addTag(tagForm.value).then(() => {
          modal.notifySuccess('添加成功');
          getTagList();
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
const changeSelectedId = (selection: Tag[]): void => {
  tagIdList.value = selection.map(item => item.id);
  multiple.value = !selection.length;
};

/**
 * 删除标签 无id参数代表批量删除
 * @param id 标签id
 */
const deleteTags = (id?: number): void => {
  let idList: number[] = [];
  if (id == undefined) {
    idList = tagIdList.value;
  } else {
    idList.push(id);
  }
  modal.messageConfirm('确认删除已选中的数据项?').then(() => {
    tagApi.deleteTags(idList).then(() => {
      modal.notifySuccess('删除成功');
      getTagList();
    });
  }).catch(() => {
  });
};

/**
 * 获取标签数据
 */
const getTagList = (): void => {
  loading.value = true;
  tagApi.getTagList(queryParams.value).then(({ data }) => {
    tagList.value = data.data.recordList;
    count.value = data.data.count;
    loading.value = false;
  });
};

/**
 * 搜索标签
 */
const queryTags = (): void => {
  queryParams.value.current = 1;
  getTagList();
};

/**
 * 重置搜索
 */
const resetQuery = (): void => {
  queryParams.value.keywords = '';
  queryParams.value.current = 1;
  getTagList();
};

// 初始化时加载数据
onMounted(() => {
  getTagList();
});

</script>

<style lang="scss" scoped>

</style>