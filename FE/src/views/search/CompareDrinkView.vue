<template>
  <div class="comparison-container">
    <div class="comparison-header">
      <h2>제품 비교하기</h2>
      <div class="comparison-images">
        <div v-for="drink in basket" :key="drink.id" class="comparison-item">
          <img :src="drink.drinkUrl" :alt="drink.drinkName" class="drink-image">
          <p class="drink-name">{{ drink.drinkName }}</p>
        </div>
      </div>
    </div>
    <table class="comparison-table">
      <tbody>
        <tr>
          <th>카페인</th>
          <td v-for="drink in basket" :key="`caffeine-${drink.id}`">
            {{ drink.drinkCaffeine }} mg
          </td>
        </tr>
        <tr>
          <th>당류</th>
          <td v-for="drink in basket" :key="`sugar-${drink.id}`">
            {{ drink.drinkSugar }} g
          </td>
        </tr>
        <tr>
          <th>포화지방</th>
          <td v-for="drink in basket" :key="`sodium-${drink.id}`">
            {{ drink.drinkFat }} g
          </td>
        </tr>
        <tr>
          <th>나트륨</th>
          <td v-for="drink in basket" :key="`sodium-${drink.id}`">
            {{ drink.drinkNatrium }} mg
          </td>
        </tr>
        <tr>
          <th>칼로리</th>
          <td v-for="drink in basket" :key="`calories-${drink.id}`">
            {{ drink.drinkCalories }} kcal
          </td>
        </tr>
        <tr>
          <th>용량</th>
          <td v-for="drink in basket" :key="`volume-${drink.id}`">
            {{ drink.drinkML }} ml
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
          <td v-for="drink in basket" :key="`allergy-${drink.id}`">
            {{ drink.drinkAllergy }}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { useComparisonStore } from '../../stores/comparison';

const comparisonStore = useComparisonStore();
const basket = comparisonStore.basket;

</script>

<style>
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

