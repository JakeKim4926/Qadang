<template>

<div class="board-create-container">
    <div class="input-box">
      <div class="drink-detail-container">
      <div class="close" @click="closeDetailModal">
        <font-awesome-icon :icon="['fas', 'circle-xmark']" style="color: #000000;" size="xl"/>
      </div>
    <div class="drink-detail-header">
      <h1>{{ drinkDetail.drinkName }}</h1>
    </div>
    <hr class="fact-line2" />
    <div>
      <button class="comparebox" @click="addToBasket">비교함담기</button>
    </div>
    <img :src="drinkDetail.drinkUrl" alt="음료 이미지" class="drink-image" />
    
    <div class="nutrition-facts">
      <div class="fact">
        <hr class="fact-line" />
        <div class="fact-row">
          <label class="fact-label">카페인</label>
          <h3 class="fact-value">{{ drinkDetail.drinkCaffeine }}mg</h3>
        </div>
      </div>
      <div class="fact">
        <div class="fact-row">
          <label class="fact-label">당</label>
          <h3 class="fact-value">{{ drinkDetail.drinkSugar }}g</h3>
        </div>
        <hr class="fact-line" />
      </div>
      <div class="fact">
        <div class="fact-row">
          <label class="fact-label">포화지방</label>
          <h3 class="fact-value">{{ drinkDetail.drinkFat }}g</h3>
        </div>
      </div>
      <div class="fact">
        <div class="fact-row">
          <label class="fact-label">나트륨</label>
          <h3 class="fact-value">{{ drinkDetail.drinkNatrium }}mg</h3>
        </div>
      </div>
      <div class="fact">
        <div class="fact-row">
          <label class="fact-label">칼로리</label>
          <h3 class="fact-value">{{ drinkDetail.drinkCalorie }}kcal</h3>
        </div>
      </div>
      <div class="fact">
        <div class="fact-row">
          <label class="fact-label">용량</label>
          <h3 class="fact-value">{{ drinkDetail.drinkML }}ml</h3>
        </div>
      </div>
      <div class="fact">
        <div class="fact-row">
          <label class="fact-label">단백질</label>
          <h3 class="fact-value">{{ drinkDetail.drinkProtein }}g</h3>
        </div>
      </div>
      <div class="fact">
        <div class="fact-row">
          <label class="fact-label">알러지 유무</label>
          <h3 class="fact-value">{{ drinkDetail.drinkAllergy }}</h3>
        </div>
      </div>
    </div>
  </div>
</div>
</div>
</template>

<script setup>
import { ref, onMounted,computed } from 'vue';
import { useRoute } from 'vue-router';
import { useDrinksStore } from '../../stores/drinks';
import { isDetailModal } from '../../stores/util'

const closeDetailModal = () => {
  isDetailModal.value = false
  console.log('!',isDetailModal.value)
}

const route = useRoute();
const drinksStore = useDrinksStore();
const drinkId = parseInt(route.params.drinkId, 10);

const drinkDetail = computed(() => {
  return drinksStore.allDrinkList.find(drink => drink.drinkId === drinkId) || {
    drinkId: 0,
    cafeId: 0,    
    cafeName: '',
    drinkName: '',
    drinkCaffeine: 0,
    drinkSugar: 0,
    drinkML: 0,
    drinkOZ: 0.0,
    drinkCalorie: 0,
    drinkNatrium: 0,
    drinkProtein: 0,
    drinkFat: 0,
    drinkUrl: '',
    drinkAllergy: '',
    cafeUrl: '',
  };
});

const addToBasket = () => {
  comparisonStore.addToBasket(drinkDetail.value);
  alert(`${drinkDetail.value.drinkName}이(가) 비교함에 추가되었습니다.`);
}

</script>
  
<style scoped>
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
  width: 500px;
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
.drink-detail-container {
  margin-bottom: 10%;
  background-color: #fff;
  padding: 20px;
  border-radius: 20px;
  text-align: center;
  font-family: Arial, sans-serif;
}

.drink-detail-header h1 {
  font-size: 20px;
  margin-bottom: 20px;
  color: #562B1A;
  
}
.comparebox{
  margin-left: 70%;
  border-radius: 90px;
  border: 2px solid #846046;
  background: var(--Color, #846046);
  color: #fff;
  cursor: pointer;
}

.drink-image {
  width: 100%;
  height: auto;
  margin-bottom: 20px;
}

.nutrition-facts .fact {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.nutrition-facts .fact-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  max-width: 300px; /* Adjust as needed */
}

.fact-label {
  
  color: black;
  
}

.fact-value {
  font-size: 14px;
  color: black;
  font-weight: bold;
}

.fact-line {
  width: 100%;
  border: none;
  border-top: 1px solid #562B1A;
  margin: 10px 0;
}
.fact-line2 {
  width: 100%;
  border: none;
  border-top: 3px solid #562B1A;
  margin: 10px 0;
}
</style>