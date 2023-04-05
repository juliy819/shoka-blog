<!--
 * @description 首页正中间显示文字
 * @author juliy
 * @date 2023/4/4 16:47
-->
<template>
  <div class="brand">
    <!-- 网站标题 -->
    <p class="site-name">
      {{ blogStore.siteConfig.siteName }} </p>
    <!-- 打字机 -->
    <div class="typer">
      {{ obj.output }}
      <span class="typer-cursor">|</span>
    </div>
  </div>
</template>

<script lang="ts" setup>
import useStore from '@/stores';
import EasyTyper from 'easy-typer-js';
import hitokoto from '@/assets/hitokoto.json';

const obj = reactive({
  output: '',
  isEnd: false,
  speed: 120,
  singleBack: false,
  sleep: 5000,
  type: 'rollback',
  backSpeed: 50,
  sentencePause: false
});

const { blogStore } = useStore();

/**
 * 打乱数组
 * @param arr 数组
 */
const shuffle = (arr: Array<string>) => {
  let n = arr.length;
  while (n > 1) {
    let i = Math.floor(Math.random() * n--);
    [arr[i], arr[n]] = [arr[n], arr[i]];
  }
  return arr;
};

let easyTyper: EasyTyper;
let data = shuffle(JSON.parse(JSON.stringify(hitokoto)));

/**
 * 加载打字机数据
 */
const loadTyperData = () => {
  easyTyper = new EasyTyper(obj, data, () => {
    data = shuffle(JSON.parse(JSON.stringify(hitokoto)));
    loadTyperData();
  }, () => {
  });
};

onMounted(() => {
  loadTyperData();
});
</script>

<style lang="scss" scoped>
@use "@/assets/styles/mixin";

.brand {
  @include mixin.flex;
  flex-direction: column;
  position: fixed;
  padding: 0 1rem;
  z-index: -1;

  .site-name {
    font-family: "Fredericka the Great", Mulish, -apple-system, "PingFang SC", "Microsoft YaHei",
    sans-serif;
    font-size: 4em;
    line-height: 1.5;
    animation: titleScale 1s;
    -webkit-animation: titleScale 1s;
  }

  .typer {
    letter-spacing: 0.1em;
  }

  .typer-cursor {
    margin-left: 0.5rem;
    opacity: 1;
    animation: blink 0.8s infinite;
    -webkit-animation: blink 0.8s infinite;
  }

  @media (min-width: 760px) {
    .typer {
      font-size: 1.5rem;
    }
  }

}

// 箭头闪烁
@keyframes blink {
  0% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}

</style>