<template>
  <div class="search-frame">
    <div class="search-bar">
      <input v-model="searchQuery" placeholder="Search for...">
      <button @click="keywordSearch">검색</button>
    </div>
    <a @click="goToFullList">전체보기</a>

    <div v-if="!searchResults.length">
      <div class="rankings">
        <h3>많이 검색한 순위</h3>
        <div class="ranking-row">          
          <div v-for="(drink, index) in keywordRanking" :key="drink.drinkId" v-if="index < 3">
            {{ drink.rank }}. {{ drink.name }}
          </div>
        </div>
        <div class="ranking-row">          
          <div v-for="(drink, index) in keywordRanking" :key="drink.drinkId" v-if="index >= 3 && index < 6">
            {{ drink.rank }}. {{ drink.name }}
          </div>
        </div>

        <h3>많이 기록한 음료 순위</h3>
        <div class="ranking-row">          
          <div v-for="(drink, index) in recordRanking" :key="drink.drinkId" v-if="index < 3">
            {{ drink.rank }}. {{ drink.name }}
          </div>
        </div>
        <div class="ranking-row">          
          <div v-for="(drink, index) in recordRanking" :key="drink.drinkId" v-if="index >= 3 && index < 6">
            {{ drink.rank }}. {{ drink.name }}
          </div>
        </div>
      </div>
    </div>
  </div>

  


    
</template>

<script setup>
import { ref, computed,onMounted,watch } from 'vue';
import { useSearchStore } from '../../stores/search';
import { useUserStore } from '../../stores/user';
import { useComparisonStore } from '../../stores/comparison';
import router from '@/router';
import '../../components/color/color.css'

const searchStore = useSearchStore();
const userStore = useUserStore();
const comparisonStore = useComparisonStore();
const searchQuery = ref('');
const searchResults = computed(() => searchStore.getSearchDrinkList);
const keywordRanking = computed(() => searchStore.getKeywordRanking);
const recordRanking = computed(() => searchStore.getRecordRanking);
const activeButtons = ref({});
const showSortMenu = ref(false);

const keywordSearch = () => {
  searchStore.researchKeywordRank(searchQuery.value);
};

const goToFullList = () => {
  router.push({ name: 'searchDetail' });
};

const enableButtons = (id) => {
  activeButtons.value[id] = true;
};

const disableButtons = (id) => {
  activeButtons.value[id] = false;
};

const toggleSortMenu = () => {
  showSortMenu.value = !showSortMenu.value;
};

const sortResults = (key, order) => {
  searchResults.value.sort((a, b) => {    
    const keyMap = {
      'sugar': 'drinkSugar',
      'caffeine': 'drinkCaffeine'
    };
    
    const actualKey = keyMap[key];
    
    if (order === 'asc') {
      return a[actualKey] - b[actualKey];
    } else {
      return b[actualKey] - a[actualKey];
    }
  });
};

// 비교하기 버튼 클릭 시 호출될 함수
const compareDrinks = () => {
  if (comparisonStore.basket.length > 1) {
    router.push({ name: 'searchDetail', params: { drinks: comparisonStore.basket } });
  }
};

// 상세보기 버튼 클릭 시 호출될 함수
const viewDetails = (drink) => {
  router.push({ name: 'compareDrink', params: { drink } });
};

watch(comparisonStore.basket, (newVal) => {
  // 세션 스토리지에 비교함 데이터 저장
  comparisonStore.saveBasketToSession();
}, { deep: true });

// 컴포넌트가 마운트될 때 세션 스토리지에서 비교함 데이터를 불러옴
onMounted(() => {
  comparisonStore.loadBasketFromSession();
});

watch(() => comparisonStore.basket, (newVal) => {
  comparisonStore.saveBasketToSession();
}, { deep: true });

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

.sort-buttons {
  position: relative;
}

.sort-menu {
  position: absolute;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  top: 100%;
  left: 0;
  z-index: 10;
}

.sort-menu button {
  margin-bottom: 5px;
  cursor: pointer;
}

.sort-menu button:last-child {
  margin-bottom: 0;
}
</style>