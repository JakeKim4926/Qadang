<template>
  <div>
    <div>
      <TheHeader />
    </div>      
    
    <div>
      <RouterView/>
    </div>
    <div>
      <TheFooter />
    </div>
    
  </div>
</template>

<script setup>
import TheFooter from './components/common/TheFooter.vue';
import TheHeader from './components/common/TheHeader.vue';
import router from '@/router';
import { RouterLink, RouterView } from 'vue-router'

</script>

<style>

</style>
