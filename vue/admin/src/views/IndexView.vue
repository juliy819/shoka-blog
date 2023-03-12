<template>
  <header>
    <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="125"
         height="125" />

    <div class="wrapper">
      <HelloWorld msg="You did it!" />
    </div>
  </header>

  <div class="div-btn">
    <el-button @click="logout">注销</el-button>
  </div>

</template>

<script setup lang="ts">
import { messageConfirm } from "@/utils/modal";
import useStore from "@/stores";

const { user } = useStore();

const logout = () => {
  messageConfirm("确定注销并退出系统吗？").then(() => {
    user.Logout().then(() => {
      location.href = "/login";
    });
  }).catch(() => {
  });
};
</script>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

.div-btn {
  display: flex;
  justify-content: center;
  align-items: center;
}

/*@media (min-width: 1024px) {*/
/*  header {*/
/*    display: flex;*/
/*    place-items: center;*/
/*  }*/

/*  .logo {*/
/*    margin: 0 2rem 0 0;*/
/*  }*/

/*  header .wrapper {*/
/*    display: flex;*/
/*    place-items: flex-start;*/
/*    flex-wrap: wrap;*/
/*  }*/
/*}*/
</style>