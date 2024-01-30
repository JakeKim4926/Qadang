<template>
  <div>
    <!-- <div id="v-app"> -->
    <!-- <v-app id="inspire"> -->
    <TheHeader />
    <div>
      <RouterView />
    </div>
    <!-- <TheFooter />
    </v-app> -->
  </div>
</template>

<script setup>
import TheFooter from './components/common/TheFooter.vue';
import TheHeader from './components/common/TheHeader.vue';
import router from '@/router';
import { RouterLink, RouterView } from 'vue-router'

</script>

<style></style>
