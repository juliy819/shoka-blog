<!--
 * @description 首页侧边栏作者信息
 * @author juliy
 * @date 2023/4/5 17:04
-->
<template>
  <div class="side-card">
    <!-- 作者信息 -->
    <div class="author-info" v-if="blogStore.siteConfig.id">
      <n-avatar class="author-avatar" round :size="150" :src="blogStore.siteConfig.authorAvatar" />
      <p class="author-name">{{ blogStore.siteConfig.siteAuthor }}</p>
      <div class="site-intro">{{ blogStore.siteConfig.siteIntro }}</div>
    </div>
    <!-- 作者信息骨架屏 -->
    <div class="author-info" v-else>
      <n-skeleton class="author-avatar" circle width="150px" />
      <n-skeleton class="author-name" round width="70px" />
      <n-skeleton class="site-intro" round width="100px" />
    </div>

    <!-- 博客信息 -->
    <div class="blog-info" v-if="blogStore.siteConfig.id">
      <!-- 文章 -->
      <div class="blog-item">
        <router-link to="/archive">
          <div class="count">{{ blogStore.articleCount }}</div>
          <div class="name">文章</div>
        </router-link>
      </div>
      <!-- 分类 -->
      <div class="blog-item">
        <router-link to="/category">
          <div class="count">{{ blogStore.categoryCount }}</div>
          <div class="name">分类</div>
        </router-link>
      </div>
      <!-- 标签 -->
      <div class="blog-item">
        <router-link to="/tag">
          <div class="count">{{ blogStore.tagCount }}</div>
          <div class="name">标签</div>
        </router-link>
      </div>
    </div>
    <!-- 博客信息骨架屏 -->
    <div class="blog-info" v-else>
      <!-- 文章 -->
      <div class="blog-item">
        <n-skeleton class="mb5" round width="30px" />
        <n-skeleton class="mb5" round width="30px" />
      </div>
      <!-- 分类 -->
      <div class="blog-item">
        <n-skeleton class="mb5" round width="30px" />
        <n-skeleton class="mb5" round width="30px" />
      </div>
      <!-- 标签 -->
      <div class="blog-item">
        <n-skeleton class="mb5" round width="30px" />
        <n-skeleton class="mb5" round width="30px" />
      </div>
    </div>

    <!-- 社交信息 -->
    <div class="social-info" v-if="blogStore.siteConfig.id">
      <n-popover trigger="hover" placement="bottom">
        <template #trigger>
          <a v-if="showSocial('github')" target="_blank" :href="blogStore.siteConfig.github" class="social-item">
            <svg-icon icon-class="github" size="1.4rem"></svg-icon>
          </a>
        </template>
        github
      </n-popover>
      <n-popover trigger="hover" placement="bottom">
        <template #trigger>
          <a v-if="showSocial('gitee')" target="_blank" :href="blogStore.siteConfig.gitee" class="social-item">
            <svg-icon icon-class="gitee" size="1.4rem"></svg-icon>
          </a>
        </template>
        gitee
      </n-popover>
      <n-popover trigger="hover" placement="bottom">
        <template #trigger>
          <a v-if="showSocial('bilibili')" target="_blank" :href="blogStore.siteConfig.bilibili" class="social-item">
            <svg-icon icon-class="bilibili" size="1.4rem"></svg-icon>
          </a>
        </template>
        bilibili
      </n-popover>
      <n-popover trigger="hover" placement="bottom">
        <template #trigger>
          <a v-if="showSocial('qq')" target="_blank"
             :href="'https://wpa.qq.com/msgrd?v=3&uin=' + blogStore.siteConfig.qq + '&site=qq&menu=yes'"
             class="social-item">
            <svg-icon icon-class="qq" size="1.4rem" color="#00a1d6"></svg-icon>
          </a>
        </template>
        qq
      </n-popover>
    </div>
    <!-- 社交信息骨架屏 -->
    <div class="social-info" v-else>
      <n-skeleton circle size="small" class="social-item" />
      <n-skeleton circle size="small" class="social-item" />
      <n-skeleton circle size="small" class="social-item" />
      <n-skeleton circle size="small" class="social-item" />
    </div>
  </div>
</template>

<script setup lang="ts">

import useStore from '@/stores';

const { blogStore } = useStore();
const showSocial = computed(() => (social: string) => blogStore.siteConfig.socialList.includes(social));

</script>

<style scoped lang="scss">
@use '@/assets/styles/mixin';

.author-info {
  @include mixin.flex;
  flex-direction: column;

  .author-avatar {
    box-shadow: 0 0 2rem 0.2rem var(--body-bg-shadow);
    border: 0.1rem solid var(--grey-0);
    animation: 0.5s ease-in-out 0ms 1 normal forwards running blur;
    -webkit-animation: 0.5s ease-in-out 0ms 1 normal forwards running blur;

    &:hover {
      animation: 1s ease 0ms 1 normal none running author-shake;
      -webkit-animation: 1s ease 0ms 1 normal none running author-shake;
    }
  }

  .author-name {
    margin-top: 0.5rem;
    font-weight: 800;
    font-size: 1.4rem;
    text-align: center;
    color: var(--grey-7);

    &:hover {
      animation: rubberBand 1s ease-in-out;
    }

  }

  .site-intro {
    margin-top: 0.5rem;
    font-size: 1em;
    text-align: center;
    color: var(--grey-5);

    &:hover {
      animation: jello 1s ease-in-out;
    }
  }
}

.blog-info {
  display: flex;
  justify-content: center;
  margin-top: 0.8rem;
  line-height: 1.4;
  text-align: center;

  .blog-item {
    color: var(--grey-6);
    padding: 0 0.7rem;
    transition: color 0.3s ease-in-out;

    &:hover {
      color: var(--primary-color);
    }

    &:not(:first-child) {
      border-left: 0.0625rem solid var(--grey-4);
    }
  }
}

.social-info {
  margin-top: 1rem;
  text-align: center;

  .social-item {
    display: inline-block;
    width: 1.875rem;
    height: 1.875rem;
    margin: 0 0.25rem;
    text-align: center;
    transition: scale 0.5s;

    &:hover {
      scale: 1.5 1.5;
    }
  }
}


</style>