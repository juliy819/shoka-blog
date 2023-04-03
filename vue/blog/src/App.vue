<template>
  <layout />
</template>

<script lang="ts" setup>
import Layout from '@/layout';
import blogApi from '@/api/blog';
import useStore from '@/stores';
import type { BlogInfo, SiteConfig } from '@/api/blog/types';

const { blogStore } = useStore();

onMounted(() => {
  blogApi.getBlogInfo().then(({ data }) => {
    blogStore.setBlogInfo(data.data);
  }).catch(() => {
    initBlogInfo();
  });
});

const initBlogInfo = () => {
  const siteConfig: SiteConfig = {
    aboutMe: '',
    authorAvatar: '',
    bilibili: '',
    commentCheck: 0,
    createSiteTime: '2023',
    emailNotice: 0,
    gitee: '',
    github: '',
    id: 0,
    loginList: '',
    messageCheck: 0,
    qq: '',
    recordNumber: '苏ICP备XXXXXXXXXXX号-1',
    siteAddress: '',
    siteAuthor: 'juliy',
    siteIntro: '',
    siteName: 'shoka-blog',
    siteNotice: '',
    socialList: '',
    touristAvatar: '',
    userAvatar: ''
  };

  const blogInfo: BlogInfo = {
    articleCount: 0,
    categoryCount: 0,
    siteConfig: siteConfig,
    tagCount: 0,
    viewCount: 0
  };

  blogStore.setBlogInfo(blogInfo);
};

</script>

