<!--
 * @description md-editor 表情包扩展
 * @author juliy
 * @date 2023/3/24 22:49
-->
<template>
  <dropdown-toolbar title="emoji" :visible="state.visible"
                    @on-change="onChange">
    <template #overlay>
      <div class="emoji-container">
        <ol class="emojis">
          <li v-for="(emoji, index) of emojis" :key="`emoji-${index}`"
              @click="emojiHandler(emoji)"
              v-text="emoji"></li>
        </ol>
      </div>
    </template>
    <template #trigger>
      <svg class="md-editor-icon" aria-hidden="true" style="width:1.3rem;">
        <use xlink:href="#icon-emoji"></use>
      </svg>
    </template>
  </dropdown-toolbar>
</template>

<script lang="ts" setup>
import type { InsertContentGenerator } from 'md-editor-v3';
import MdEditor from 'md-editor-v3';
import type { PropType } from 'vue';
import { reactive } from 'vue';
import { emojis } from './data';

const DropdownToolbar = MdEditor.DropdownToolbar;
const props = defineProps({
  onInsert: {
    type: Function as PropType<(generator: InsertContentGenerator) => void>,
    default: () => () => null
  }
});
const state = reactive({
  visible: false
});

/**
 * 点击对应emoji后在输入区域插入该emoji
 * @param emoji 表情
 */
const emojiHandler = (emoji: string) => {
  const generator: InsertContentGenerator = () => {
    return {
      targetValue: emoji,
      select: false,
      deviationStart: 0,
      deviationEnd: 0
    };
  };
  props.onInsert(generator);
};

/**
 * 控制弹出菜单的显示与关闭
 * @param visible 是否显示
 */
const onChange = (visible: boolean) => {
  state.visible = visible;
};
</script>

<style lang="scss" scoped>
.emoji-container {
  border-radius: 3px;
  border: 1px solid #e6e6e6;
}

.emojis {
  position: relative;
  width: 363px;
  margin: 10px;
  padding: 0;
  background-color: #fff;

  li {
    cursor: pointer;
    float: left;
    border: 1px solid #e8e8e8;
    font-size: 18px;
    height: 34px;
    width: 34px;
    overflow: hidden;
    margin: -1px 0 0 -1px;
    padding: 4px 2px;
    text-align: center;
    list-style: none;
    z-index: 11;

    &:hover {
      position: relative;
      border: 1px solid #63a35c;
      z-index: 12;
    }
  }

  &::after {
    content: '';
    clear: left;
    display: block;
  }

  * {
    user-select: none;
  }
}
</style>