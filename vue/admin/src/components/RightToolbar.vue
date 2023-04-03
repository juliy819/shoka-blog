<!--
 * @description 右侧工具栏
 * @author juliy
 * @date 2023/3/25 15:32
-->
<template>
  <div class="top-right-btn" style="margin-right: 5px">
    <el-row>
      <el-tooltip v-if="search" class="item" placement="top"
                  :content="showSearch ? '隐藏搜索' : '显示搜索'">
        <el-button circle icon="Search" @click="toggleSearch"/>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="刷新" placement="top">
        <el-button circle icon="Refresh" @click="refresh()"/>
      </el-tooltip>
    </el-row>
  </div>
</template>

<script setup lang='ts'>

const props = defineProps({
  showSearch: {
    type: Boolean,
    default: true
  },
  search: {
    type: Boolean,
    default: true
  }
});

const emits = defineEmits(['update:showSearch', 'queryTable']);

// 显隐数据
const value = ref([]);

const toggleSearch = (): void => {
  emits('update:showSearch', !props.showSearch);
};

const refresh = (): void => {
  emits('queryTable');
};
</script>