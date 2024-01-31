<template>
  <div>
    <h2>hello home</h2>
    <div>
      <!-- <RouterLink to="/calendar">캘린더 보기</RouterLink> -->
      <!-- <RouterLink to="/calendarDetail">캘린더 보기</RouterLink> -->
      <RouterLink :to="{ name : 'input'}">input</RouterLink>
      <div>
        <RouterView />
      </div>
    </div>
  </div>
</template>
  
<script setup>
import '../components/font/font.css';
import { onMounted } from "vue";

onMounted(() => {

})

</script>
  
<style scoped></style>