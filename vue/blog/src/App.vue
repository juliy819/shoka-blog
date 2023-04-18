<template>
  <layout />
</template>

<script lang="ts" setup>
import Layout from '@/layout';
import useStore from '@/stores';
import blogApi from '@/api/blog';

const { blogStore } = useStore();

onMounted(() => {
  blogApi.getBlogInfo().then(({ data }) => {
    blogStore.setBlogInfo(data.data);
  }).catch(() => {
  });
});

</script>

