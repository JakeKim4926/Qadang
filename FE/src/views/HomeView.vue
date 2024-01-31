<template>
  <div>
    <h2>hello home</h2>
    <!-- <RouterLink to="/chat"><font-awesome-icon :icon="['fas', 'comments']" class="footer-icon" /></RouterLink> -->
    <RouterLink :to="{ name:'mainCaffeine' }">mainCaffeine</RouterLink>
    <RouterLink :to="{ name:'mainSugar' }">mainsugar</RouterLink>
  </div>
</template>
  
<script setup>
import '../components/font/font.css';
import { onMounted } from "vue";

onMounted(() => {

})

</script>
  
<style scoped></style>