<template>
  <router-link :to="props.to">
    <slot />
  </router-link>
</template>

<script lang="ts" setup>

const props = defineProps({
  to: {
    type: [String, Object],
    required: true
  }
});

</script>