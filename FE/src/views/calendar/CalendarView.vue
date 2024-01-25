<template>

  <div class ="diary-container">
    <h2>콰당 당력</h2>
    <FullCalendar class="calendar" />
  </div>
</template>

<script setup>
import FullCalendar from '@/components/calendar/Fullcalendar.vue'
import { RouterView } from 'vue-router';

</script>

<style scoped>
.calendar {
  margin-top: 100px;
  font-size: 14px;
  width: 700px;
}

.diary-container {
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
}
</style>