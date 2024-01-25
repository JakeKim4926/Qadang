<template>
    <div>
<p></p>
    </div>
</template>

<script setup>

import { ref, onMounted,computed } from 'vue';
import { useUserStore } from '../../stores/user'; 
import router from '@/router';
import { RouterLink, RouterView } from 'vue-router'

const store = useUserStore();
const bringUserInfo = async () => {
  try { 
    await store.researchUser();    
    userInfo.value = store.user.value;
  } catch (error) {    
    console.error("데이터를 가져오지 못했습니다.", error);
  }
}

  onMounted(() => {
  bringUserInfo();
});

</script>

<style>

</style>