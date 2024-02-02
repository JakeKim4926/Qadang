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
    <div v-if="accumulateStore.getAccumulateToday.accumulateCaffeine < 200.0">
      <img src="@/components/icons/ca_good.png" alt="양호 이미지" srcset="">
    </div>
    <div v-else if="accumulateStore.getAccumulateToday.accumulateCaffeine < 400.0">
      <img src="@/components/icons/ca_soso.png" alt="보통 이미지" srcset="">
    </div>
    <div v-else>
      <img src="@/components/icons/ca_bad.png" alt="나쁨 이미지" srcset="">
    </div>

    <div>
      <p>방금 마신 음료</p>
      <div v-if="recordsStore.getDayDrink.length > 0">
        {{ recordsStore.getDayDrink[recordsStore.getDayDrink.length-1].drinkCaffeine }}mg
      </div>
      <div v-else>
        오늘 마신 음료가 없습니다!
      </div>

      <p>하루 총합 섭취량 / 권장량</p>
      <p>{{ accumulateStore.getAccumulateToday.accumulateCaffeine }} / 
        {{ accumulateStore.getAccumulateToday.userCaffeine }}mg</p>
    </div>
    <div>
      <div v-if="accumulateStore.getAccumulateToday.accumulateCaffeine < 200.0">
        <p>양호 메시지</p>
      </div>
      <div v-else-if="accumulateStore.getAccumulateToday.accumulateCaffeine < 400.0">
        <p>보통 메시지</p>
      </div>
      <div v-else>
        <p>나쁨 메시지</p>
      </div>
    </div>
  </div>
  
  <div>
    최근에 마신 카페인을 한눈에 보아요
    <canvas id="chartCanvas"></canvas>
  </div>

  <div>
    오늘은 이 음료 어때요?
    <img :src="recommendStore.getRecommendedCaffeine.drinkUrl" alt="Recommended Drink" class="photo"/>
    {{ recommendStore.getRecommendedCaffeine.cafeName }}
    {{ recommendStore.getRecommendedCaffeine.drinkName }}
    {{ recommendStore.getRecommendedCaffeine.drinkCaffeine }}
    {{ recommendStore.getRecommendedCaffeine.drinkSugar }}
    추천 음료 상세보기
  </div>

  <div @click="goChat">
    <font-awesome-icon :icon="['fas', 'comments']" style="color: #000000;" />
  </div>
</template>

<script setup>
import { computed, reactive, ref, watchEffect } from 'vue';
import { onMounted } from 'vue';
import router from '@/router';

import { Chart } from "chart.js/auto";
import 'chartjs-adapter-date-fns';

import { useUserStore } from '@/stores/user';
import { useAccumulateStore } from '@/stores/accumulate';
import { useRecordsStore } from '@/stores/records';
import { useRecommendStore } from '@/stores/recommend';

const userStore = useUserStore()
const accumulateStore = useAccumulateStore()
const recordsStore = useRecordsStore()
const recommendStore = useRecommendStore()

const chartData = reactive({
    type: 'bar',
    data: {
      labels: [], // 날짜
      datasets: [{
        label: '일별 카페인 섭취량',
        data: [], // 날짜에 따른 데이터 기록 합산
        backgroundColor: ['#846046'],
      }]
    },
    options: {
      scales: {
        x: {
          type: 'time',
          time: {
            unit: 'day'
          }
        },
        y: {
          beginAtZero:true
        }
      },
      plugins: {
        title: {
          display: true,
          text: '일별 카페인 섭취량',
          font: {
            size: 20
          }
        },
      },
      responsive: true,
      // maintainAspectRatio: false
    }
  })

// 데이터를 가져오기 위한 함수
onMounted(async () => {

  // 현재 날짜를 알기 위한 변수
  const todayDate = new Date()
  const year = todayDate.getFullYear()
  let month = todayDate.getMonth() + 1
  let day = todayDate.getDate()

  month = month < 10 ? '0' + month.toString() : month.toString()
  day = day < 10 ? '0' + day.toString() : day.toString()

  const date = ref(null)
  date.value = year + month + day

  // await userStore.researchUser()                // 닉네임 <- 404 error
  await accumulateStore.today();                   // 권장량, 섭취량
  await accumulateStore.duration()                // chart.js를 위한 기간별 섭취량
  await recommendStore.researchRecommendCaffeine()     // 기록 기반 음료추천 카페인
  await recordsStore.researchDayDrink(date)       // 방금 마신 음료 계산을 위한 일자별 기록

  // chart.js
  const chartElement = document.querySelector('#chartCanvas').getContext('2d');
  
  const tmp_data = ref([])
  const chartCanvas = new Chart(chartElement, chartData)
  
  // 차트 데이터에 넣을 데이터가 생긴 뒤 데이터 삽입
  watchEffect(() => {
    if (accumulateStore.getAccumulateList.length > 1) {
      // console.log(accumulateStore.getAccumulateList)
      accumulateStore.getAccumulateList.forEach(data => {
        // console.log(data)
        chartData.data.labels.push(data.accumulateDate)
        // console.log(data.accumulateCaffeine)
        // console.log(chartData.data.datasets[0].data)
        tmp_data.value.push(data.accumulateCaffeine)
        // chartData.data.datasets[0].data.push(data.accumulateCaffeine)
      });
      chartData.data.datasets[0].data = tmp_data.value
      console.log(chartData.data.datasets[0].data)
    }
    chartCanvas.update()
  })
})

// 채팅으로 이동
const goChat = () => {
  router.push({name : 'chat'})
}
</script>

<style scoped>
.photo {
  width: 10%;
  height: 10%;
}
</style>