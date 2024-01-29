<template>
  <div>
    <div class="search-bar">
      <input v-model="searchQuery" placeholder="Search for...">
      <button @click="keywordSearch">검색</button>
    </div>
    <button @click="goToFullList">전체보기</button>

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

    <div>
    <div class="sort-buttons">
      <button @click="toggleSortMenu">정렬</button>
      <div v-if="showSortMenu" class="sort-menu">
        <button @click="sortResults('drinkSugar', 'asc')">당↑ (오름차순)</button>
        <button @click="sortResults('drinkSugar', 'desc')">당↓ (내림차순)</button>
        <button @click="sortResults('drinkCaffeine', 'asc')">카페인↑ (오름차순)</button>
        <button @click="sortResults('drinkCaffeine', 'desc')">카페인↓ (내림차순)</button>
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
          <tr v-for="drink in searchResults" :key="drink.drinkId" @mouseover="enableButtons(drink.drinkId)" @mouseleave="disableButtons(drink.drinkId)">
            <td>
              <img :src="drink.cafeUrl" alt="Cafe Image">
            </td>
            <td>{{ drink.drinkName }}</td>
            <td :class="{ 'high-sugar': drink.drinkSugar + userRDI.value.accumulateSugar > userRDI.value.userSugar, 'low-sugar': drink.drinkSugar + userRDI.value.accumulateSugar <= userRDI.value.userSugar }">{{ drink.drinkSugar }} g</td>
            <td :class="{ 'high-caffeine': drink.drinkCaffeine + userRDI.value.accumulateCaffeine > userRDI.value.userCaffeine, 'low-caffeine': drink.drinkCaffeine + userRDI.value.accumulateCaffeine <= userRDI.value.userCaffeine }">{{ drink.drinkCaffeine }} mg</td>
          </tr>
        </tbody>
      </table>

      <button v-if="activeButtons[drink.drinkId]" class="detail-view-button">상세보기</button>
      <button v-if="activeButtons[drink.drinkId]" class="compare-button">비교함 담기</button>
    </div>
  </div>
  </div>

    
</template>

<script setup>
import { ref, computed } from 'vue';
import { useSearchStore } from '../../stores/search';
import { useUserStore } from '../../stores/user';
import router from '@/router';

const searchStore = useSearchStore();
const userStore = useUserStore();
const searchQuery = ref('');
const searchResults = computed(() => searchStore.getSearchDrinkList);
const keywordRanking = computed(() => searchStore.getKeywordRanking);
const recordRanking = computed(() => searchStore.getRecordRanking);
const activeButtons = ref({});
const showSortMenu = ref(false);
const userRDI = computed(() => userStore.getUserRDI);

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

</script>

<style>
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