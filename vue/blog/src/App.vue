<template>
  <layout />
</template>

<script lang="ts" setup>
import Layout from '@/layout';
import useStore from '@/stores';
import blogApi from '@/api/blog';

const { blogStore } = useStore();

onMounted(() => {
  console.log(
    '%c Shoka-Blog %c By juliy %c',
    'background:#e9546b ; padding: 1px; border-radius: 3px 0 0 3px;  color: #fff; padding:5px 0;',
    'background:#ec8c69 ; padding: 1px; border-radius: 0 3px 3px 0;  color: #000; padding:5px 0;',
    'background:transparent'
  );

  blogApi.getBlogInfo().then(({ data }) => {
    blogStore.setBlogInfo(data.data);
  }).catch(() => {});

  blogApi.report().catch(() => {});
});

</script>

