<template>
  <drinkDetailView v-if="isDetailModal" :drink="selectedDrink" @closeModal="isDetailModal = false" />
  <CompareDrinkView v-if="isCompareModal" :drinks="comparisonStore.basket" @closeModal="isCompareModal = false" />
  <div class="search-frame">
    <searchTopView />

    <div class="button-row">
      <div class="left-buttons">
        <h4 @click="goToRanklist" class="rank-button">< 순위보기</h4>
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


    <div v-if="comparisonStore.basket.length" class="comparison-basket">
    <div v-for="(item, index) in comparisonStore.basket" :key="item.drinkId" class="basket-item">
      <img :src="item.drinkImage" alt="음료 이미지" class="drink-image">
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
            <td>
              <img :src="drink.cafeUrl" alt="Cafe Image">
            </td>
            <td>{{ drink.drinkName }}</td>
            <td :class="{'font_sugar_color': sugarClass(drink) === 'high-sugar', 'font_gray': sugarClass(drink) !== 'high-sugar'}">{{ drink.drinkSugar }} g</td>
            <td :class="{'font_caffeine_color': caffeineClass(drink) === 'high-caffeine', 'font_gray': caffeineClass(drink) !== 'high-caffeine'}">{{ drink.drinkCaffeine }} mg</td>
            <td>
              <button v-if="activeButtons[drink.id]" @click="viewDetailsModal(drink.id)" class="detail-view-button button_select">
              </button>
              <button v-if="activeButtons[drink.id]" @click="comparisonStore.addToBasket(drink)" class="compare-button button_add_drink_color">
              </button>
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

const viewDetailsModal = (drinkId) => {
  const drink = drinksStore.getAllDrinkList.find(d => d.id === drinkId);
  if (drink) {
    selectedDrink.value = drink;
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

const sugarClass = (drink) => ({
  'high-sugar': drink.drinkSugar + accumulateStore.accumulateToday.accumulateSugar > userRDI.userSugar,
  'low-sugar': drink.drinkSugar + accumulateStore.accumulateToday.accumulateSugar <= userRDI.userSugar
});

const caffeineClass = (drink) => ({
  'high-caffeine': drink.drinkCaffeine + accumulateStore.accumulateToday.accumulateCaffeine > userRDI.userCaffeine,
  'low-caffeine': drink.drinkCaffeine + accumulateStore.accumulateToday.accumulateCaffeine <= userRDI.userCaffeine
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

.sort-menu {
  position: absolute;
  top: 100%;
  left: 0;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.2);
  display: flex;
  flex-direction: column;
  padding: 10px;
  z-index: 100;
  white-space: nowrap;
}

.sort-menu button {
  margin-bottom: 5px;
  cursor: pointer;
}

.sort-menu button:last-child {
  margin-bottom: 0;
}

.search-results-container tr:hover .detail-view-button,
.search-results-container tr:hover .compare-button {
  display: block;
}

/* 상세보기 버튼과 비교함 담기 버튼에 적용될 기본 스타일 */
.detail-view-button,
.compare-button {
  border-radius: 90px;
  padding: 5px 10px;
  border: none;
  cursor: pointer;
  margin: 2px;
  font-size: 0.8em;
}
.button-row {
  display: flex;
  justify-content: space-around; /* 변경된 부분 */
  width: 100%;
  align-items: center;
  margin-bottom: 10px;
}

.left-buttons {
  /* 추가적인 마진이 필요 없으면 삭제합니다 */
  margin-right: 150px;
}

.right-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  
}
/* 활성화 상태에 따른 버튼 스타일 변경 */
.button_caffeine {
  background: #846046;
  color: #fff;
}

.button_nonActive_color {
  background: #DDD;
  color: #AAA;
}

/* 당과 카페인 수치에 따른 글자색 변경 */
.high-sugar {
  color: var(--font_red);
}

.low-sugar {
  color: var(--font_green);
}

.high-caffeine {
  color: var(--font_red);
}

.low-caffeine {
  color: var(--font_green);
}

/* 비교함 담기 버튼 스타일 */
.compare-button {
  background: var(--button_add_drink_color);
  color: #fff;
}

/* 비활성화 상태인 비교하기 버튼 스타일 */
.button_nonActive {
  background: var(--button_nonActive_color);
  color: #AAA;
}

/* 활성화된 버튼에 대한 추가적인 스타일 */
.button_caffeine.active {
  animation: pulse 1.5s infinite;
}

/* 버튼에 애니메이션 효과 추가 */
@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(132, 96, 70, 0.7);
  }
  70% {
    box-shadow: 0 0 0 10px rgba(132, 96, 70, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(132, 96, 70, 0);
  }
}
.button_select2 {
  border-radius: 90px;
  background: #846046; /* 버튼 배경색 */
  color: #FFF; /* 버튼 내 글씨 색상 */
  padding: 3px; /* 버튼 내 패딩: 위 아래 5px, 좌우 15px */
  cursor: pointer; /* 마우스 오버 시 커서 변경 */
  text-align: center; /* 텍스트 중앙 정렬 */
  font-weight: bold; /* 글씨체 두께 */
  border: none; /* 테두리 없앰 */
  width: 75px;
  
  transition: background-color 0.3s; 
}

.button_select2:hover {
  background-color: #754c24; /* 호버 상태의 배경 색상 */
}

.search-results-container {
  flex: auto;
  width: 50%; /* 테이블의 너비를 컨테이너에 맞춤 */
  margin-top: 20px; /* 상단과의 여백을 조정 */
  overflow-x: auto; /* 너비가 넘치는 경우 스크롤바를 표시 */
  margin: auto;
}

.search-results-container table {
  width: 100%; /* 테이블의 너비를 컨테이너에 맞춤 */
  border-collapse: collapse; /* 테이블 셀 사이의 경계선을 하나로 합침 */
  text-align: center; /* 텍스트를 왼쪽 정렬 */
  margin: auto;
}

.search-results-container th,
.search-results-container td {
  border-bottom: 1px solid black; /* 각 셀의 하단에 경계선 추가 */
  padding: 8px; /* 셀 안쪽의 여백 추가 */
}

/* 상세보기 및 비교함 담기 버튼 스타일 */
.detail-view-button,
.compare-button {
  /* 버튼 스타일 지정 */
}


.comparison-basket {
  display: flex;
  gap: 10px;
  overflow-x: auto;
  padding: 10px;
  border-radius: 10px;
  background-color: #f5f5f5;
}

.basket-item {
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 10px;
  width: 120px;
  text-align: center;
  position: relative;
  background-color: #fff;
}

.drink-image {
  width: 100px;
  height: auto;
  border-radius: 5px;
}

.drink-info {
  margin-top: 10px;
}

.drink-name {
  font-weight: bold;
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
</style>
