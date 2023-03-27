<!--
 * @description 发布文章页面
 * @author juliy
 * @date 2023/3/24 22:12
-->
<template>
  <div class="app-container">
    <!-- 文章标题 -->
    <div class="header-container">
      <el-input v-model="articleForm.articleTitle"
                placeholder="请输入文章标题"/>
      <el-button style="margin-left: 10px" @click="openModel" type="danger">
        发布文章
      </el-button>
    </div>
    <md-editor v-model="articleForm.articleContent"
               :toolbars="toolbars"
               ref="editorRef"
               class="md-container"
               placeholder="请输入文章内容...">
      <template #defToolbars>
        <emoji-extension :on-insert="insert"></emoji-extension>
      </template>
    </md-editor>
    <!-- 发布/修改 对话框 -->
    <el-dialog title="发布文章" v-model="addOrUpdate" width="600px"
               append-to-body="append-to-body">
      <el-form ref="articleFormRef" :model="articleForm" :rules="rules">
        <!-- 文章分类 -->
        <el-form-item label="文章分类" prop="categoryName">
          <el-tag type="success" v-show="articleForm.categoryName">
            {{ articleForm.categoryName }}
          </el-tag>
          <!-- 分类选项 -->
          <el-popover v-if="!articleForm.categoryName" placement="bottom-start">
            <template #reference>
              <el-button type="success" plain>添加分类</el-button>
            </template>
            <div class="popover-title">分类</div>
            <el-autocomplete
                placeholder="请输入分类名搜索,enter可添加自定义分类">
            </el-autocomplete>
          </el-popover>
        </el-form-item>
        <!-- 文章标签 -->
        <el-form-item label="文章标签" prop="tagNameList">
          <el-tag v-for="(item, index) of articleForm.tagNameList" :key="index"
                  :closable="true" type="success"
                  v-show="articleForm.tagNameList" style="margin-right: 1rem;">
            {{ item }}
          </el-tag>
          <!-- 标签选项 -->
          <el-popover v-if="articleForm.tagNameList.length < 3"
                      placement="bottom-start">
            <template #reference>
              <el-button type="success" plain>添加标签</el-button>
            </template>
            <div class="popover-title">标签</div>
            <!-- 搜索框 -->
            <el-autocomplete
                placeholder="请输入标签名搜索,enter可添加自定义标签">
            </el-autocomplete>
            <!-- 标签 -->
          </el-popover>
        </el-form-item>
        <!-- 文章类型 -->
        <el-form-item>
          <el-form-item label="文章类型" prop="articleType">
            <el-select v-model="articleForm.articleType"
                       placeholder="请选择类型">
              <el-option v-for="item in typeList" :key="item.value"
                         :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form-item>
        <!-- 缩略图 -->
        <el-form-item label="缩略图" prop="articleCover">
          <el-upload>
            <el-icon drag v-if="articleForm.articleCover === ''">
              <upload-filled/>
            </el-icon>
            <div v-if="articleForm.articleCover === ''">
              将文件拖到此处，或<em>点击上传</em>
            </div>
          </el-upload>
        </el-form-item>
        <!-- 置顶 -->
        <el-form-item label="置顶" prop="isTop">
          <el-switch v-model="articleForm.isTop" :active-value="1"
                     :inactive-value="0"></el-switch>
        </el-form-item>
        <!-- 推荐 -->
        <el-form-item label="推荐" prop="isRecommend">
          <el-switch v-model="articleForm.isRecommend" :active-value="1"
                     :inactive-value="0"></el-switch>
        </el-form-item>
        <!-- 发布形式 -->
        <el-form-item label="发布形式" prop="status">
          <el-radio-group v-model="articleForm.status">
            <el-radio :label="1">公开</el-radio>
            <el-radio :label="2">私密</el-radio>
            <el-radio :label="3">草稿</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button v-if="articleForm.status !== 3" type="danger">
            发布文章
          </el-button>
          <el-button v-else type="danger">保存草稿</el-button>
          <el-button @click="addOrUpdate = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script setup lang='ts'>
import type { ExposeParam, InsertContentGenerator } from 'md-editor-v3';
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { useDark, useDateFormat } from '@vueuse/core';
import { reactive, ref } from 'vue';
import type { ArticleForm } from '@/api/article/types';
import { toolbars } from '@/components/EmojiExtension/staticConfig';
import type { FormInstance, FormRules } from 'element-plus';
import { UploadFilled } from '@element-plus/icons-vue';
import { msgError } from '@/utils/modal';

const editorRef = ref<ExposeParam>();
const isDark = useDark();
const articleTitle = ref(useDateFormat(new Date(), 'YYYY-MM-DD'));
const addOrUpdate = ref(false);

//表格检验
const articleFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  categoryName: [{
    required: true,
    message: '文章分类不能为空',
    trigger: 'blur'
  }],
  tagNameList: [{
    required: true,
    message: '文章标签不能为空',
    trigger: 'blur'
  }]
});

const articleForm = reactive<ArticleForm>({
  id: undefined,
  articleCover: '',
  articleTitle: articleTitle.value,
  articleContent: 'content',
  categoryName: '',
  tagNameList: [],
  articleType: 1,
  isTop: 0,
  isRecommend: 0,
  status: 1
});

const typeList = reactive([
  { value: 1, label: '原创' },
  { value: 2, label: '转载' },
  { value: 3, label: '翻译' }
]);


const openModel = () => {
  if (articleForm.articleTitle.trim() === '') {
    msgError('文章标题不能为空');
    return;
  }
  if (articleForm.articleContent.trim() === '') {
    msgError('文章内容不能为空');
    return;
  }
  // 避免再次打开弹窗后显示分类、标签不能为空
  articleFormRef.value?.clearValidate();
  addOrUpdate.value = true;
};

const insert = (generator: InsertContentGenerator) => {
  editorRef.value?.insert(generator);
};
</script>

<style scoped lang='scss'>
.header-container {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
}

.md-container {
  min-height: 300px;
  height: calc(100vh - 200px);
}
</style>