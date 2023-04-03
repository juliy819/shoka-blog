<!--
 * @description 顶部导航栏
 * @author juliy
 * @date 2023/4/2 22:02
-->
<template>
  <div class="menu">
    <!-- 网站标题 -->
    <div class="menu-item title">
      <router-link class="menu-btn" to="/">
        {{ blogStore.siteConfig.siteName }}
      </router-link>
    </div>
    <div v-for="(menuItem, index) in menuItems" :key="index" class="menu-item">
      <!-- 一级菜单项 -->
      <router-link v-if="menuItem.children === undefined" :to="menuItem.path" class="menu-btn">
        <svg-icon :icon-class="menuItem.icon" />
        {{ menuItem.title }}
      </router-link>
      <!-- 多级菜单项 -->
      <div v-else class="sub-menu">
        <a class="sub-menu-title">
          <svg-icon :icon-class="menuItem.icon" />
          {{ menuItem.title }}
        </a>
        <!-- 下拉列表项 -->
        <ul class="sub-menu-dropdown">
          <li v-for="(subItem, index) in menuItem.children as MenuItem[]" :key="index"
              :class="{ active: route.path === subItem.path }" class="sub-menu-item">
            <router-link :to="subItem.path" class="sub-menu-link">
              <svg-icon :icon-class="subItem.icon" />
              {{ subItem.title }}
            </router-link>
          </li>
        </ul>
      </div>
    </div>
    <!-- 登录 -->
    <div class="menu-item sub-menu">
      <a v-if="!userStore.id" class="menu-btn" @click="showLoginDialog = true">
        <svg-icon icon-class="user" />
        登录
      </a>
      <template v-else>
        <el-avatar class="avatar" :src="userStore.avatar ? userStore.avatar : defAva" :size="30">
          user
        </el-avatar>
        <ul class="sub-menu-dropdown">
          <li class="sub-menu-item" :class="{ active: route.path === '/user' }">
            <router-link to="/user" class="sub-menu-link">
              <svg-icon icon-class="author"></svg-icon>
              个人中心
            </router-link>
          </li>
          <li class="sub-menu-item">
            <a class="sub-menu-link" @click="logout">
              <svg-icon icon-class="logout" />
              退出
            </a>
          </li>
        </ul>
      </template>
    </div>
  </div>

  <el-dialog v-model="showLoginDialog" append-to-body>

  </el-dialog>
</template>

<script lang="ts" setup>
import useStore from '@/stores';
import defAva from '@/assets/images/defAva.png';

const route = useRoute();
const router = useRouter();
const { blogStore, userStore } = useStore();
const showLoginDialog = ref(false);

interface MenuItem {
  title: string,
  path: string | undefined,
  icon: string,
  children?: MenuItem[]
}

const menuItems: MenuItem[] = [
  {
    title: '首页',
    path: '/',
    icon: 'home'
  },
  {
    title: '文章',
    path: undefined,
    icon: 'article',
    children: [
      {
        title: '归档',
        path: '/archive',
        icon: 'archive'
      },
      {
        title: '分类',
        path: '/category',
        icon: 'category'
      },
      {
        title: '标签',
        path: '/tag',
        icon: 'tag'
      }
    ]
  },
  {
    title: '说说',
    path: '/talk',
    icon: 'talk'
  },
  {
    title: '留言板',
    path: '/message',
    icon: 'message'
  },
  {
    title: '关于',
    path: '/about',
    icon: 'plane'
  }
];

const logout = () => {

};


</script>

<style lang="scss" scoped>
.avatar {
  display: flex;
}


.menu {
  display: flex;
  align-items: center;
  height: 100%;
}

.menu-item {
  position: relative;
  display: inline-block;
  padding: 0 0.625rem;
  letter-spacing: 0.0625rem;
  font-size: 1.1rem;
  text-align: center;

  // 下划线
  &:not(.title) .menu-btn::before {
    content: '';
    position: absolute;
    width: 0;
    height: 0.1875rem;
    bottom: 0;
    border-radius: 0.125rem;
    left: 50%;
    transform: translateX(-50%);
    background-color: currentColor;
    transition: all 0.4s ease-in-out 0s;
  }

  &:hover .sub-menu-dropdown {
    display: block;

    .sub-menu-item {
      animation: showList 0.5s ease-in-out forwards;
    }
  }
}

.menu-item.active .menu-btn::before,
.menu-item:hover .menu-btn::before {
  width: 70%;
}

.sub-menu-dropdown {
  display: none;
  position: absolute;
  width: max-content;
  margin-top: 0.5rem;
  padding: 0;
  background: var(--grey-9-a5);
  box-shadow: 0 0.3125rem 1.25rem -0.25rem var(--grey-9-a1);
  border-radius: 0.625rem 0;

  transform-origin: top;
  animation: slideDown 0.3s;

  // 在弹出框上边加个矩形，延长hover触发区域
  &::before {
    position: absolute;
    top: -1.25rem;
    left: 0;
    width: 100%;
    height: 2.5rem;
    content: "";
  }
}

// 子菜单向下展开动画
@keyframes slideDown {
  0% {
    transform: scale(1, 0);
  }
  100% {
    transform: scale(1, 1);
  }
}

// 菜单项向下位移动画
@keyframes showList {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: none;
  }
}


.sub-menu-item {
  display: block;
  font-size: 1rem;

  &:first-child {
    border-radius: 0.625rem 0 0 0;
  }

  &:last-child {
    border-radius: 0 0 0.625rem 0;
  }

  .sub-menu-link {
    padding: 0.3rem 0.7rem;
    display: inline-block;
    text-shadow: none;
  }

  &:hover .sub-menu-link {
    transform: translateX(0.3rem);
  }
}

.sub-menu .sub-menu-item:hover,
.sub-menu .sub-menu-item.active {
  background-image: linear-gradient(to right, var(--color-pink) 0, var(--color-orange) 100%);
}

.show .sub-menu-dropdown {
  background-color: var(--grey-1);
}

.sub-menu-title::after {
  content: "";
  display: inline-block;
  vertical-align: middle;
  border: 0.3rem solid transparent;
  border-top-color: currentColor;
  border-bottom: 0;
}

@media (max-width: 865px) {
  .menu {
    justify-content: center;
  }

  .menu .menu-item {
    display: none;
  }

  .menu .title {
    display: block;
  }
}

</style>