<!--
 * @description 标签栏
 * @author juliy
 * @date 2023/3/21 15:18
-->
<template>
  <div class="tags-view-container">
    <scroll-pane class="tags-view-wrapper">
      <span v-for="tag in visitedViews" class="tags-view-item">
        <router-link
            :key="tag.path"
            :class="isActive(tag) ? 'active' : ''"
            :to="{ path: tag.path, query: tag.query }"
            class="tags-view-title"
            @contextmenu.prevent="openMenu(tag, $event)"
            @click.middle="handleMiddleClick(tag)">
          {{ tag.title }}
          <span
              v-if="!isAffix(tag)"
              class="close-icon"
              @click.prevent="closeSelectedTag(tag)">
          <svg-icon icon-class="close" size="0.9rem"/>
        </span>
        </router-link>
      </span>

    </scroll-pane>
    <ul
        v-show="menuVisible"
        :style="{ left: left + 'px', top: top + 'px' }"
        class="context-menu">
      <li>
        <el-icon class="menu-icon">
          <refresh-right/>
        </el-icon>
        刷新页面
      </li>
      <li v-if="!isAffix(selectedTag)" @click="closeSelectedTag(selectedTag)">
        <el-icon class="menu-icon">
          <close/>
        </el-icon>
        关闭当前
      </li>
      <li @click="closeOtherTags">
        <el-icon class="menu-icon">
          <circle-close/>
        </el-icon>
        关闭其它
      </li>
      <li v-if="!isFirstTag()" @click="closeLeftTags">
        <el-icon class="menu-icon">
          <back/>
        </el-icon>
        关闭左侧
      </li>
      <li v-if="!isLastTag()" @click="closeRightTags">
        <el-icon class="menu-icon">
          <right/>
        </el-icon>
        关闭右侧
      </li>
      <li @click="closeAllTags">
        <el-icon class="menu-icon">
          <circle-close/>
        </el-icon>
        全部关闭
      </li>
    </ul>
  </div>
</template>

<script lang="ts" setup>
import ScrollPane from '@/layout/components/TagsView/ScrollPane.vue';
import useStore from '@/store';
import type { TagsView } from '@/store/interface';
import { Back, CircleClose, Close, RefreshRight, Right } from '@element-plus/icons-vue';
import type { ComponentInternalInstance } from 'vue';
import { ref } from 'vue';
import type { RouteRecordRaw } from 'vue-router';

const route = useRoute();
const router = useRouter();
const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { tagStore, permissionStore } = useStore();
const menuVisible = ref(false);
const left = ref(0);
const top = ref(0);
const selectedTag = ref({});
const affixTags = ref<TagsView[]>([]);

const visitedViews = computed(() => tagStore.visitedViews as TagsView[]);
const routes = computed(() => permissionStore.routes);

/**
 * 打开右键菜单
 * @param tag 标签
 * @param e 点击事件
 */
const openMenu = (tag: TagsView, e: MouseEvent): void => {
  const menuMinWidth = 105;
  // 当前组件实例(即TagsView)到浏览器左侧的距离
  const offsetLeft = proxy?.$el.getBoundingClientRect().left;
  // TagsView的宽度 包括内边距和边框
  const offsetWidth = proxy?.$el.offsetWidth;
  // 最大偏移量，即菜单左边距的最大值，为TagsView的宽度减去菜单宽度
  const maxLeft = offsetWidth - menuMinWidth;

  // 点击位置距TagsView左侧的距离  15为菜单显示位置偏移量，即向右侧偏移15px
  const l = e.clientX - offsetLeft + 15;
  // 若点击位置超过了最大值，则以最大值作为菜单位置的左边距
  if (l > maxLeft) {
    left.value = maxLeft;
  } else {
    left.value = l;
  }

  top.value = e.clientY;
  menuVisible.value = true;
  selectedTag.value = tag;
};

/**
 * 关闭右键菜单
 */
const closeMenu = (): void => {
  menuVisible.value = false;
};

/**
 * 中键点击关闭标签
 * @param tag 标签
 */
const handleMiddleClick = (tag: TagsView) => {
  if (!isAffix(tag)) {
    closeSelectedTag(tag);
  }
};

/**
 * 是否为正在显示的标签页
 * @param tag 标签
 * @return 是:true 否:false
 */
const isActive = (tag: TagsView): boolean => {
  return tag.path === route.path;
};

/**
 * 是否为固定标签页
 * @param tag 标签
 * @return 是:true 否:false
 */
const isAffix = (tag: TagsView): boolean => {
  return !!tag?.meta?.affix;
};

/**
 * 是否为第一个标签页
 * @return 是:true 否:false
 */
const isFirstTag = (): boolean => {
  // 首次加载时selectedTag为undefined，会报错
  try {
    return (selectedTag.value as TagsView).path === '/index' ||
        (selectedTag.value as TagsView).fullPath === visitedViews.value[1].fullPath;
  } catch {
    return false;
  }
};

/**
 * 是否为最后一个标签页
 * @return 是:true 否:false
 */
const isLastTag = (): boolean => {
  // 首次加载时selectedTag为undefined，会报错
  try {
    return (selectedTag.value as TagsView).fullPath === visitedViews.value[visitedViews.value.length - 1].fullPath;
  } catch {
    return false;
  }
};

/**
 * 添加新标签
 */
const addTag = (): void => {
  if (route.name) {
    tagStore.addView(route);
  }
};

/**
 * 滚动到当前标签处
 */
const moveToCurrentTag = (): void => {
  nextTick(() => {
    for (const t of visitedViews.value) {
      if (t.path === route.path) {
        // todo 滚动功能待实现...
        // 若查询参数发生了变化，则进行更新
        if (t.fullPath !== route.fullPath) {
          tagStore.updateView(route);
        }
      }
    }
  });
};

/**
 * 关闭选中的标签
 * @param tag 标签
 */
const closeSelectedTag = (tag: TagsView): void => {
  tagStore.delView(tag).then(visitedViews => {
    if (isActive(tag)) {
      toLastView(visitedViews, tag);
    }
  });
};

/**
 * 关闭其它标签页
 */
const closeOtherTags = (): void => {
  const tag = selectedTag.value;
  router.push(tag);
  tagStore.delOtherViews(tag);
};

/**
 * 关闭左侧标签页
 */
const closeLeftTags = (): void => {
  tagStore.delLeftViews(selectedTag.value).then(visitedViews => {
    // 若关闭了当前浏览的标签页，则跳转至选中的标签页
    if (!visitedViews.find(tag => tag.fullPath === route.fullPath)) {
      router.push(selectedTag.value);
    }
  });
};

/**
 * 关闭右侧标签页
 * @param tag 标签
 */
const closeRightTags = (tag: TagsView): void => {
  tagStore.delRightViews(selectedTag.value).then(visitedViews => {
    // 若关闭了当前浏览的标签页，则跳转至选中的标签页
    if (!visitedViews.find(tag => tag.fullPath === route.fullPath)) {
      router.push(selectedTag.value);
    }
  });
};

/**
 * 关闭所有标签页
 */
const closeAllTags = (): void => {
  tagStore.delAllViews().then(visitedViews => {
    if (affixTags.value.some(tag => tag.path === route.path)) {
      return;
    }
    toLastView(visitedViews, selectedTag.value);
  });
};

/**
 * 前往上一个标签页
 * @param visitedViews 所有标签
 * @param tag 标签
 */
const toLastView = (visitedViews: TagsView[], tag?: TagsView): void => {
  const latestView = visitedViews.slice(-1)[0];
  if (latestView && latestView.fullPath) {
    router.push(latestView.fullPath);
  } else {
    // 若没有标签页，则重定向至首页
    // if (tag?.name === '首页') {
    //   router.replace({path: '/redirect' + tag.fullPath});
    // } else {
    //   router.push('/');
    // }
    console.error('123123123123123123123');
  }
};

/**
 * 初始化添加所有固定标签
 */
const initTags = (): void => {
  const visitedViews = filterAffixTags(routes.value);
  affixTags.value = visitedViews;
  for (const tag of visitedViews) {
    if (tag.name) {
      tagStore.addView(tag);
    }
  }
};

/**
 * 筛选出所有固定标签
 * @param routes 路由表
 * @param basePath 基础路径
 * @return 固定标签数组
 */
const filterAffixTags = (routes: RouteRecordRaw[], basePath = ''): TagsView[] => {
  let tags: TagsView[] = [];
  routes.forEach(route => {
    if (route.meta?.affix) {
      const tagPath = getNormalPath(basePath + '/' + route.path);
      tags.push({
        fullPath: tagPath,
        path: tagPath,
        name: route.name,
        meta: { ...route.meta }
      });
    }
    if (route.children) {
      const childTags = filterAffixTags(route.children, route.path);
      if (childTags.length >= 1) {
        tags = tags.concat(childTags);
      }
    }
  });
  return tags;
};

/**
 * 路径转化，将'//'替换为'/'，并去掉末尾的'/'
 * @param p 路径
 * @return 转化后的路径
 */
const getNormalPath = (p: string): string => {
  if (p.length === 0 || !p || p === 'undefined') {
    return p;
  }
  let res = p.replace('//', '/');
  if (res[res.length - 1] === '/') {
    return res.slice(0, res.length - 1);
  }
  return res;
};

// 只要路由发生变化，便更新标签状态
watch(
    route,
    () => {
      addTag();
      moveToCurrentTag();
    },
    {
      // 初始化立即执行
      immediate: true
    }
);

// 打开右键菜单后，点击任意位置隐藏菜单
watch(
    menuVisible,
    value => {
      if (value) {
        document.body.addEventListener('click', closeMenu);
      } else {
        document.body.removeEventListener('click', closeMenu);
      }
    }
);

// 页面加载时添加固定标签
onMounted(() => {
  initTags();
});
</script>

<style lang="scss" scoped>
.tags-view-container {
  height: 34px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);

  .tags-view-wrapper {
    .tags-view-item {
      &:first-child {
        margin-left: 15px;
      }

      &:last-child {
        margin-right: 15px;
      }

      .tags-view-title {
        display: inline-block;
        position: relative;
        cursor: pointer;
        height: 26px;
        line-height: 26px;
        margin-top: 4px;
        margin-right: 6px;
        padding: 0 8px;
        border: 1px solid #d8dce5;
        border-radius: 8px;
        color: #495060;
        background: #fff;
        font-size: 12px;

        &:hover {
          background-color: rgba(0, 0, 0, 0.12);
        }

        // 选中标签的样式
        &.active {
          background-color: var(--el-color-primary);
          color: #fff;
          border-color: var(--el-color-primary);
          // 在标题前画个小圆点
          &::before {
            content: "";
            background: #fff;
            display: inline-block;
            width: 8px;
            height: 8px;
            border-radius: 50%;
            position: relative;
            margin-right: 5px;
          }

          & + .tags-view-divider {
            visibility: hidden;
          }
        }
      }

      .tags-view-divider {
        margin: 0 0;
      }

      // 关闭图标
      .close-icon {
        margin-left: 2px;
        margin-right: -2px;
        border-radius: 50%;

        &:hover {
          background-color: #ccc;
          color: #fff;
        }
      }
    }
  }

  .context-menu {
    margin: 0;
    background-color: #fff;
    z-index: 3000;
    position: absolute;
    list-style-type: none;
    padding: 5px 0;
    font-size: 12px;
    color: #333;

    li {
      padding: 7px 16px;
      cursor: pointer;

      .menu-icon {
        vertical-align: -0.12em;
      }

      &:hover {
        background: #eee;
      }
    }
  }

}
</style>