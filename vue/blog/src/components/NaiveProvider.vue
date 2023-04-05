<!--
 * @description naive provider
 * @author juliy
 * @date 2023/4/4 11:14
-->
<template>
  <n-config-provider inline-theme-disabled :theme-overrides="themeOverrides">
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
import { useDialog, useMessage, useNotification } from 'naive-ui';

const themeOverrides: GlobalThemeOverrides = {
  common: {
    primaryColorHover: 'var(--primary-color)'
  },
  Button: {
    textColorText: 'currentColor',
    textColorTextHover: 'var(--primary-color)',
    textColorTextPressed: 'var(--primary-color-darker)'
  },
  Drawer: {
    color: 'currentColor'
  }
};

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