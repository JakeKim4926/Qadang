<template>
  <div class="chart-container">
    <p>최근에 마신 당을 한눈에 보아요</p>
    <select name="selectDate" id="selectDate" v-model="seleteDate" class="button_select chart-date-button">
      <option value="day" class="date-text">일</option>
      <option value="week" class="date-text">주</option>
    </select>
  </div>
  <div class="info-box">
    <canvas id="chartCanvas" width="500"></canvas>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { onMounted } from 'vue';

import { Chart } from "chart.js/auto";
import 'chartjs-adapter-date-fns';

import { useAccumulateStore } from '@/stores/accumulate';

const accumulateStore = useAccumulateStore()

// 차트 날별로 선택하기 위한 변수
const seleteDate = ref('day')

// x축 기본값이 day인 차트
const chartData = {
type: 'bar',
data: {
  labels: [], // 날짜
  datasets: [{
    label: '일별 당 섭취량',
    data: [], // 날짜에 따른 데이터 기록 합산
    backgroundColor: ['#374B59'],
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

  // 현재 날짜를 알기 위한 변수
  const todayDate = new Date()
  const year = todayDate.getFullYear()
  let month = todayDate.getMonth() + 1
  let day = todayDate.getDate()

  month = month < 10 ? '0' + month.toString() : month.toString()
  day = day < 10 ? '0' + day.toString() : day.toString()

  const date = ref(null)
  date.value = year + month + day

  // chart.js를 위한 기간별 섭취량
  await accumulateStore.duration()

  // chart.js
  const chartElement = document.querySelector('#chartCanvas').getContext('2d');
  const chartCanvas = new Chart(chartElement, chartData)

  // 차트 데이터에 넣을 데이터가 생긴 뒤 데이터 삽입
  // 초기값이 day이므로 day로 설정
  watch(() => accumulateStore.getAccumulateList, (newData) => {
    if (newData.length > 0) {

      const tmpDayData = []
      const tmpDataData = []

      // 일은 최근 일주일 간을 표시해주기 위해 첫 시작을 지정
      let firstDate = new Date(new Date(`${year}-${month}-${day}`).setDate(new Date(`${year}-${month}-${day}`).getDate() - 7))

      let firsttYear = firstDate.getFullYear()
      let firstMonth = firstDate.getMonth() + 1
      let firstDay = firstDate.getDate()

      firstMonth = firstMonth < 10 ? '0' + firstMonth.toString() : firstMonth.toString()
      firstDay = firstDay < 10 ? '0' + firstDay.toString() : firstDay.toString()

      // 만약 최근 7일 전 데이터가 없을 경우 임의로 데이터 삽입
      if (!newData.includes(`${firsttYear}-${firstMonth}-${firstDay}`)) {
        tmpDayData.push(`${firsttYear}-${firstMonth}-${firstDay}`)
        tmpDataData.push(0)
      }

      // 차트 데이터에 넣을 데이터 적절하게 삽입
      newData.forEach(data => {
        tmpDayData.push(data.accumulateDate)
        tmpDataData.push(data.accumulateSugar)
      })

      // 오늘 날짜까지 갱신하기 위해 현재 날짜가 없으면 날짜 삽입
      if (!tmpDayData.includes(`${year}-${month}-${day}`)) {        
        tmpDayData.push(`${year}-${month}-${day}`)
      }

      // 다 끝난 뒤 차트에 대입
      chartData.data.labels = tmpDayData
      chartData.data.datasets[0].data = tmpDataData
    }

    // 차트 업데이트
    chartCanvas.update()
    })

  // 날짜가 변경되었을 때 
  watch(() => seleteDate.value, (xTime) => {
    if (xTime === 'week') {
      
      // 기존 차트에 있던 값
      const pastDayData = chartData.data.labels
      const pastDataData = chartData.data.datasets[0].data

      const tmpDayData = []
      const tmpDataData = []

      // 가장 오래된 날짜 계산
      let firstDate = new Date(new Date(`${year}-${month}-${day}`).setMonth(new Date(`${year}-${month}-${day}`).getMonth() - 3))

      let firstYear = firstDate.getFullYear()
      let firstMonth = firstDate.getMonth() + 1
      let firstDay = firstDate.getDate()

      firstMonth = firstMonth < 10 ? '0' + firstMonth.toString() : firstMonth.toString()
      firstDay = firstDay < 10 ? '0' + firstDay.toString() : firstDay.toString()

      let compareFirst = `${firstYear}-${firstMonth}-${firstDay}`

      // 오늘 날짜 계산
      let lateDate = new Date(`${year}-${month}-${day}`)

      let latetYear = lateDate.getFullYear()
      let lateMonth = lateDate.getMonth() + 1
      let lateDay = lateDate.getDate()

      lateMonth = lateMonth < 10 ? '0' + lateMonth.toString() : lateMonth.toString()
      lateDay = lateDay < 10 ? '0' + lateDay.toString() : lateDay.toString()

      let compareLate = `${latetYear}-${lateMonth}-${lateDay}`

      // 숫자를 더하면서 바뀔 값
      let cntDate = new Date(firstDate)

      let idx = 0
      let cnt = 0

      while (cntDate <= lateDate) {

        let cntYear = cntDate.getFullYear()
        let cntMonth = cntDate.getMonth() + 1
        let cntDay = cntDate.getDate()

        cntMonth = cntMonth < 10 ? '0' + cntMonth.toString() : cntMonth.toString()
        cntDay = cntDay < 10 ? '0' + cntDay.toString() : cntDay.toString()

        const compareCnt = `${cntYear}-${cntMonth}-${cntDay}`
        
        // 첫날짜이면 시작을 해야 하므로 cnt 초기화후 날짜를 push
        // 값이 있다면 cnt에 더해줌
        if (compareCnt === compareFirst) {
          cnt = 0
          tmpDayData.push(compareCnt)

          // 만약 기존값에 해당값이 있다면 그 값을 더해줌
          if (pastDayData.includes(compareCnt)) {
            cnt += pastDataData[idx]
            idx++
          }
        }

        // 마지막 날짜면 끝을 내야 하므로 cnt를 push해주고 초기화
        else if (compareCnt === compareLate) {
          if (pastDayData.includes(compareCnt)) {
            cnt += pastDataData[idx]
            idx++
          }

          tmpDataData.push(cnt)
          cnt = 0
        }

        // 요일의 시작인 일요일일 경우 cnt 초기화후 날짜를 push
        // 값이 있다면 cnt에 더해줌
        else if (cntDate.getDay() === 0) {
          cnt = 0
          tmpDayData.push(compareCnt)

          // 만약 기존값에 해당값이 있다면 그 값을 더해줌
          if (pastDayData.includes(compareCnt)) {
            cnt += pastDataData[idx]
            idx++
          }
        }

        // 요일의 마지막인 토요일일 경우 cnt를 push해주고 초기화
        else if (cntDate.getDay() === 6) {
          if (pastDayData.includes(compareCnt)) {
            cnt += pastDataData[idx]
            idx++
          }

          tmpDataData.push(cnt)
          cnt = 0
        }

        // 그 외 요일일 경우 cnt를 더해줌
        else {
          if (pastDayData.includes(compareCnt)) {
            cnt += pastDataData[idx]
            idx++
          }
        }

        // while문 종료를 위해 하루 뒤로 추가
        cntDate.setDate(cntDate.getDate() + 1)
      }

      // while문 종료 후 차트에 대입
      chartData.data.datasets[0].label = '주별 당 섭취량'
      chartData.data.labels = tmpDayData
      chartData.data.datasets[0].data = tmpDataData
      chartData.options.scales.x.time.unit = xTime

    }

    else if (xTime === 'day') {

      const newData = accumulateStore.getAccumulateList
      if (newData.length > 0) {

        const tmpDayData = []
        const tmpDataData = []

        // 일은 최근 일주일 간을 표시해주기 위해 첫 시작을 지정
        let firstDate = new Date(new Date(`${year}-${month}-${day}`).setDate(new Date(`${year}-${month}-${day}`).getDate() - 7))

        let firsttYear = firstDate.getFullYear()
        let firstMonth = firstDate.getMonth() + 1
        let firstDay = firstDate.getDate()

        firstMonth = firstMonth < 10 ? '0' + firstMonth.toString() : firstMonth.toString()
        firstDay = firstDay < 10 ? '0' + firstDay.toString() : firstDay.toString()

        // 만약 최근 7일 전 데이터가 없을 경우 임의로 데이터 삽입
        if (!newData.includes(`${firsttYear}-${firstMonth}-${firstDay}`)) {
          tmpDayData.push(`${firsttYear}-${firstMonth}-${firstDay}`)
          tmpDataData.push(0)
        }

        // 차트 데이터에 넣을 데이터 적절하게 삽입
        newData.forEach(data => {
          tmpDayData.push(data.accumulateDate)
          tmpDataData.push(data.accumulateSugar)
        })

        // 오늘 날짜까지 갱신하기 위해 현재 날짜가 없으면 날짜 삽입
        if (!tmpDayData.includes(`${year}-${month}-${day}`)) {        
          tmpDayData.push(`${year}-${month}-${day}`)
        }

        // 다 끝난 뒤 차트에 대입
        chartData.data.datasets[0].label = '일별 당 섭취량'
        chartData.data.labels = tmpDayData
        chartData.data.datasets[0].data = tmpDataData
        chartData.options.scales.x.time.unit = xTime

      }
    }

    // 차트 업데이트
    chartCanvas.update()
  })
})
</script>

<style scoped>
@import "../../components/font/font.css";
@import "../../components/color/color.css";

p {
  font-weight: bold;
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

.chart-date-button {
  border-color: #374B59;
  width: 50px;
  height: 20px;
  margin-top: 10px;
  cursor: pointer;
}

.date-text {
  text-align: center;
}

#chartCanvas {
  margin: auto;
  margin-top: 20px;
  margin-bottom: 20px;
}

.chart-container {
  display: flex;
  justify-content: space-between;
}
</style>