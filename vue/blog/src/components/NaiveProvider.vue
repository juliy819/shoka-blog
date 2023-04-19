<!--
 * @description naive provider
 * @author juliy
 * @date 2023/4/4 11:14
-->
<template>
  <n-config-provider inline-theme-disabled :theme="setTheme as Theme<any>" :theme-overrides="themeOverrides">
    <n-dialog-provider>
      <n-notification-provider>
        <n-message-provider>
          <slot></slot>
          <naive-provider-content />
        </n-message-provider>
      </n-notification-provider>
    </n-dialog-provider>
  </n-config-provider>
</template>

<script setup lang="ts">
import type { GlobalThemeOverrides } from 'naive-ui';
import { darkTheme, useDialog, useMessage, useNotification } from 'naive-ui';
import { useDark } from '@vueuse/core';
import type { Theme } from 'naive-ui/es/_mixins';

const isDark = useDark();

const setTheme = computed(() => isDark.value ? darkTheme : null);

const themeOverrides: GlobalThemeOverrides = {
  common: {
    primaryColorHover: 'var(--primary-color)',
    primaryColorPressed: 'var(--primary-color-darker)'
  },
  Button: {
    textColorText: 'var(--text-color)'
  }
};

/**
 * 注册弹窗等组件
 */
const registerNaiveTools = () => {
  window.$dialog = useDialog();
  window.$message = useMessage();
  window.$notification = useNotification();
};

const NaiveProviderContent = defineComponent({
  name: 'NaiveProviderContent',
  setup() {
    registerNaiveTools();
  },
  render() {
    return h('div');
  }
});
</script>