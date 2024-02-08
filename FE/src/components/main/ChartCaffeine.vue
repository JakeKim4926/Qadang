<template>
  <div>
    <div class="chart-container">
      <p>최근에 마신 카페인을 한눈에 보아요</p>
      <select name="selectDate" id="selectDate" v-model="seleteDate" class="button_select chart-date-button">
        <option value="day" class="date-text">일</option>
        <option value="week" class="date-text">주</option>
      </select>
    </div>
    <div class="info-box">
      <canvas id="chartCanvas" width="500"></canvas>
    </div>
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
      console.log('!!!', newData)

      const tmpDayData = []
      const tmpDataData = []

      // 차트 데이터에 넣을 데이터 적절하게 삽입
      newData.forEach(data => {
        tmpDayData.push(data.accumulateDate)
        tmpDataData.push(data.accumulateCaffeine)
      })

      // 오늘 날짜까지 갱신하기 위해 현재 날짜가 없으면 날짜 삽입
      if (!tmpDayData.includes(`${year}-${month}-${day}`)) {        
        tmpDayData.push(`${year}-${month}-${day}`)
      }

      // 다 끝난 뒤 차트에 대입
      chartData.data.labels = tmpDayData
      chartData.data.datasets[0].data = tmpDataData

      console.log('&&&', chartData.data.labels, chartData.data.datasets[0].data, chartData.options.scales.x.time.unit)
    }

    // 차트 업데이트
    chartCanvas.update()
    })

  // 날짜가 변경되었을 때 
  watch(() => seleteDate.value, (xTime) => {
    if (xTime === 'week') {
      console.log('1234', seleteDate.value)

      console.log('기존값', chartData.data.labels, chartData.data.datasets[0].data)

      const tmpDayData = []
      const tmpDataData = []

      // 가장 오래된 날짜부터 요일 계산하면서 값을 더해줌
      let firstDate = new Date(new Date(`${year}-${month}-${day}`).setMonth(new Date(`${year}-${month}-${day}`).getMonth() - 3))
      let lateDate = new Date(`${year}-${month}-${day}`)

      // 숫자를 더하면서 바뀔 값
      let cntDate = new Date(firstDate)

      console.log('가장 오래된 날짜 / 처음 날짜 / 비교 날짜', firstDate, lateDate, cntDate)

      let idx = 0
      let cnt = 0

      while (cntDate <= lateDate) {
        let cntYear = cntDate.getFullYear()
        let cntMonth = cntDate.getMonth() + 1
        let cntDay = cntDate.getDate()

        cntMonth = cntMonth < 10 ? '0' + cntMonth.toString() : cntMonth.toString()
        cntDay = cntDay < 10 ? '0' + cntDay.toString() : cntDay.toString()

        const comparePastDate = `${cntYear}-${cntMonth}-${cntDay}`

        // 첫날짜이면 시작을 해야 하므로 cnt 초기화후 그 날짜 push하고 값이 있다면 cnt에 더해줌
        if (idx === 0) {

          // 시작을 해야 하므로 그 날짜 push
          tmpDayData.push(cntDate)
          console.log('첫번째 날짜!')

          if (chartData.data.labels.includes(comparePastDate)) {
            cnt = 0
            cnt += chartData.data.datasets[0].data[idx]
            idx += 1
          }
          }

        // 마지막 날짜면 cnt 값 push 후 cnt 초기화
        else if (idx === chartData.data.labels.length-1) {

          console.log('마지막 날짜!')

          if (chartData.data.labels.includes(comparePastDate)) {
            cnt += chartData.data.datasets[0].data[idx]
            idx += 1
          }

          // 값 여부와 상관없이 cnt push 후 초기화
          tmpDataData.push(cnt)
          cnt = 0
        }

        // 일요일이라면 시작을 해야 하므로 cnt 초기화후 그 날짜 push하고 값이 있다면 cnt에 더해줌
        else if (cntDate.getDay() === 0) {

          // 시작을 해야 하므로 그 날짜 push
          console.log('한주 시작!')
          tmpDayData.push(cntDate)

          if (chartData.data.labels.includes(comparePastDate)) {
            cnt = 0
            cnt += chartData.data.datasets[0].data[idx]
            idx += 1
          }
        }

        // 토요일이라면 cnt 값 push 후 cnt 초기화
        else if (cntDate.getDay() === 6) {

          console.log('한주 끝!')
          if (chartData.data.labels.includes(comparePastDate)) {
            cnt += chartData.data.datasets[0].data[idx]
            idx += 1
          }

          // 값 여부와 상관없이 cnt push 후 초기화
          tmpDataData.push(cnt)
          cnt = 0
        }

        // 나머지는 날짜가 있다면 cnt 더해주기
        else {
          if (chartData.data.labels.includes(comparePastDate)) {
            cnt += chartData.data.datasets[0].data[idx]
            idx += 1
          }
        }

        // while문 종료를 위해 하루 뒤로 추가
        cntDate.setDate(cntDate.getDate() + 1)
      }

      // while문 종료 후 차트에 대입
      chartData.data.labels = tmpDayData
      chartData.data.datasets[0].data = tmpDataData
      chartData.options.scales.x.time.unit = xTime

      console.log('갱신했다!', chartData.data.labels, chartData.data.datasets[0].data, chartData.options.scales.x.time.unit)
    }

    else if (xTime === 'day') {
      console.log('1234', seleteDate.value)

      const newData = accumulateStore.getAccumulateList
      if (newData.length > 0) {
        console.log('!!!', newData)

        const tmpDayData = []
        const tmpDataData = []

        // 차트 데이터에 넣을 데이터 적절하게 삽입
        newData.forEach(data => {
          tmpDayData.push(data.accumulateDate)
          tmpDataData.push(data.accumulateCaffeine)
        })

        // 오늘 날짜까지 갱신하기 위해 현재 날짜가 없으면 날짜 삽입
        if (!tmpDayData.includes(`${year}-${month}-${day}`)) {        
          tmpDayData.push(date.value)
        }

        // 다 끝난 뒤 차트에 대입
        chartData.data.labels = tmpDayData
        chartData.data.datasets[0].data = tmpDataData
        chartData.options.scales.x.time.unit = xTime

        console.log('갱신했다!', chartData.data.labels, chartData.data.datasets[0].data, chartData.options.scales.x.time.unit)
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
width: 50px;
height: 20px;
margin-top: 10px;
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