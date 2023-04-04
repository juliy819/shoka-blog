<!--
 * @description naive provider
 * @author juliy
 * @date 2023/4/4 11:14
-->
<template>
  <n-dialog-provider>
    <n-notification-provider>
      <n-message-provider>
        <slot></slot>
        <naive-provider-content />
      </n-message-provider>
    </n-notification-provider>
  </n-dialog-provider>
</template>

<script setup lang="ts">
import { useDialog, useMessage, useNotification } from 'naive-ui';

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