<!--
 * @description 分类页面
 * @author juliy
 * @date 2023/4/1 16:03
-->
<template>
  <div class="page-header">
    <h1 class="page-title">分类</h1>
    <img class="page-cover" src="http://static.juliy.top/site-imgs/def-bg.png" alt="">
    <waves />
  </div>
  <div class="bg">
    <div class="page-container">
      <load-viewer :status="status" no-data-msg="暂时还没有分类哦~" failed-msg="分类加载失败">
        <template #data>
          <echarts class="echarts" :options="categoryOption" />
          <ul class="category-list">
            <li class="category-item" v-for="category in categoryList" :key="category.id">
              <router-link :to="`/category/${category.id}`">{{ category.categoryName }}</router-link>
              <span class="category-count">({{ category.articleCount }})</span>
            </li>
          </ul>
        </template>
        <template #loading>
          <n-skeleton text round :repeat="5" />
        </template>
      </load-viewer>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import categoryApi from '@/api/category';
import type { Category } from '@/api/category/types';

let categoryOption = reactive({
  textStyle: {
    color: 'var(--grey-0)'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  title: {
    text: '文章分类统计图🎉',
    x: 'center',
    textStyle: {
      color: 'var(--grey-9)'
    }
  },
  legend: {
    top: 'bottom',
    textStyle: {
      color: 'var(--grey-6)'
    }
  },
  series: [
    {
      name: '分类统计',
      type: 'pie',
      radius: [35, 130],
      center: ['50%', '47%'],
      roseType: 'area',
      itemStyle: {
        borderRadius: 6
      },
      label: {
        color: 'var(--grey-6)'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: '20',
          fontWeight: 'bold'
        }
      },
      data: [] as {
        value: number;
        name: string;
      }[]
    }
  ]

});

const status = ref<number>(0);
const categoryList = ref<Category[]>([]);
onMounted(() => {
  categoryApi.getCategoryList().then(({ data }) => {
    categoryList.value = data.data;
    if (data.data !== null) {
      data.data.forEach((item) => {
        categoryOption.series[0].data.push({
          value: item.articleCount,
          name: item.categoryName
        });
      });
    }
    if (categoryList.value.length > 0) {
      status.value = 1;
    } else {
      status.value = 2;
    }
  }).catch(() => {status.value = -1;});
});
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.category-list {
  @include flex;
  flex-wrap: wrap;
  margin: 3rem 0 1rem 0;
}

.category-item {
  padding: 0.12em 1.2em 0.12em 1.4em;
}

.category-item:before {
  display: inline-block;
  position: relative;
  left: -0.75rem;
  width: 12px;
  height: 12px;
  border: 0.2rem solid var(--color-blue);
  border-radius: 50%;
  background: var(--grey-0);
  content: "";
  transition-duration: 0.3s;
}

.category-item:hover:before {
  border: 0.2rem solid var(--color-orange);
}

.category-item a:hover {
  transition: all 0.3s;
  color: var(--primary-color);
}

.category-item a:not(:hover) {
  transition: all 0.3s;
}

.category-count {
  margin-left: 0.5rem;
  font-size: 0.95rem;
  color: var(--grey-5);
}
</style>