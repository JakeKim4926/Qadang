<template>
  <div>
    <h2>hello home</h2>
    <RouterLink :to="{name: 'mypage'}" class="edit-info-link">마이페이지</RouterLink>
    <RouterLink :to="{name: 'searchRank'}" class="edit-info-link">검색</RouterLink>
  </div>
</template>
  
<script setup>
import '../components/font/font.css';
</script>
  
<style scoped>
</style>
