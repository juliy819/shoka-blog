<!--
 * @description 导航栏
 * @author juliy
 * @date 2023-03-20 11:09
-->
<template>
  <div class="navbar">
    <Hamburger :is-active="appStore.sidebar.opened" class="hamburger-container" @toggle="toggleSidebar"></Hamburger>
    <Breadcrumb></Breadcrumb>
    <!-- 右侧菜单 -->
    <div class="right-menu">
      <template v-if="device !== 'mobile'">
        <!-- 博客首页 -->
        <el-tooltip content="博客首页">
          <home class="right-menu-item hover-effect"></home>
        </el-tooltip>

        <el-tooltip content="github">
          <github class="right-menu-item hover-effect"></github>
        </el-tooltip>

        <el-tooltip content="gitee">
          <gitee class="right-menu-item hover-effect"></gitee>
        </el-tooltip>
        <!-- 修改密码 -->
        <el-tooltip content="修改密码">
          <password class="right-menu-item hover-effect"></password>
        </el-tooltip>
        <!-- 全屏 -->
        <el-tooltip content="切换全屏">
          <screenfull class="right-menu-item hover-effect"></screenfull>
        </el-tooltip>
        <!-- 布局大小 -->
        <el-tooltip content="布局大小">
          <size-select class="right-menu-item hover-effect" />
        </el-tooltip>
      </template>
      <el-dropdown class="right-menu-item hover-effect avatar-container" trigger="click" @command="handleCommand">
        <!-- 头像 -->
        <div class="avatar-wrapper">
          <img :src="userStore.avatar" alt="" class="user-avatar" />
        </div>
        <!-- 选项 -->
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="setLayout">
              <span>布局设置</span>
            </el-dropdown-item>
            <el-dropdown-item command="logout" divided>
              <span>退出登录</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script lang="ts" setup>
import useStore from '@/store';
import { modal } from '@/utils/modal';

const { appStore, userStore } = useStore();
const device = computed(() => appStore.device);

const toggleSidebar = () => {
  appStore.toggleSidebar(false);
};

const handleCommand = (command: string) => {
  switch (command) {
    case 'setLayout' :
      setLayout();
      break;
    case 'logout' :
      logout();
      break;
    default:
      break;
  }
};

const emits = defineEmits(['setLayout']);
const setLayout = () => {
  emits('setLayout');
};

const logout = () => {
  modal.messageConfirm('确定注销并退出系统吗？').then(() => {
    userStore.logout().then(() => {
      location.href = '/login';
    });
  });
};
</script>

<style lang="scss" scoped>

.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: white;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 50px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;
    display: flex;

    .right-menu-item {
      display: inline-block;
      padding: 0 10px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }
  }

  .avatar-container {
    margin-right: 10px;

    .avatar-wrapper {
      margin-top: 7px;
      position: relative;

      .user-avatar {
        cursor: pointer;
        width: 36px;
        height: 36px;
        border-radius: 10px;
      }
    }
  }
}
</style>