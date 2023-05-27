<!--
 * @description 首页侧边栏作者信息
 * @author juliy
 * @date 2023/4/5 17:04
-->
<template>
  <div :class="card ? 'side-card' : ''">
    <load-viewer :status="blogStore.status" failed-msg="作者信息加载失败">
      <template #data>
        <div class="author-info">
          <n-avatar class="author-avatar" round :size="150" :src="blogStore.siteConfig.authorAvatar" />
          <p class="author-name">{{ blogStore.siteConfig.siteAuthor }}</p>
          <div class="site-intro">{{ blogStore.siteConfig.siteIntro }}</div>
        </div>
        <div class="blog-info">
          <div class="blog-item">
            <router-link to="/archive">
              <div class="count">{{ blogStore.articleCount }}</div>
              <div class="name">文章</div>
            </router-link>
          </div>
          <div class="blog-item">
            <router-link to="/category">
              <div class="count">{{ blogStore.categoryCount }}</div>
              <div class="name">分类</div>
            </router-link>
          </div>
          <div class="blog-item">
            <router-link to="/tag">
              <div class="count">{{ blogStore.tagCount }}</div>
              <div class="name">标签</div>
            </router-link>
          </div>
        </div>
        <div class="social-info">
          <n-popover trigger="hover" placement="bottom" v-if="showSocial('github')">
            <template #trigger>
              <a target="_blank" :href="blogStore.siteConfig.github" class="social-item">
                <svg-icon icon-class="github" size="1.4rem"></svg-icon>
              </a>
            </template>
            github
          </n-popover>
          <n-popover trigger="hover" placement="bottom" v-if="showSocial('gitee')">
            <template #trigger>
              <a target="_blank" :href="blogStore.siteConfig.gitee" class="social-item">
                <svg-icon icon-class="gitee" size="1.4rem"></svg-icon>
              </a>
            </template>
            gitee
          </n-popover>
          <n-popover trigger="hover" placement="bottom" v-if="showSocial('bilibili')">
            <template #trigger>
              <a target="_blank" :href="blogStore.siteConfig.bilibili" class="social-item">
                <svg-icon icon-class="bilibili" size="1.4rem"></svg-icon>
              </a>
            </template>
            bilibili
          </n-popover>
          <n-popover trigger="hover" placement="bottom" v-if="showSocial('qq')">
            <template #trigger>
              <a target="_blank"
                 :href="'https://wpa.qq.com/msgrd?v=3&uin=' + blogStore.siteConfig.qq + '&site=qq&menu=yes'"
                 class="social-item">
                <svg-icon icon-class="qq" size="1.4rem" color="#00a1d6"></svg-icon>
              </a>
            </template>
            qq
          </n-popover>
        </div>
      </template>
      <template #loading>
        <div class="author-info">
          <n-skeleton class="author-avatar" circle width="150px" />
          <n-skeleton class="author-name" round width="70px" />
          <n-skeleton class="site-intro" round width="100px" />
        </div>
        <div class="blog-info">
          <div class="blog-item">
            <n-skeleton class="mb5" round width="30px" />
            <n-skeleton class="mb5" round width="30px" />
          </div>
          <div class="blog-item">
            <n-skeleton class="mb5" round width="30px" />
            <n-skeleton class="mb5" round width="30px" />
          </div>
          <div class="blog-item">
            <n-skeleton class="mb5" round width="30px" />
            <n-skeleton class="mb5" round width="30px" />
          </div>
        </div>
        <div class="social-info">
          <n-skeleton circle size="small" class="social-item" />
          <n-skeleton circle size="small" class="social-item" />
          <n-skeleton circle size="small" class="social-item" />
          <n-skeleton circle size="small" class="social-item" />
        </div>
      </template>
    </load-viewer>
  </div>
</template>

<script setup lang="ts">
import useStore from '@/stores';

const { blogStore } = useStore();
const showSocial = computed(() => (social: string) => blogStore.siteConfig.socialList.includes(social));

defineProps({
  card: {
    type: Boolean,
    default: true
  }
});

</script>

<style scoped lang="scss">
@use '@/assets/styles/mixin';

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