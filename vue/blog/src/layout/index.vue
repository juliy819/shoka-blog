<!--
 * @description 布局
 * @author juliy
 * @date 2023/4/1 16:46
-->
<template>
  <naive-provider>
    <div class="app-wrapper">
      <the-header />
      <app-main />
      <the-footer />
      <!-- 其它 -->
      <side-drawer />
      <login-dialog />
      <register-dialog />
      <email-dialog />
      <password-dialog />
      <affix-tools />
    </div>
  </naive-provider>
</template>

<script setup lang="ts">
import TheHeader from '@/layout/components/TheHeader';
import AppMain from '@/layout/components/AppMain.vue';
import TheFooter from '@/layout/components/TheFooter.vue';
import SideDrawer from '@/layout/components/SideDrawer.vue';
import RegisterDialog from '@/components/dialog/RegisterDialog.vue';
import EmailDialog from '@/components/dialog/EmailDialog.vue';
import PasswordDialog from '@/components/dialog/PasswordDialog.vue';
</script>

<style scoped lang="scss">
.app-wrapper {
  position: relative;
  min-height: 100vh;
}
</style>