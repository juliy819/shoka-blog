<!--
 * @description 表格分页工具
 * @author juliy
 * @date 2023/3/27 13:58
-->
<template>
  <div class="pagination-container">
    <el-pagination
        :background="background"
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :layout="layout"
        :page-sizes="pageSizes"
        :pager-count="pagerCount"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"/>
  </div>
</template>

<script setup lang='ts'>

const props = defineProps({
  total: {
    required: true,
    type: Number as PropType<number>,
    default: 0
  },
  page: {
    type: Number,
    default: 1
  },
  limit: {
    type: Number,
    default: 10
  },
  pageSizes: {
    type: Array as PropType<number[]>,
    default() {
      return [10, 20];
    }
  },
  // 移动端页码按钮的数量端默认值5
  pagerCount: {
    type: Number,
    default: document.body.clientWidth < 992 ? 5 : 7
  },
  layout: {
    type: String,
    default: 'total, sizes, prev, pager, next, jumper'
  },
  background: {
    type: Boolean,
    default: true
  }
});

const emit = defineEmits(['update:page', 'update:limit', 'pagination']);

const currentPage = computed<number>({
  get: () => props.page,
  set: value => {
    emit('update:page', value);
  }
});

const pageSize = computed<number>({
  get: () => props.limit,
  set: value => {
    emit('update:limit', value);
  }
});

const handleSizeChange = (value: number): void => {
  emit('pagination', { page: currentPage, limit: value });
};

const handleCurrentChange = (value: number): void => {
  currentPage.value = value;
  emit('pagination', { page: value, limit: props.limit });
};
</script>