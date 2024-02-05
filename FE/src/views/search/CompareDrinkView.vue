<template>
  <div class="board-create-container">
    <div class="input-box">      
      <div class="close" @click="closeCompareModal">
        <font-awesome-icon :icon="['fas', 'circle-xmark']" style="color: #000000;" size="xl"/>
      </div>
  <div class="comparison-container">
    <div class="comparison-header">
      <h2>제품 비교하기</h2>
    </div>
    <table class="comparison-table">
      <tbody>
        <tr>
          <th></th>
          <td v-for="drink in basket" :key="`caffei-${drink.drinkId}`">
            <img :src="drink.drinkUrl" :alt="drink.drinkName" class="drink-image">
          </td>
        </tr>
        <tr>
          <th>음료명</th>
          <td v-for="drink in basket" :key="`caffei-${drink.drinkId}`">
            {{ drink.drinkName }}
          </td>
        </tr>
        <tr>
          <th>카페인</th>
          <td v-for="drink in basket" :key="`caffei-${drink.drinkId}`">
            {{ drink.drinkCaffeine }} mg
          </td>
        </tr>
        <tr>
          <th>카페인</th>
          <td v-for="drink in basket" :key="`caffei-${drink.drinkId}`">
            {{ drink.drinkCaffeine }} mg
          </td>
        </tr>
        <tr>
          <th>당류</th>
          <td v-for="drink in basket" :key="`sugar-${drink.drinkId}`">
            {{ drink.drinkSugar }} g
          </td>
        </tr>
        <tr>
          <th>포화지방</th>
          <td v-for="drink in basket" :key="`sodium-${drink.drinkId}`">
            {{ drink.drinkFat }} g
          </td>
        </tr>
        <tr>
          <th>나트륨</th>
          <td v-for="drink in basket" :key="`sodium-${drink.drinkId}`">
            {{ drink.drinkNatrium }} mg
          </td>
        </tr>
        <tr>
          <th>칼로리</th>
          <td v-for="drink in basket" :key="`calories-${drink.drinkId}`">
            {{ drink.drinkCalorie }} kcal
          </td>
        </tr>
        <tr>
          <th>용량</th>
          <td v-for="drink in basket" :key="`volume-${drink.drinkId}`">
            {{ drink.drinkMl }} ml
          </td>
        </tr>        
        <tr>
          <th>단백질</th>
          <td v-for="drink in basket" :key="`protein-${drink.id}`">
            {{ drink.drinkProtein }} g
          </td>
        </tr>
        <tr>
          <th>알레르기</th>
          <td v-for="drink in basket" :key="`allergy-${drink.idrinkId}`">
            {{ drink.drinkAllergy }}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</div>
</div>
</template>

<script setup>
import { useComparisonStore } from '../../stores/comparison';
import { isCompareModal } from '../../stores/util'

const closeCompareModal = () => {
  isCompareModal.value = false
  console.log('!',isCompareModal.value)
}
const comparisonStore = useComparisonStore();
const basket = comparisonStore.basket;

</script>

<style>

.board-create-container {
  position: fixed;
  width: 100vw;
  height: 100vh;
  /* max-width:614px;
  max-height: 584px; */
  background-color: rgba(128, 128, 128, 0.863) !important;
  top: 0;
  left: 0;
  margin: 0;
  z-index: 99 !important;
  display: flex;
  justify-content: center;
  align-items: center;
}

.input-box {
  background: #ffffff;
  border-radius: 30px;
  border-style: solid;
  border-color: #d9d9d9;
  border-width: 1px;
  position: absolute;
  width: 700px;
  height: 500px;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.close {
  display: flex;
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
}
.comparison-container {
  text-align: center;
}

.comparison-header h2 {
  margin-top: 5%;
  margin-bottom: 0;
  padding-bottom: 0.5em;
  color: #562B1A;
  border-bottom: 2px solid #562B1A;
  display: inline-block;
}

.comparison-images {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.comparison-item {
  text-align: center;
  margin: 0 10px;
}

.drink-image {
  max-width: 100px;
  height: auto;
}

.drink-name {
  margin-top: 5px;
  color: #562B1A; /* 제품명 색상 변경 */
  font-size: 0.9em;
}

.comparison-table {
  margin-top: 20px;
  border-collapse: collapse;
  width: 100%; /* 표 너비를 100%로 설정하여 전체 너비 사용 */
  border-top: 3px solid #562B1A;
  border-bottom: 3px solid #562B1A;
}
.comparison-table th {
  background-color: #ddd; /* 첫 번째 열 배경색 설정 */
  color: black;
  border-right: 1px solid #ddd;
}

.comparison-table td {
  color: #000; /* 셀 내용 색상을 검은색으로 설정 */
  border-bottom: 1px solid #ddd;
}


/* 나머지 행에 대한 상단 선을 회색으로 설정 */
.comparison-table tr:nth-child(n+2) th,
.comparison-table tr:nth-child(n+2) td {
  border-top: 1px solid #ddd;
}

/* 세로 줄 색상을 회색으로 설정 */
.comparison-table th, .comparison-table td {
  border-left: 1px solid #ddd;
}

/* 마지막 세로줄 제거 */
.comparison-table td:last-child {
  border-right: none;
}
</style>

