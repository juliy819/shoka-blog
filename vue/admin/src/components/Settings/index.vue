<!--
 * @description 设置页
 * @author juliy
 * @date 2023/3/22 21:04
-->
<template>
  <el-drawer v-model="showSettings" :with-header="false" direction="rtl" size="300px">
    <h3 class="drawer-title">主题风格设置</h3>
    <div class="theme-checkbox">
      <div class="theme-checkbox-item" @click="changeSideTheme('theme-dark')">
        <img alt="dark" src="@/assets/images/dark.svg" />
        <div v-if="sideTheme === 'theme-dark'" class="theme-checkbox-selected-icon">
          <el-icon><Select /></el-icon>
        </div>
      </div>
      <div class="theme-checkbox-item" @click="changeSideTheme('theme-light')">
        <img alt="light" src="@/assets/images/light.svg" />
        <div v-if="sideTheme === 'theme-light'" class="theme-checkbox-selected-icon">
          <el-icon><Select /></el-icon>
        </div>
      </div>
    </div>
    <div class="drawer-item">
      <span>主题颜色</span>
      <span class="comp-style">
        <el-color-picker v-model="theme" :predefine="predefineColors" @change="changeTheme" />
      </span>
    </div>

    <el-divider />

    <h3 class="drawer-title">系统布局配置</h3>
    <div class="drawer-item">
      <span>开启 Tags-View</span>
      <span class="comp-style">
        <el-switch v-model="tagsView" class="drawer-switch" />
      </span>
    </div>
    <div class="drawer-item">
      <span>固定 Header</span>
      <span class="comp-style">
        <el-switch v-model="fixedHeader" class="drawer-switch" />
      </span>
    </div>
    <div class="drawer-item">
      <span>显示 Logo</span>
      <span class="comp-style">
        <el-switch v-model="sidebarLogo" class="drawer-switch" />
      </span>
    </div>
    <div class="drawer-item">
      <span>动态标题</span>
      <span class="comp-style">
        <el-switch v-model="dynamicTitle" class="drawer-switch" />
      </span>
    </div>

    <el-divider />

    <el-button icon="DocumentAdd" plain type="primary" @click="saveSetting">
      保存设置
    </el-button>
    <el-button icon="Refresh" plain @click="resetSetting">重置配置</el-button>
  </el-drawer>
</template>

<script lang="ts" setup>
import useStore from '@/store';
import { computed, ref } from 'vue';
import { useDynamicTitle } from '@/utils/dynamicTitle';
import { modal } from '@/utils/modal';
import { handleThemeStyle } from '@/utils/theme';

const { settingStore } = useStore();
const theme = ref(settingStore.theme);
const sideTheme = ref(settingStore.sideTheme);
const showSettings = ref(false);
const predefineColors = ref(['#409EFF', '#ff4500', '#ff8c00', '#ffd700', '#90ee90', '#00ced1', '#1e90ff', '#c71585']);

const storeSetting = computed(() => settingStore);
const tagsView = computed({
  get: () => storeSetting.value.tagsView,
  set: (val) => settingStore.changeSetting({ key: 'tagsView', value: val })
});
const fixedHeader = computed({
  get: () => storeSetting.value.fixedHeader,
  set: (val) => {
    settingStore.changeSetting({ key: 'fixedHeader', value: val });
  }
});
const sidebarLogo = computed({
  get: () => storeSetting.value.sidebarLogo,
  set: (val) => {
    settingStore.changeSetting({ key: 'sidebarLogo', value: val });
  }
});
const dynamicTitle = computed({
  get: () => storeSetting.value.dynamicTitle,
  set: (val) => {
    settingStore.changeSetting({ key: 'dynamicTitle', value: val });
    useDynamicTitle();
  }
});

/**
 * 设置侧边栏主题
 * @param themeColor 主题颜色
 */
const changeSideTheme = (themeColor: 'theme-dark' | 'theme-light'): void => {
  settingStore.changeSetting({ key: 'sideTheme', value: themeColor });
  sideTheme.value = themeColor;
};

const el = ref(null);

/**
 * 设置整体主题
 * @param themeColor 主题颜色
 */
const changeTheme = (themeColor: string | null) => {
  if (themeColor !== null) {
    settingStore.changeSetting({ key: 'theme', value: themeColor });
    theme.value = themeColor;
    handleThemeStyle(themeColor);
  }
};

/**
 * 保存设置
 */
const saveSetting = (): void => {
  modal.loading('正在保存设置到本地，请稍后...');
  let layoutSetting = {
    'tagsView': storeSetting.value.tagsView,
    'fixedHeader': storeSetting.value.fixedHeader,
    'sidebarLogo': storeSetting.value.sidebarLogo,
    'dynamicTitle': storeSetting.value.dynamicTitle,
    'sideTheme': storeSetting.value.sideTheme,
    'theme': storeSetting.value.theme
  };
  localStorage.setItem('layout-setting', JSON.stringify(layoutSetting));
  setTimeout(modal.closeLoading, 1000);
};

/**
 * 重置设置项
 */
const resetSetting = (): void => {
  modal.loading('正在清除设置缓存并刷新，请稍候...');
  localStorage.removeItem('layout-setting');
  setTimeout('window.location.reload()', 1000);
};

/**
 * 打开设置窗口
 */
const openSettings = (): void => {
  showSettings.value = true;
};

defineExpose({
  openSettings
});
</script>

<style lang="scss" scoped>
.drawer-title {
  margin-bottom: 12px;
  line-height: 22px;
  font-weight: bold;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
}

.theme-checkbox {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-top: 10px;
  margin-bottom: 20px;

  .theme-checkbox-item {
    position: relative;
    height: 56px;
    margin-right: 16px;
    border-radius: 2px;
    cursor: pointer;

    img {
      width: 65px;
      height: 56px;
      border-radius: 5px;
    }

    .theme-checkbox-selected-icon {
      position: absolute;
      top: 25px;
      left: 35px;
      color: #1890ff;
    }
  }
}

.drawer-item {
  color: rgba(0, 0, 0, 0.65);
  padding: 12px 0;
  font-size: 14px;

  .comp-style {
    float: right;
    margin: -3px 8px 0px 0px;
  }
}
</style>
