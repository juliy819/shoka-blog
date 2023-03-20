<!--
 * @description 设置页面布局大小
 * @author JuLiy
 * @date 2023/3/20 20:38
-->
<template>
  <el-dropdown trigger="click" @command="handleSetSize">
    <div class="size-select-icon">
      <svg-icon icon-class="size" size="1.1rem" />
    </div>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item v-for="item of sizeOptions" :key="item.value"
                          :disabled="size === item.value" :command="item.value">
          {{ item.label }}
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script lang="ts" setup>

import useStore from '@/store';
import { loading } from '@/utils/modal';

const { appStore } = useStore();
const size = computed(() => appStore.size);

const sizeOptions = ref<{ label: string, value: string }[]>([
  { label: '默认', value: 'default' },
  { label: '大型', value: 'large' },
  { label: '小型', value: 'small' }
]);

const handleSetSize = (size: string) => {
  loading('正在设置布局大小，请稍候...');
  appStore.setSize(size);
  setTimeout('window.location.reload()', 2000);
};

</script>

<style lang="scss" scoped>

.size-select-icon {
  line-height: 50px;
}
</style>