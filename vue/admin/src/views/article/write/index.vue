<!--
 * @description 发布文章页面
 * @author juliy
 * @date 2023/3/24 22:12
-->
<template>
  <div class="app-container">
    <!-- 文章标题 -->
    <div class="header-container">
      <el-input v-model="articleForm.articleTitle" placeholder="请输入文章标题" />
      <el-button type="danger" @click="resetArticleForm(true)">重置</el-button>
      <el-button plain @click="saveDraft">保存</el-button>
      <el-button type="primary" @click="openModel">发布文章</el-button>
    </div>
    <!-- 编辑器 -->
    <v-md-editor v-model="articleForm.articleContent" :disabled-menus="[]" :toc-nav-position-right="true" height="550px"
                 left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code tip emoji | save"
                 placeholder="请输入文章内容..." @upload-image="uploadImage" />
    <!-- 发布/修改 对话框 -->
    <el-dialog v-model="open" :append-to-body="true" title="发布文章" top="0.5vh" width="600px">
      <el-form ref="articleFormRef" :model="articleForm" :rules="rules" label-width="80">
        <!-- 文章分类 -->
        <el-form-item label="文章分类" prop="categoryName">
          <el-tag v-show="articleForm.categoryName" :closable="true" :disable-transitions="true" round size="large"
                  @close="removeCategory">
            {{ articleForm.categoryName }}
          </el-tag>
          <!-- 分类选项 -->
          <el-popover v-if="!articleForm.categoryName" placement="bottom-start" trigger="click" width="400">
            <template #reference>
              <el-button icon="plus" plain round type="success">
                添加分类
              </el-button>
            </template>
            <div class="popover-title">分类</div>
            <el-autocomplete v-model="categoryName" :fetch-suggestions="searchCategory" :trigger-on-focus="false"
                             placeholder="请输入分类名搜索,enter可添加自定义分类" style="width: 100%"
                             value-key="categoryName" @select="selectCategory" @keyup.enter="saveCategory" />
            <!-- 分类 -->
            <div class="popover-container">
              <el-check-tag v-for="(category, index) of categoryList" :key="index" checked class="popover-item"
                            @click="addCategory(category.categoryName)">
                {{ category.categoryName }}
              </el-check-tag>
            </div>
          </el-popover>
        </el-form-item>
        <!-- 文章标签 -->
        <el-form-item label="文章标签" prop="tagNameList">
          <el-tag v-for="(tagName, index) of articleForm.tagNameList" v-show="articleForm.tagNameList" :key="index"
                  :closable="true" :disable-transitions="true" round size="large" style="margin-right: 1rem;"
                  @close="removeTag(tagName)">
            {{ tagName }}
          </el-tag>
          <!-- 标签选项 -->
          <el-popover v-if="articleForm.tagNameList.length < 3" placement="bottom-start" trigger="click" width="400">
            <template #reference>
              <el-button icon="plus" plain round type="success">添加标签
              </el-button>
            </template>
            <div class="popover-title">标签</div>
            <!-- 搜索框 -->
            <el-autocomplete v-model="tagName" :fetch-suggestions="searchTag" :trigger-on-focus="false"
                             placeholder="请输入标签名搜索,enter可添加自定义标签" style="width: 100%"
                             value-key="tagName" @select="selectTag" @keyup.enter="saveTag" />
            <!-- 标签 -->
            <div class="popover-container">
              <el-check-tag v-for="(tag, index) of tagList" :key="index"
                            :class="{ 'popover-item-selected': articleForm.tagNameList.indexOf(tag.tagName) !== -1 }"
                            checked class="popover-item" @click="addTag(tag.tagName)">
                {{ tag.tagName }}
              </el-check-tag>
            </div>
          </el-popover>
        </el-form-item>
        <!-- 文章类型 -->
        <el-form-item label="文章类型" prop="articleType">
          <el-select v-model="articleForm.articleType" placeholder="请选择类型">
            <el-option v-for="item in typeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <!-- 缩略图 -->
        <el-form-item label="缩略图" prop="articleCover">
          <el-upload :before-upload="beforeUpload" :headers="authorization" :on-success="updateArticleCover"
                     :show-file-list="false" accept="image/*" action="/api/article/upload" drag>
            <el-icon v-if="articleForm.articleCover === ''" class="el-icon--upload">
              <upload-filled />
            </el-icon>
            <div v-if="articleForm.articleCover === ''" class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <img v-else :src="articleForm.articleCover" alt="加载失败" width="360" />
          </el-upload>
        </el-form-item>
        <!-- 置顶 -->
        <el-form-item label="置顶" prop="isTop">
          <el-switch v-model="articleForm.isTop" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <!-- 推荐 -->
        <el-form-item label="推荐" prop="isFeatured">
          <el-switch v-model="articleForm.isFeatured" :active-value="1" :inactive-value="0"></el-switch>
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
          <el-button v-if="articleForm.status !== 3" type="primary" @click="submitForm">
            发布文章
          </el-button>
          <el-button v-else type="primary" @click="submitForm">保存草稿
          </el-button>
          <el-button @click="open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { useDateFormat } from '@vueuse/core';
import { reactive, ref } from 'vue';
import type { ArticleForm, CategoryOption, TagOption } from '@/api/article/types';
import type { FormInstance, FormRules, UploadRawFile } from 'element-plus';
import { UploadFilled } from '@element-plus/icons-vue';
import { articleApi } from '@/api/article';
import * as imageConversion from 'image-conversion';
import type { AxiosResponse } from 'axios';
import { getToken, token_prefix } from '@/utils/token';
import type { Awaitable } from 'element-plus/es/utils';
import { modal } from '@/utils/modal';
import useStore from '@/stores';
import router from '@/router';

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

const { tagStore } = useStore();
const route = useRoute();
const articleId = route.params.articleId;
const articleTitle = ref(useDateFormat(new Date(), 'YYYY-MM-DD'));
const open = ref(false);
const categoryList = ref<CategoryOption[]>([]);
const tagList = ref<TagOption[]>([]);
const categoryName = ref('');
const tagName = ref('');
const articleForm = ref<ArticleForm>({
  id: undefined,
  articleCover: '',
  articleTitle: articleTitle.value,
  articleContent: '',
  categoryName: '',
  tagNameList: [],
  articleType: 1,
  isTop: 0,
  isFeatured: 0,
  status: 3
});
const typeList = reactive([
  { value: 1, label: '原创' },
  { value: 2, label: '转载' },
  { value: 3, label: '翻译' }
]);

// el-upload 请求头
const authorization = computed(() => {
  return {
    Authorization: token_prefix + getToken()
  };
});

/**
 * 打开发布页面
 */
const openModel = (): void => {
  if (articleForm.value.articleTitle.trim() === '') {
    modal.msgError('文章标题不能为空');
    return;
  }
  if (articleForm.value.articleContent.trim() === '') {
    modal.msgError('文章内容不能为空');
    return;
  }
  // 避免再次打开弹窗后显示分类、标签不能为空
  articleFormRef.value?.clearValidate();
  articleApi.getCategoryOptions().then(({ data }) => {
    categoryList.value = data.data;
  });
  articleApi.getTagOptions().then(({ data }) => {
    tagList.value = data.data;
  });
  open.value = true;
};

/**
 * 移除分类选项
 */
const removeCategory = (): void => {
  articleForm.value.categoryName = '';
};

/**
 * 搜索分类选项
 * @param keywords 关键词
 * @param cb 回调函数
 */
const searchCategory = (keywords: string, cb: (arg: CategoryOption[]) => void): void => {
  const results = keywords
    ? categoryList.value.filter(category =>
      category.categoryName.indexOf(keywords) !== -1)
    : categoryList.value;
  console.log(results);
  cb(results);
};

/**
 * 回车保存分类
 */
const saveCategory = (): void => {
  if (categoryName.value.trim() != '') {
    addCategory(categoryName.value);
    categoryName.value = '';
  }
};

/**
 * 搜索框选择分类
 * @param category 分类选项
 */
const selectCategory = (category: CategoryOption | any): void => {
  addCategory(category.categoryName);
};

/**
 * 添加分类
 * @param categoryName 分类名称
 */
const addCategory = (categoryName: string): void => {
  articleForm.value.categoryName = categoryName;
};

/**
 * 移除标签选项
 */
const removeTag = (tagName: string): void => {
  const index = articleForm.value.tagNameList.indexOf(tagName);
  articleForm.value.tagNameList.splice(index, 1);
};

/**
 * 搜索标签选项
 * @param keywords 关键词
 * @param cb 回调函数
 */
const searchTag = (keywords: string, cb: any): void => {
  const results = keywords
    ? tagList.value.filter(tag =>
      tag.tagName.indexOf(keywords) !== -1)
    : tagList.value;
  cb(results);
};

/**
 * 回车保存标签
 */
const saveTag = (): void => {
  if (tagName.value.trim() != '') {
    addTag(tagName.value);
    tagName.value = '';
  }
};

/**
 * 搜索框选择标签
 * @param tag 标签
 */
const selectTag = (tag: TagOption | any): void => {
  addTag(tag.tagName);
};

/**
 * 添加标签
 * @param tagName 标签名称
 */
const addTag = (tagName: string): void => {
  if (articleForm.value.tagNameList.indexOf(tagName) === -1) {
    articleForm.value.tagNameList.push(tagName);
  }
};

/**
 * md编辑器内上传图片
 * @param event 未知
 * @param insertImage 图片回显
 * @param files 文件
 */
const uploadImage = (event: any, insertImage: any, files: Array<File>) => {
  const form = new FormData();
  form.append('file', files[0]);
  articleApi.uploadImages(form).then(({ data }) => {
    // 上传成功则调用insertImage回显图片
    insertImage({
      url: data.data
    });
  });
};

/**
 * 文章封面上传前处理
 * @param rawFile 图片
 */
const beforeUpload = (rawFile: UploadRawFile): Awaitable<boolean | void | File | Blob | null | undefined> => {
  return new Promise(resolve => {
    if (rawFile.size / 1024 / 1024 < 2) {
      resolve(rawFile);
    } else {
      // 若图片超过2MB，则将其压缩至2MB
      imageConversion
        .compressAccurately(rawFile, 2048)
        .then(res => {
          console.log('压缩前图片:', rawFile);
          console.log('压缩后图片:', res);
          resolve(res);
        });
    }
  });
};

/**
 * 文章封面上传成功后更新
 * @param response 响应数据
 */
const updateArticleCover = (response: AxiosResponse): void => {
  articleForm.value.articleCover = response.data;
};

/**
 * 提交表单
 */
const submitForm = () => {
  articleFormRef.value?.validate(valid => {
    if (valid) {
      articleApi.updateArticle(articleForm.value).then(() => {
        // 若是编辑文章，则编辑完成后返回
        if (articleForm.value.id !== undefined) {
          modal.notifySuccess('文章修改成功');
          tagStore.delView(({ path: `/article/write/${articleForm.value.id}` }));
          router.push({ path: '/article/list' });
          resetArticleForm();
        } else {
          modal.notifySuccess('文章发布成功');
        }
        open.value = false;
      });
    }
  });
};

/**
 * 重置表单
 */
const resetArticleForm = (showConfirm: boolean = false): void => {
  if (showConfirm) {
    modal.messageConfirm('确定要重置数据吗?这会清空所有已编辑的数据').then(() => {
      articleForm.value = {
        id: undefined,
        articleCover: '',
        articleTitle: articleTitle.value,
        articleContent: '',
        categoryName: '',
        tagNameList: [],
        articleType: 1,
        isTop: 0,
        isFeatured: 0,
        status: 1
      };
    });
  } else {
    articleForm.value = {
      id: undefined,
      articleCover: '',
      articleTitle: articleTitle.value,
      articleContent: '',
      categoryName: '',
      tagNameList: [],
      articleType: 1,
      isTop: 0,
      isFeatured: 0,
      status: 1
    };
  }
};

/**
 * 保存草稿
 */
const saveDraft = (): void => {
  articleForm.value.status = 3;
  articleApi.addArticle(articleForm.value).then(() => {
    modal.notifySuccess('文章保存成功');
    open.value = false;
  });
};

onMounted(() => {
  if (articleId) {
    if (isNaN(Number(articleId))) {
      modal.notifyError(`文章id格式有误:${articleId}`);
      return;
    }
    articleApi.editArticle(Number(articleId)).then(({ data }) => {
      articleForm.value = data.data;
      console.log(articleForm.value);
    }).catch(() => {
      tagStore.delView({ path: `/article/write/${articleId}` });
      router.push({ path: '/article/list' });
    });
  }
});
</script>

<style lang="scss" scoped>
.header-container {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;

  .el-button {
    width: 100px;
    margin-left: 10px;
  }
}

.md-container {
  min-height: 300px;
  height: calc(100vh - 180px);
}

.popover-title {
  margin-bottom: 1rem;
  text-align: center;
}

.popover-container {
  margin-top: 1rem;
  overflow-y: auto;

  .popover-item {
    margin-right: 1rem;
    margin-bottom: 1rem;
    cursor: pointer;


  }

  .popover-item-selected {
    cursor: not-allowed;
    color: #fff !important;
    background-color: #b6b6b6 !important;
  }
}
</style>
