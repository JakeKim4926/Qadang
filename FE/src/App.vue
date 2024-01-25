<template>
  <div>
    <div>
      <TheHeader />
    </div>
      <RouterView />
    <div>
      <TheFooter />
    </div>
  </div>
</template>

<script setup>
import TheFooter from './components/common/TheFooter.vue';
import TheHeader from './components/common/TheHeader.vue';

</script>

<style>

</style>
