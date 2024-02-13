<template>
  <div class="diary-container">
    <div class="icon-info">
      <h1 class="calendar-title">한달간 마신 음료를 한 눈에</h1>
      <div class="icons">
        <img src="@/components/icons/caffeine.png" alt="no" />
        <h5>:75 mg</h5>
        <img src="@/components/icons/sugar.png" alt="no" />
        <h5>:6g</h5>
      </div>
    </div>
    <div v-if="!loading" :key="calendarKey" class="calendar-container">
      <FullCalendar v-if="updateCalendar" class="calendar" :key="calendarKey" />
      <CalendarDetail v-if="isCalendarModal" :key="detailKey" />
    </div>
  </div>
</template>

<script setup>
import FullCalendar from "@/components/calendar/Fullcalendar.vue";
import CalendarDetail from "@/components/calendar/calendarDetail.vue";
import { RouterView } from "vue-router";
import router from '@/router';
import {
  isCalendarModal,
  isUpdateInputModal,
  isUpdateNothingModal,
  isInputModal,
  isInputNothingModal,
  updateCalendar,
} from "@/stores/util";
import { useAccumulateStore } from "@/stores/accumulate";
import { useRecordsStore } from "@/stores/records";
import { ref, onMounted, onBeforeMount, watch } from "vue";

const accumulateStore = useAccumulateStore();
const recordStroe = useRecordsStore();
const loading = ref(true);
const calendarKey = ref(1);
const detailKey = ref(1);

// const accumulateList = ref([]);
// const getAccumulateList = computed(()=>accumulateList);

watch(isCalendarModal, (newValue, oldValue) => {
  if (!newValue ) {
    calendarKey.value += 1;
    if(isUpdateNothingModal.value || isUpdateInputModal.value)
      return;  

    router.go();
  }
});

watch(isUpdateInputModal, (newValue, oldValue) => {
  if (!newValue) {
    calendarKey.value += 1;
    router.go();
  }
});

watch(isUpdateNothingModal, (newValue, oldValue) => {
  if (!newValue) {
    calendarKey.value += 1;
    router.go();
  }
});

watch(isInputModal, (newValue, oldValue) => {
  if (!newValue ) {
    calendarKey.value += 1;
    if(isInputNothingModal.value)
      return;
    router.go();
  }
});

watch(isInputNothingModal, (newValue, oldValue) => {
  if (!newValue ) {
    calendarKey.value += 1;
    if(isInputModal.value)
      return;

    router.go();
  }
});

onBeforeMount(async () => {
  const date = new Date();
  const year = date.getFullYear();
  let month = date.getMonth() + 1; // Adding 1 to adjust for zero-based months
  month = month < 10 ? "0" + month.toString() : month.toString();
  const now = ref(year.toString() + month.toString());

  await accumulateStore.month(now);
  loading.value = false;
});
</script>

<style scoped>
.calendar {
  font-size: 14px;
  left: 0%;
  top: 0%;
  width: 70%;
}

.diary-container {
  position: relative;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
}

.icon-info {
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 60%;
  margin-top: 20px;
}

.calendar-title {
  color: rgb(68, 74, 104);
  margin-top: 20px;
  /* 원하는 만큼의 상단 마진 추가 */
  margin-left: 10%;
}

.icons {
  display: flex;
  align-items: center;
  margin-right: 5%;
}

.icons img {
  width: 10%;
  height: 10%;
  margin-left: 10%;
}

.calendar-container {
  width: 70%;
  /* 캘린더 컨테이너 너비 조정 */
  height: calc(100% - 100%);
  /* 캘린더 컨테이너 높이 조정 */
  /* 100px은 아이콘과 제목이 차지하는 높이를 고려하여 조절합니다. */
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
