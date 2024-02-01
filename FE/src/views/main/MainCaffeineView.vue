<template>
  <div>
    <h2>Hello Main Caffeine</h2>
  </div>

  <p>{{ userStore.getUserName }} 님</p>
  <div>
    <RouterLink :to="{name:'mainSugar'}">당 섭취량으로 가는 버튼</RouterLink>
  </div>

  <p>카페인 섭취량</p>
  <div>
    <!-- 상태에 따라 출력되는 이미지와 문구가 다름 -->
    <p>이미지</p>
    <div>
      <p>방금 마신 음료</p>
      <p>카페인 {{ getRecentCaffeine }}mg</p>
      <p>하루 총합 섭취량 / 권장량</p>
      <p>{{ accumulateStore.getAcuumulateToday.accumulateCaffeine }} / 
        {{ accumulateStore.getAcuumulateToday.userCaffeine }}mg</p>
    </div>
    <div>
      <p>혈중 카페인농도 만땅</p>
    </div>
  </div>

  <div>
    최근에 마신 카페인을 한눈에 보아요
    chart.js 활용
  </div>

  <div>
    오늘은 이 음료 어때요?
    {{ userStore.getRecommendedSugar }}
    추천 음료 상세보기
  </div>

  <div @click="goChat">
    <font-awesome-icon :icon="['fas', 'comments']" style="color: #000000;" />
  </div>
</template>

<script setup>
import { computed, ref } from 'vue';
import { onMounted } from 'vue';
import router from '@/router';

import { useUserStore } from '@/stores/user';
import { useAccumulateStore } from '@/stores/accumulate';
import { useRecordsStore } from '@/stores/records';
import { useRecommendStore } from '@/stores/recommend';

const userStore = useUserStore()
const accumulateStore = useAccumulateStore()
const recordsStore = useRecordsStore()
const recommendStore = useRecommendStore()

const recentCaffeine = ref(0)

const getRecentCaffeine = computed(() => {
  return recentCaffeine.value
})

// 데이터를 가져오기 위한 함수
onMounted(async () => {

  // 현재 날짜를 알기 위한 변수
  const todayDate = new Date()
  const year = todayDate.getFullYear()
  let month = todayDate.getMonth()
  let day = todayDate.getDate()

  month = month < 10 ? '0' + month.toString() : month.toString()
  day = day < 10 ? '0' + day.toString() : day.toString()

  const date = ref(null)
  date.value = year + month + day

  // await userStore.researchUser()                   // 닉네임
  // await accumulateStore.today()                   // 권장량, 섭취량
  // await accumulateStore.duration()                // chart.js를 위한 기간별 섭취량
  // await userStore.researchRecommendCaffeine()     // 기록 기반 음료추천 카페
  // await recordsStore.researchDayDrink(date)       // 방금 마신 음료 계산을 위한 일자별 기록

  // 최근 마신 음료의 카페인 계산
  const idx = recordsStore.getDayDrink.length - 1
  const recentDrink = recordsStore.getDayDrink[idx]
  recentCaffeine.value = recentDrink ? recentDrink.drinkCaffeine.value : 0
})

// 채팅으로 이동
const goChat = () => {
  router.push({name : 'chat'})
}
</script>

<style scoped>
</style>