<template>
  <div class="search-frame">    
        <searchTopView />
    
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
import { ref, computed } from 'vue';
import { useSearchStore } from '../../stores/search';
;
import router from '@/router';
import '../../components/color/color.css'
import searchTopView from './SearchTopView.vue';
const searchStore = useSearchStore();

const searchResults = computed(() => searchStore.getSearchDrinkList);
const keywordRanking = computed(() => searchStore.getKeywordRanking);
const recordRanking = computed(() => searchStore.getRecordRanking);

const goToFullList = () => {
  router.push({ name: 'searchDetail' });
};


</script>

<style>
.search-frame {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 600px;
  margin: auto;
  margin-top: 40px;
  background: #FFF;
  padding: 20px;
}

.rankings {
  display: flex;
  flex-direction: column;
  align-items: center; /* 추가: 순위 섹션을 가운데로 정렬 */
  width: 100%;
}

.ranking-row {
  display: flex;
  justify-content: center; /* 추가: 순위 행을 가운데로 정렬 */
  width: 100%;
  margin-bottom: 10px; /* 추가: 각 순위 행 사이의 여백 */
}

/* ... 기존 스타일 ... */

.sort-menu button {
  margin-bottom: 5px;
  cursor: pointer;
}

.sort-menu button:last-child {
  margin-bottom: 0;
}

/* ... 기존 스타일 ... */
</style>
