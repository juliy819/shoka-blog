<!--
 * @description 菜单显示按钮
 * @author juliy
 * @date 2023/4/3 19:10
-->
<template>
  <div :class="classObj" @click="appStore.showSideDrawer()">
    <div class="lines">
      <span class="line" />
      <span class="line" />
      <span class="line" />
    </div>
  </div>
</template>

<script setup lang="ts">

import useStore from '@/stores';

const { appStore } = useStore();
const classObj = computed(() => appStore.sideDrawerOpen ? 'toggle close' : 'toggle');

</script>

<style scoped lang="scss">
.toggle {
  display: none;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  line-height: 0;
  cursor: pointer;

  .lines {
    width: 1.375rem;
    padding: 1.25rem;
    box-sizing: unset;
  }

  .line {
    display: inline-block;
    position: relative;
    top: 0;
    left: 0;
    width: 100%;
    height: 0.15rem;
    border-radius: 0.0625rem;
    background: var(--header-text-color);
    vertical-align: top;
    box-shadow: 0 0 0.5rem rgb(0 0 0 / 50%);
    transition: all 0.4s;

    &:not(:first-child) {
      margin-top: 0.2rem;
    }
  }
}

.toggle.close {
  .line:nth-child(1) {
    transform: rotate(45deg);
    top: 0.35rem;
  }

  .line:nth-child(2) {
    opacity: 0;
  }

  .line:nth-child(3) {
    transform: rotate(-45deg);
    top: -0.35rem;
  }
}

.show .line {
  background: var(--text-color);
  box-shadow: 0 0 0.0625rem var(--grey-9-a1);
  transition: background-color 0.4s ease-in-out 0s;
}

@media (max-width: 991px) {
  .toggle {
    display: flex;
  }
}

</style>