<template>
  <div>
    <h2>hello home</h2>
    <div>
      <RouterLink to="/calendar">캘린더 보기</RouterLink>
      <div>
        <RouterView />
      </div>
      <h2>Hello Home</h2>
      <RouterLink :to="{name: 'input'}">input</RouterLink>
    </div>
  </div>
</template>
  
<script setup>

</script>
  
<style scoped></style>
