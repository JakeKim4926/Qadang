<template>
  <div>
    <h2>hello home</h2>
    <div>
      <RouterLink to="/calendar">캘린더 보기</RouterLink>
      <div>
        <RouterView />
      </div>
      <h1 class="superbig-font">Hello Home</h1>
      <h2 class="big-font">Hello Home</h2>
      <h3 class="mid-font">Hello Home</h3>
      <h4 class="small-font">Hello Home</h4>
      
      <RouterLink :to="{name: 'mypage'}">마이페이지</RouterLink>
    </div>
  </div>
</template>
  
<script setup>
import '../components/font/font.css';
</script>
  
<style scoped>
</style>
