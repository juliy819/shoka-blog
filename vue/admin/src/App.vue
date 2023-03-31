<template>
  <router-view />
</template>

<script lang="ts" setup>

import { handleThemeStyle } from '@/utils/theme';
import useSettingStore from '@/store/modules/setting';

onMounted(() => {
  nextTick(() => {
    handleThemeStyle(useSettingStore().theme);
  });
});

</script>