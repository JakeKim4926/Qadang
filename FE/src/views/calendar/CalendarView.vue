<template>

  <div class ="diary-container">
    <h2>콰당 당력</h2>
    <FullCalendar class="calendar" />
    <CalendarDetail v-if="isCalendarModal" />
  </div>
</template>

<script setup>
import FullCalendar from '@/components/calendar/Fullcalendar.vue'
import CalendarDetail from '@/components/calendar/calendarDetail.vue';
import { RouterView } from 'vue-router';
import {isCalendarModal, getCalendarModal} from "@/stores/util"

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