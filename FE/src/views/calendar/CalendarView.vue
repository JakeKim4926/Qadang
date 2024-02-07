<template>
  <div class="diary-container">
    <h2>콰당 당력</h2>
    <template v-if="!loading">
      <FullCalendar v-if="!isCalendarModal" class="calendar" :key="calendarKey" />
      <CalendarDetail v-if="isCalendarModal" />
    </template>
  </div>
</template>

<script setup>
import FullCalendar from '@/components/calendar/Fullcalendar.vue'
import CalendarDetail from '@/components/calendar/calendarDetail.vue';
import { RouterView } from 'vue-router';
import { isCalendarModal, isUpdateInputModal, isUpdateNothingModal, isInputModal, isInputNothingModal } from "@/stores/util"
import { useAccumulateStore } from '@/stores/accumulate';
import { ref, onMounted, onBeforeMount, watch } from 'vue'

const accumulateStore = useAccumulateStore();
const loading = ref(true);
const calendarKey = ref(0);

// const accumulateList = ref([]);
// const getAccumulateList = computed(()=>accumulateList);

watch(isCalendarModal, (newValue, oldValue) => {
  if (!newValue) {
    console.log("iscal")
    calendarKey.value += 1;
  }
});

watch(isUpdateInputModal, (newValue, oldValue) => {
  if (!newValue) {
    console.log("iscal2")
    calendarKey.value += 1;
  }
});

watch(isUpdateNothingModal, (newValue, oldValue) => {
  if (!newValue) {
    console.log("iscal3")
    calendarKey.value += 1;
  }
});

watch(isInputModal, (newValue, oldValue) => {
  if (!newValue) {
    console.log("iscal4")
    calendarKey.value += 1;
  }
});

watch(isInputNothingModal, (newValue, oldValue) => {
  if (!newValue) {
    console.log("iscal5")
    calendarKey.value += 1;
    window.location.reload();
  }
});

onBeforeMount(async () => {
  const date = new Date();
  const year = date.getFullYear();
  let month = date.getMonth() + 1; // Adding 1 to adjust for zero-based months
  month = month < 10 ? "0" + month.toString() : month.toString();
  const now = ref(year.toString() + month.toString());

  await accumulateStore.month(now);
  console.log("are you really here?");
  loading.value = false;
})

</script>

<style scoped>
.calendar {
  font-size: 14px;
  left: 0%;
  top: 0%;
  width: 700px;
}

.diary-container {
  position: relative;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
}
</style>