<template>
  <div class="app">
    <TheHeader />
    <div class="main-content">
      <RouterView />
    </div>
    <template v-if="userAccessToken != null">
      <TheFooter />
    </template>

  </div>
</template>

<script setup>
import TheFooter from './components/common/TheFooter.vue';
import TheHeader from './components/common/TheHeader.vue';
import router from '@/router';
import { RouterLink, RouterView } from 'vue-router'
import {userAccessToken } from "@/stores/util"


</script>

<style scoped>
.app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
.main-content {
  flex: 1;
}
</style>
