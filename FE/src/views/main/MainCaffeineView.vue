<template>
  <div class="main-container">
    <div class="user-info">
      <h2>{{ userStore.getUserName }} 님</h2>
      <div @click="goSugar">
        <img src="@/components/icons/ca_toggle.png" alt="당 섭취량 메인페이지로 이동" class="toggle">
      </div>
    </div>

    <div>
      <p>카페인 섭취량</p>
      <div class="info-box">

        <div v-if="accumulateStore.getAccumulateToday.accumulateCaffeine < userStore.getUserRDI.userCaffeine * 1/2">
          <img src="@/components/icons/ca_good.png" alt="양호 이미지" class="status-img left-info">
        </div>
        <div v-else-if="accumulateStore.getAccumulateToday.accumulateCaffeine < userStore.getUserRDI.userCaffeine">
          <img src="@/components/icons/ca_soso.png" alt="보통 이미지" class="status-img left-info">
        </div>
        <div v-else>
          <img src="@/components/icons/ca_bad.png" alt="나쁨 이미지" class="status-img left-info">
        </div>

        <div class="mid-info">
          <p class="recent-drink">방금 마신 음료</p>
          <div v-if="recordsStore.getDayDrink.length > 0" class="drink-info">
            {{ (recordsStore.getDayDrink[recordsStore.getDayDrink.length-1].drinkCaffeine
            + 75 * recordsStore.getDayDrink[recordsStore.getDayDrink.length-1].plusShot).toFixed(2) }}mg
          </div>
          <div v-else class="drink-info">
            오늘 마신 음료가 없습니다!
          </div>

          <p class="today-title">하루 총합 섭취량 / 권장량</p>
          <p class="today-info"
          :class="{ 'font_red': accumulateStore.getAccumulateToday.accumulateCaffeine >= userStore.getUserRDI.userCaffeine,
          'font_green': accumulateStore.getAccumulateToday.accumulateCaffeine < userStore.getUserRDI.userCaffeine }">
            {{ accumulateStore.getAccumulateToday.accumulateCaffeine }} / 
            {{ userStore.getUserRDI.userCaffeine }}mg</p>
        </div>

        <div class="right-info superbig-font">
          <div v-if="accumulateStore.getAccumulateToday.accumulateCaffeine < userStore.getUserRDI.userCaffeine * 1/2">
            <p>양호 메시지</p>
          </div>
          <div v-else-if="accumulateStore.getAccumulateToday.accumulateCaffeine < userStore.getUserRDI.userCaffeine">
            <p>보통 메시지</p>
          </div>
          <div v-else="accumulateStore.getAccumulateToday.accumulateCaffeine >= userStore.getUserRDI.userCaffeine">
            <p>나쁨 메시지</p>
          </div>
        </div>
      </div>
    </div>

    <div>
      <p>최근에 마신 카페인을 한눈에 보아요</p>
      <div class="info-box">
        <canvas id="chartCanvas" width="500"></canvas>
      </div>
    </div>

    <div>
      <p>오늘은 이 음료 어때요?</p>
      <div class="info-box">
        <img :src="recommendStore.getRecommendedCaffeine.drinkUrl" alt="Recommended Drink" class="photo"/>
        <p>{{ recommendStore.getRecommendedCaffeine.cafeName }} {{ recommendStore.getRecommendedCaffeine.drinkName }}</p>
        <button @click="viewDetailsModal(recommendStore.getRecommendedCaffeine.drinkId)" class="button_caffeine">상세보기</button>
      </div>
    </div>

    <div @click="goChat" class="chat">
      <font-awesome-icon :icon="['fas', 'comments']" style="color: #000000;" size="2xl"/>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { onMounted } from 'vue';
import router from '@/router';

import { Chart } from "chart.js/auto";
import 'chartjs-adapter-date-fns';

import { useUserStore } from '@/stores/user';
import { useAccumulateStore } from '@/stores/accumulate';
import { useRecordsStore } from '@/stores/records';
import { useRecommendStore } from '@/stores/recommend';
import { useDrinksStore } from '@/stores/drinks';

import { isDetailModal } from '../../stores/util'

const userStore = useUserStore()
const accumulateStore = useAccumulateStore()
const recordsStore = useRecordsStore()
const recommendStore = useRecommendStore()
const drinksStore = useDrinksStore()

const chartData = {
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
      responsive: false,
    }
  }

// 데이터를 가져오기 위한 함수
onMounted(async () => {

  const urlParams = new URLSearchParams(window.location.search);
  // 'code' 파라미터 값 가져오기
  const kakaoCode = urlParams.get('code');
  // 현재 날짜를 알기 위한 변수
  if(kakaoCode != null && kakaoCode.length > 0) {
    userStore.sendKakaoToken(kakaoCode);
    console.log(kakaoCode);

    // URL에서 'code' 파라미터 제거
    const newUrl = window.location.origin + window.location.pathname;
    history.replaceState({}, document.title, newUrl);
  }

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
  // await userStore.researchAmount()              // 권장량 <- 404 error
  await accumulateStore.today()                    // 섭취량
  await accumulateStore.duration()                // chart.js를 위한 기간별 섭취량
  await recommendStore.researchRecommendCaffeine()     // 기록 기반 음료추천 카페인
  await recordsStore.researchDayDrink(date)       // 방금 마신 음료 계산을 위한 일자별 기록

  // chart.js
  const chartElement = document.querySelector('#chartCanvas').getContext('2d');
  const chartCanvas = new Chart(chartElement, chartData)
  
  // 차트 데이터에 넣을 데이터가 생긴 뒤 데이터 삽입
  watch(() => accumulateStore.getAccumulateList, (newData) => {
    if (newData.length >= 1) {
      console.log('!!!', newData)

      const tmpDayData = []
      const tmpDataData = []

      // 차트 데이터에 넣을 데이터 적절하게 삽입
      newData.forEach(data => {
        tmpDayData.push(data.accumulateDate)
        tmpDataData.push(data.accumulateCaffeine)
      })

      // 다 끝난 뒤 차트에 대입
      chartData.data.labels = tmpDayData
      chartData.data.datasets[0].data = tmpDataData

      console.log('@@@', chartData.data.labels, chartData.data.datasets[0].data)
    }

    // 차트 업데이트
    chartCanvas.update()
  })
})

// 당 섭취량 메인페이지로 이동
const goSugar = () => {
  router.push({name : 'mainSugar'})
}

// 추천 음료 상세페이지로 이동
const selectedDrink = ref(null)

const viewDetailsModal = (drinkId) => {
  const drink = drinksStore.getAllDrinkList.find(d => d.id === drinkId);
  if (drink) {
    selectedDrink.value = drink;
    isDetailModal.value = true;
  } else {
    alert('해당 음료를 찾을 수 없습니다.');
  }
}

// 채팅으로 이동
const goChat = () => {
  router.push({name : 'chat'})
}
</script>

<style scoped>
@import "../../components/font/font.css";
@import "../../components/color/color.css";

h2 {
  color: #562B1A;
  font-weight: bold;
  text-align: left;
}

p {
  font-weight: bold;
}

.main-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.toggle {
  width: 75px;
  cursor: pointer;
}

.user-info {
  display: flex;
  flex-direction: flex;
  justify-content: space-between;
  align-items: center;
  width: 700px;
}

.info-box {
  background: #ffffff;
  border-radius: 30px;
  border-style: solid;
  border-color: #d9d9d9;
  border-width: 1px;
  display: flex;
  align-items: center;
  width: 700px;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  margin-bottom: 10px;
}

.recent-drink {
  font-size: 25px;
  margin-bottom: 0;
}

.drink-info {
  font-size: 30px;
  color: #562B1A;
  font-weight: bold;
}

.status-img {
  width: 130px;
  height: 130px;
  margin-left: 10px;
  margin-right: 20px;
}

.left-info {
  flex: 1;
}

.mid-info {
  flex: 3;
}

.right-info {
  flex: 3;
}

.today-title {
  margin-bottom: 0;
}

.today-info {
  margin-top: 0;
}

#chartCanvas {
  margin: auto;
}

.photo {
  width: 50px;
  margin: 10px;
  border-radius: 20px;
}

.button_caffeine {
  width: 75px;
  height: 30px;
  color: white;
  font-weight: bold;
  border: none;
  margin-left: auto;
  margin-right: 10px;
  cursor: pointer;
}

.chat {
  width: 700px;
  right: 20px;
  margin-left: 1300px;
}
</style>