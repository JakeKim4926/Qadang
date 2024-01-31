<template>
    <div class="drink-detail-container">
      <div class="drink-detail-header">
        <h1>{{ drinkDetail.drinkName }}</h1>        
      </div>
      <img :src="drinkDetail.drinkUrl" alt="음료 이미지" />
      <div class="drink-detail-body">
        <div class="nutrition-facts">          
          <ul>
            <label>카페인</label> <h3>{{ drinkDetail.drinkCaffeine }}mg</h3>
            <label>당</label> <h3>{{ drinkDetail.drinkSugar }}g</h3>
            <label>포화지방</label> <h3>{{ drinkDetail.drinkFat }}g</h3>
            <label>나트륨</label> <h3>{{ drinkDetail.drinkNatrium }}mg</h3>
            <label>칼로리</label> <h3>{{ drinkDetail.drinkCalorie }}kcal</h3>
            <label>용량</label> <h3>{{ drinkDetail.drinkML }}ml</h3>
            <label>단백질</label> <h3>{{ drinkDetail.drinkProtein }}g</h3>
            <label>알러지유무</label> <h3>{{ drinkDetail.drinkAllergy }}</h3>
            
          </ul>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
import { ref, onMounted,computed } from 'vue';
import { useRoute } from 'vue-router';
import { useDrinksStore } from '../../stores/drinks';

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
  
</script>
  
<style>
  
  .drink-detail-container {
   
  }
  
  .drink-detail-header {
    
  }
  
  .drink-detail-body {
    
  }
  
  .nutrition-facts {
    
  }  
  
</style>
  