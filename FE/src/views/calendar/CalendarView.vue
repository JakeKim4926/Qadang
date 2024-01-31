<template>
  <div class="diary-container">
    <h2>콰당 당력</h2>
    <FullCalendar class="calendar" />
    <CalendarDetail v-if="isCalendarModal" />
  </div>
</template>

<script setup>
import FullCalendar from '@/components/calendar/Fullcalendar.vue'
import CalendarDetail from '@/components/calendar/calendarDetail.vue';
import { RouterView } from 'vue-router';
import { isCalendarModal } from "@/stores/util"
import { useAccumulateStore } from '@/stores/accumulate';
import { ref, onMounted } from 'vue'

const accumulateStore = useAccumulateStore();

// const accumulateList = ref([]);
// const getAccumulateList = computed(()=>accumulateList);

onMounted(() => {
  const date = new Date();
  const year = date.getFullYear();
  let month = date.getMonth() + 1; // Adding 1 to adjust for zero-based months
  month = month < 10 ? "0" + month.toString() : month.toString();
  const now = ref(year.toString() + month.toString());
  console.log(now);
  accumulateStore.month(now);
})


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