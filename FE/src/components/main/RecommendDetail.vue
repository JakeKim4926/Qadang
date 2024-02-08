<template>
  <div class="board-create-container">
      <div class="input-box">
        <div class="drink-detail-container">
        <div class="close" @click="closeRecommendDetailModal">
          <font-awesome-icon :icon="['fas', 'circle-xmark']" style="color: #000000;" size="xl"/>
        </div>
      <div class="drink-detail-header">
        <h1>{{ drinkDetail.cafeName }} {{ drinkDetail.drinkName }}</h1>
      </div>
      <hr class="fact-line2" />
      <img :src="drinkDetail.drinkUrl" alt="음료 이미지" class="drink-image" />
      
      <div class="nutrition-facts">
        <div class="fact">
          <hr class="fact-line" />
          <div class="fact-row">
            <label class="fact-label">카페인</label>
            <h3 class="fact-value">{{ drinkDetail.drinkCaffeine || '0'}}mg</h3>
          </div>
        </div>
        <div class="fact">
          <div class="fact-row">
            <label class="fact-label">당</label>
            <h3 class="fact-value">{{ drinkDetail.drinkSugar || '0'}}g</h3>
          </div>
          <hr class="fact-line" />
        </div>
        <div class="fact">
          <div class="fact-row">
            <label class="fact-label">포화지방</label>
            <h3 class="fact-value">{{ drinkDetail.drinkFat || '0'}}g</h3>
          </div>
        </div>
        <div class="fact">
          <div class="fact-row">
            <label class="fact-label">나트륨</label>
            <h3 class="fact-value">{{ drinkDetail.drinkNatrium || '0'}}mg</h3>
          </div>
        </div>
        <div class="fact">
          <div class="fact-row">
            <label class="fact-label">칼로리</label>
            <h3 class="fact-value">{{ drinkDetail.drinkCalorie || '0'}}kcal</h3>
          </div>
        </div>
        <div class="fact">
          <div class="fact-row">
            <label class="fact-label">용량</label>
            <h3 class="fact-value">
              {{ drinkDetail.drinkMl === 0 ? drinkDetail.drinkOz + 'oz' : drinkDetail.drinkMl + 'ml' }}
            </h3>
          </div>
        </div>
        <div class="fact">
          <div class="fact-row">
            <label class="fact-label">단백질</label>
            <h3 class="fact-value">{{ drinkDetail.drinkProtein || '0'}}g</h3>
          </div>
        </div>
        <div class="fact">
          <div class="fact-row">
            <label class="fact-label">알러지 유무</label>
            <h3 class="fact-value">{{ drinkDetail.drinkAllergy || '-' }}</h3>
          </div>
        </div>
      </div>
    </div>
  </div>
  </div>
  </template>
  
  <script setup>
  import { isRecommedModal, recommedDrinkInfo } from '../../stores/util'

  const drinkDetail = recommedDrinkInfo.value
  
  const closeRecommendDetailModal = () => {
    isRecommedModal.value = false
    console.log('!',isRecommedModal.value)
  }
  </script>
  <style scoped>

  .nutrition-facts .fact {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    margin-top: 0.5px; /* 위 간격 설정 */
    margin-bottom: 0.5px; /* 아래 간격 설정 */
  }
  
  
  
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
    height: 650px;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
    margin-bottom: 50px;
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
    padding: 5px;
    border-radius: 20px;
    text-align: center;
    font-family: Arial, sans-serif;
  }
  
  .drink-detail-header h1 {
    font-size: 20px;
    margin-bottom: 10px;
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
    margin-bottom: 0px;
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
    max-width: 300px;
  }
  
  .fact-label {  
    color: black;
    font-size: 18px;
    font-weight: bold;  
  }
  
  .fact-value {
    font-size: 16px;
    color: #562B1A;
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