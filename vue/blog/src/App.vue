<template>
  <layout />
</template>

<script lang="ts" setup>
import Layout from '@/layout';
import useStore from '@/stores';
import type { BlogInfo, SiteConfig } from '@/api/blog/types';
import blogApi from '@/api/blog';

const { blogStore } = useStore();

onMounted(() => {
  blogApi.getBlogInfo().then(({ data }) => {
    blogStore.setBlogInfo(data.data);
  }).catch(error => {
    setTimeout(initBlogInfo, 1000);
  });
});

const initBlogInfo = () => {
  const siteConfig: SiteConfig = {
    aboutMe: '',
    authorAvatar: 'http://static.juliy.top/site-imgs/author-avatar.jpg',
    bilibili: '',
    commentCheck: 0,
    createSiteTime: '2023',
    emailNotice: 0,
    gitee: '',
    github: '',
    id: 1,
    loginList: '',
    messageCheck: 0,
    qq: '',
    recordNumber: '苏ICP备XXXXXXXXXXX号-1',
    siteAddress: '',
    siteAuthor: 'juliy',
    siteIntro: '(●ˇ∀ˇ●) 哎嘿~',
    siteName: 'shoka-blog',
    siteNotice: '这是一条测试公告<br/>可以显示html内容<br/>如:<b>加粗</b>,<span style="color: red">变色</span>,' +
      '或者是<a class="underline-a" href="https://bing.com">超链接</a>等',
    socialList: 'gitee,github,qq,bilibili',
    touristAvatar: 'http://static.juliy.top/site-imgs/def-avatar.png',
    userAvatar: 'http://static.juliy.top/site-imgs/def-avatar.png'
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

