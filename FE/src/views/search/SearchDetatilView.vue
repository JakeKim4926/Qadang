<template>
  <div class="search-frame">  
    <searchTopView />
  <div>
    <a @click="goToRanklist">순위보기</a>
    <div class="compare-sort-buttons">
      <button
        :class="{ 'active': comparisonStore.basket.length >= 2 }"
        :disabled="comparisonStore.basket.length < 2"
        @click="compareDrinks">
        비교하기
      </button>      
      <div v-if="comparisonStore.basket.length" class="comparison-basket">
        <div v-for="(item, index) in comparisonStore.basket" :key="item.drinkId" class="basket-item">
          {{ item.drinkName }} - {{ item.drinkSugar }}g, {{ item.drinkCaffeine }}mg
          <button @click="comparisonStore.removeFromBasket(index)">X</button>
        </div>
      </div>
      <div class="sort-container">
        <button @click="toggleSortMenu" class="button_select">{{ currentSortLabel }}</button>
        <div v-if="showSortMenu" class="sort-menu">
          <button @click="setSort('drinkSugar', 'asc')">당↑</button>
          <button @click="setSort('drinkSugar', 'desc')">당↓</button>
          <button @click="setSort('drinkCaffeine', 'asc')">카페인↑</button>
          <button @click="setSort('drinkCaffeine', 'desc')">카페인↓</button>
        </div>
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
        <!-- 아마 이 부분에서 v-if를 써서 검색결과가 있으면 아래의 페이지가 나오고 없으면 전체보기가 나와함 -->
        <!-- 전체보기 역시도 아래와 같이 drink의 모든 정보들을 다 받아와서 그 정보를 쭉 나열해야함 -->
        <tbody>
            <tr v-for="drink in displayDrinks" :key="drink.drinkId" @mouseover="enableButtons(drink.drinkId)" @mouseleave="disableButtons(drink.drinkId)">
              <td>
                <img :src="drink.cafeUrl" alt="Cafe Image">
              </td>
              <td>{{ drink.drinkName }}</td>
              <td :class="sugarClass(drink)">{{ drink.drinkSugar }} g</td>
              <td :class="caffeineClass(drink)">{{ drink.drinkCaffeine }} mg</td>
              <td>
                <button v-if="activeButtons[drink.drinkId]" @click="viewDetails(drink)" class="detail-view-button">상세보기</button>
                <button v-if="activeButtons[drink.drinkId]" @click="comparisonStore.addToBasket(drink)" class="compare-button">비교함 담기</button>
              </td>
            </tr>
          </tbody>
      </table>
    </div>
  </div>
  <!-- </div> -->

    
</template>

<script setup>
import { ref, computed,onMounted,watch } from 'vue';
import { useSearchStore } from '../../stores/search';
import { useUserStore } from '../../stores/user';
import { useDrinksStore} from '../../stores/drinks'
import { useComparisonStore } from '../../stores/comparison';
import router from '@/router';
import '../../components/color/color.css'
import searchTopView from './SearchTopView.vue';

const searchStore = useSearchStore();
const userStore = useUserStore();
const drinkStore = useDrinksStore();
const comparisonStore = useComparisonStore();
const activeButtons = ref({});
const showSortMenu = ref(false);
const currentSortLabel = ref('정렬');
const userRDI = computed(() => userStore.getUserRDI);

const searchResults = computed(() => searchStore.getSearchDrinkList);
const allDrinkList = computed(() => drinkStore.getAllDrinkList);

const displayDrinks = computed(() => searchResults.value.length > 0 ? searchResults.value : allDrinkList.value);

const sugarClass = (drink) => ({
  'high-sugar': drink.drinkSugar + userRDI.value.accumulateSugar > userRDI.value.userSugar,
  'low-sugar': drink.drinkSugar + userRDI.value.accumulateSugar <= userRDI.value.userSugar
});

const caffeineClass = (drink) => ({
  'high-caffeine': drink.drinkCaffeine + userRDI.value.accumulateCaffeine > userRDI.value.userCaffeine,
  'low-caffeine': drink.drinkCaffeine + userRDI.value.accumulateCaffeine <= userRDI.value.userCaffeine
});

// 여기에 drink 정보를 가져오기
// (추후 데이터 불러오는 상황을 봐서 이 코드를 수정해야할 수 있을듯)
onMounted(() => {
  if (!searchResults.length) {
    drinkStore.researchAllDrink();
  }
});

const enableButtons = (id) => {
  activeButtons.value[id] = true;
};

const disableButtons = (id) => {
  activeButtons.value[id] = false;
};

const toggleSortMenu = () => {
  showSortMenu.value = !showSortMenu.value;
};

const compareDrinks = () => {
  if (comparisonStore.basket.length > 1) {
    router.push({ name: 'compareDrink', params: { drinks: comparisonStore.basket } });
  }
};
const viewDetails = (drink) => {
  router.push({ name: 'drinkDetail', params: { drink } });
};

// 컴포넌트가 마운트될 때 세션 스토리지에서 basket을 로드합니다.
onMounted(() => {
  comparisonStore.loadBasketFromSession();
});

// basket 배열이 변경될 때마다 세션 스토리지에 저장합니다.
watch(comparisonStore.basket, () => {
  comparisonStore.saveBasketToSession();
}, { deep: true });

const goToRanklist = () => {
  router.push({ name: 'searchRank' });
};
</script>

<style>
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
.ranking-row {
  display: flex;
  justify-content: space-between;
}
.high-sugar {
  color: red;
}

.low-sugar {
  color: blue;
}

.high-caffeine {
  color: red;
}

.low-caffeine {
  color: blue;
}

.detail-view-button, .compare-button {
  display: none;
}

.search-results-container tr:hover .detail-view-button,
.search-results-container tr:hover .compare-button {
  display: block;
}

.compare-sort-buttons {
  display: flex; 
  align-items: center; 
  gap: 10px; 
}
.sort-container {
  position: relative; 
}

.sort-menu {
  position: absolute;
  top: 100%; /* 정렬 버튼 바로 아래에 나타나도록 설정 */
  left: 0;
  background-color: #fff; /* 배경색 */
  border: 1px solid #ddd; /* 테두리 */
  border-radius: 5px; /* 테두리 둥글게 */
  box-shadow: 0 2px 5px rgba(0,0,0,0.2); /* 그림자 효과 */
  display: flex;
  flex-direction: column; /* 정렬 옵션을 세로로 나열 */
  padding: 10px; /* 패딩 */
  z-index: 100; /* 다른 요소 위에 오도록 z-index 설정 */
  white-space: nowrap;
}
.sort-menu button {
  margin-bottom: 5px;
  cursor: pointer;
}

.sort-menu button:last-child {
  margin-bottom: 0;
}
</style>