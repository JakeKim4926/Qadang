<template>
  <drinkDetailView v-if="isDetailModal" :drink="selectedDrink" @closeModal="isDetailModal = false" />
  <CompareDrinkView v-if="isCompareModal" :drinks="comparisonStore.basket" @closeModal="isCompareModal = false" />
  <div class="search-frame">
    <searchTopView />

    <div class="button-row">
      <div class="left-buttons">
        <h4 @click="goToRanklist" class="rank-button"> 순위보기</h4>
      </div>
      <div class="right-buttons">
        <button
          :class="{'button_caffeine': comparisonStore.basket.length >= 2, 'button_nonActive_color': comparisonStore.basket.length < 2}"
          @click="compareDrinksModal"
          :disabled="comparisonStore.basket.length < 2">
          비교하기
        </button>
        <div class="sort-container">
          <button @click="toggleSortMenu" class="button_select2">{{ currentSortLabel }}</button>
          <div v-show="showSortMenu" class="sort-menu">
            <button @click="setSort('drinkId', 'asc')">등록순↑</button>
            <button @click="setSort('drinkId', 'desc')">등록순↓</button>
            <button @click="setSort('drinkSugar', 'asc')">당↑</button>
            <button @click="setSort('drinkSugar', 'desc')">당↓</button>
            <button @click="setSort('drinkCaffeine', 'asc')">카페인↑</button>
            <button @click="setSort('drinkCaffeine', 'desc')">카페인↓</button>
          </div>
        </div>
      </div>
    </div>


    <div v-if="comparisonStore.basket.length" class="comparison-basket shading">
    <div v-for="(item, index) in comparisonStore.basket" :key="item.drinkId" class="basket-item">
      <img :src="item.drinkUrl" alt="음료 이미지" class="drink-image">
      <div class="drink-info">
        <span class="drink-name">{{ item.drinkName }}</span>        
      </div>
      <button @click="comparisonStore.removeFromBasket(index)" class="remove-button"> <font-awesome-icon :icon="['fas', 'circle-xmark']" style="color: #000000;" /></button>
    </div>
  </div>
    </div>


    <div class="search-results-container">
      <table>
        <thead>
          <tr>
            <th>카페</th>
            <th>음료</th>
            <th>당(g)</th>
            <th>카페인(mg)</th>
          </tr>
        </thead>
        <tbody>          
          <tr v-for="drink in sortedDrinks" :key="drink.drinkId" @mouseover="enableButtons(drink.drinkId)" @mouseleave="disableButtons(drink.drinkId)">
         
          <td v-if="!activeButtons[drink.drinkId]">
            <img :src="drink.cafeUrl" alt="Cafe Image" class="cafe-img">
          </td>
          <td v-if="!activeButtons[drink.drinkId]">{{ drink.drinkName }}</td>

          <td v-if="!activeButtons[drink.drinkId]">
            <template v-if="(drink.drinkSugar + accumulateStore.getAccumulateDay.accumulateSugar) >= userRDISugar">
              <span class="font_green">{{ drink.drinkSugar }} g</span>
            </template>
            <template v-else>
              <span class="font_red">{{ drink.drinkSugar }} g</span>
            </template>
          </td>
          <td v-if="!activeButtons[drink.drinkId]">
            <template v-if="(drink.drinkCaffeine + accumulateStore.getAccumulateDay.accumulateCaffeine) >= userRDICaffeine">
              <span class="font_green">{{ drink.drinkCaffeine }} mg</span>
            </template>
            <template v-else>
              <span class="font_red">{{ drink.drinkCaffeine }} mg</span>
            </template>
          </td>

          <td v-if="activeButtons[drink.drinkId]" colspan="4">
            <div class="button-container">
              <a @click="viewDetailsModal(drink.drinkId)" class="detail-view-button">
                <h4>상세보기</h4>
              </a>
              <a @click="comparisonStore.addToBasket(drink)" class="detail-view-button">
                <h4>비교함 담기</h4>
              </a>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  
</template>

  

<script setup>
import { ref, computed,onMounted,watch,reactive } from 'vue';
import { useSearchStore } from '../../stores/search';
import { useUserStore } from '../../stores/user';
import { useDrinksStore} from '../../stores/drinks'
import { useComparisonStore } from '../../stores/comparison';
import { useAccumulateStore } from '../../stores/accumulate';
import router from '@/router';
import '../../components/color/color.css'
import searchTopView from './SearchTopView.vue';
import { isCompareModal, isDetailModal } from '../../stores/util'
import drinkDetailView from '@/views/search/drinkDetailView.vue'
import CompareDrinkView from '@/views/search/CompareDrinkView.vue'


const searchStore = useSearchStore();
const userStore = useUserStore();
const drinkStore = useDrinksStore();
const comparisonStore = useComparisonStore();
const activeButtons = ref({});
const showSortMenu = ref(false);
const userRDI = computed(() => userStore.getUserRDI);
const accumulateStore = useAccumulateStore();
const searchResults = computed(() => searchStore.getSearchDrinkList);
const allDrinkList = computed(() => drinkStore.getAllDrinkList);
const sortedDrinks = ref([]);
const sortState = reactive({
  sortField: 'drinkId',
  sortOrder: 'asc'
});
const selectedDrink = ref(null);
const userRDISugar = computed(() => userRDI.value.userRDISugar);
const userRDICaffeine = computed(() => userRDI.value.userRDICaffeine);


const viewDetailsModal = (drinkId) => {
  drinkStore.setSelectedDrink(drinkId); 
  if (drinkStore.selectedDrink) {
    isDetailModal.value = true; 
  } else {
    alert('해당 음료를 찾을 수 없습니다.');
  }
};

const compareDrinksModal = () => {
  if (comparisonStore.basket.length > 1) {
    isCompareModal.value = true;
  } else {
    alert('비교하기 위해서는 최소 2개의 음료가 필요합니다.');
  }
};

const setSort = (field, order) => {
  sortState.sortField = field;
  sortState.sortOrder = order;
  showSortMenu.value = false; 

  const drinksToSort = searchResults.value.length > 0 ? searchResults.value : allDrinkList.value;
  sortedDrinks.value = [...drinksToSort].sort((a, b) => {
    let result = 0;
    if (a[field] < b[field]) result = -1;
    else if (a[field] > b[field]) result = 1;
    return order === 'asc' ? result : -result;
  });
};

watch(searchResults, (newResults) => {
  setSort(sortState.sortField, sortState.sortOrder);
}, { immediate: true });
const currentSortLabel = computed(() => {
  const labels = {
    drinkId: '등록순',
    drinkSugar: '당',
    drinkCaffeine: '카페인'
  };
  const suffix = sortState.sortOrder === 'asc' ? '↑' : '↓';
  return `${labels[sortState.sortField]} ${suffix}`;
});

const toggleSortMenu = () => {
  showSortMenu.value = !showSortMenu.value;
};


// 여기에 drink 정보를 가져오기
// (추후 데이터 불러오는 상황을 봐서 이 코드를 수정해야할 수 있을듯)
onMounted(() => {
  if (!searchResults.length) {
    drinkStore.researchAllDrink();
  }
});

const enableButtons = (drinkId) => {
  activeButtons.value[drinkId] = true;
};

const disableButtons = (drinkId) => {
  activeButtons.value[drinkId] = false;
};



onMounted(() => {
  comparisonStore.loadBasketFromSession();
});

watch(comparisonStore.basket, () => {
  comparisonStore.saveBasketToSession();
}, { deep: true });

const goToRanklist = () => {
  router.push({ name: 'searchRank' });
};
</script>

<style scoped>
.cafe-img{
  width: 40px;
  height: 40px;
}
.rank-button{
  cursor: pointer;
}

.search-frame{
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 600px;
  margin: auto;
  margin-top: 40px;
  background: #FFF;
  padding: 20px;  
}

.rank-list-link {
  align-self: flex-start;
  margin-bottom: 20px; 
  margin-left: 55px;  
}

.compare-sort-buttons {
  display: flex; 
  align-items: center; 
  gap: 10px; 
}

.sort-container {
  position: relative; 
}

.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px; 
  height: 48px;
  /* background-color: #CB8A58;  */
  color: #f5f5f5;
  transition: background-color 0.5s ease;
  /* transform: translateZ(0);  */
  will-change: transform; 
}

.button-container:hover {
  background-image: linear-gradient(
    to right, 
    
    rgba(235, 170, 121, 0.5),
    rgba(197, 101, 27, 0.8), 
    rgba(204, 153, 114, 0.5) 
  );
}

.sort-menu {
  position: absolute;
  top: 100%;
  left: 0;
  /* background-color: #CB8A58;   */
  border-radius: 5px;
  /* box-shadow: 0 2px 5px rgba(0,0,0,0.2); */
  display: flex;
  flex-direction: column;
  padding: 5px;
  z-index: 100;
  white-space: nowrap;

}

.sort-menu button {
  margin-bottom: 5px;
  background-color: #CB8A58;
  cursor: pointer;
  color: #f5f5f5;
  border-radius: 10px;

}

.sort-menu button:last-child {
  margin-bottom: 0;
}

.search-results-container tr:hover .detail-view-button,
.search-results-container tr:hover .compare-button {
  display: block;
}


.compare-button {    
  display: inline-block; /* or 'block' if you want the button to take the full width of its container */
  padding: 10px 20px; /* 버튼 내부 패딩을 늘려서 텍스트가 들어갈 공간을 확보합니다. */
  margin: 5 10px; 
  font-size: 0.9em; 
}
.button-row {
  display: flex;
  justify-content: space-around; 
  width: 100%;
  align-items: center;
  margin-bottom: 10px;
}

.left-buttons {
  
  margin-right: 150px;
}

.right-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  cursor: pointer;  
}
/* 활성화 상태에 따른 버튼 스타일 변경 */
.button_caffeine {
  background: #CB8A58; 
  color: #fff;
  cursor: pointer; 
}

.button_nonActive_color {
  background: #DDD;
  color: #AAA;
}


/* 활성화된 버튼에 대한 추가적인 스타일 */
.button_caffeine.active {
  animation: pulse 1.5s infinite;
  cursor: pointer;
}


.button_select2 {
  border-radius: 90px;
  background: #846046; 
  color: #FFF; 
  padding: 3px; 
  cursor: pointer; 
  text-align: center; 
  font-weight: bold;
  width: 75px;
  
  transition: background-color 0.3s; 
}

.button_select2:hover {
  background-color: #754c24; 
}

.search-results-container {
  flex: auto;
  width: 530px; 
  margin-top: 20px; 
  overflow-x: auto; 
  margin: auto;
}

.cafe-image{
  width: 20px;
  height: 20px;
}

.search-results-container table {
  width: 100%;
  border-collapse: collapse; 
  text-align: center; 
  margin: auto;
}

.search-results-container th,
.search-results-container td {
  border-bottom: 3px solid #846046; /* 각 셀의 하단에 경계선 추가 */
  padding: 8px; /* 셀 안쪽의 여백 추가 */
}

.comparison-basket {
  display: flex;
  gap: 10px;
  overflow-x: auto;
  padding: 20px;
  border-radius: 10px;
  background-color: #f5f5f5;  
}
.shading {
  display: flex; 
  flex-direction: row; 
  flex-wrap: wrap; 
  align-items: left; 
  justify-content: flex-start;
  border-radius: 45px; 
  border: 1px solid #EFEFEF;
  background: var(--Color, #FFF);
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  padding: 20px; 
  margin-bottom: 20px; 
  width: 90%;
  margin: auto;
}

.basket-item {
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 10px;
  width: 120px;
  text-align: center;
  position: relative;
  background-color: #fff;  
  margin: auto;
}

.drink-image {
  width: 100px;
  height: 100px;
  border-radius: 5px;
}

.drink-info {
  margin-top: 10px;
}

.drink-name {
  font-weight: bold;
  font-size: 13px;
}

.remove-button {
  position: absolute;
  top: 5px;
  right: 5px;
  border: none;
  background-color: transparent;
  cursor: pointer;
  font-size: 18px;
}



.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;

}

/* 상세보기 버튼과 비교함 담기 버튼 스타일 */
.detail-view-button{
  cursor: pointer;
  margin: 30px;
}


.search-results-container td {
  border-bottom: 1px solid #e0e0e0; 
}
</style>
