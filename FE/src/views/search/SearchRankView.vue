<template>
  <div class="search-frame">    
    <searchTopView />
    
    <div class="full-list-link">
      <h4 @click="goToFullList"> < 전체보기 </h4>
    </div>

    <div v-if="!searchResults.length">
      <div class="rankings">
        <div class="shading">
          <h3 class="ranking-title search">많이 <span class="highlight">검색</span>한 순위</h3>

        <div class="ranking-row">          
          <div v-for="(drink, index) in keywordRanking" :key="drink.drinkId" v-if="index < 3">
            {{ index + 1 }}. {{ drink.drinkName }}
          </div>
        </div>
        <div class="ranking-row">          
          <div v-for="(drink, index) in keywordRanking" :key="drink.drinkId" v-if="index >= 3 && index < 6">
            {{ index + 1 }}. {{ drink.drinkName }}
          </div>
        </div>
        </div>
        <div class="shading">
          <h3 class="ranking-title search">많이 <span class="highlight">기록</span>한 음료 순위</h3>
        <div class="ranking-row">          
          <div v-for="(drink, index) in recordRanking" :key="drink.drinkId" v-if="index < 3">
            {{ index + 1 }}. {{ drink.drinkName }}
          </div>
        </div>
        <div class="ranking-row">          
          <div v-for="(drink, index) in recordRanking" :key="drink.drinkId" v-if="index >= 3 && index < 6">
            {{ index + 1 }}. {{ drink.drinkName }}
          </div>
        </div>
      </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, computed } from 'vue';
import { useSearchStore } from '../../stores/search';
import router from '@/router';
import '../../components/color/color.css';
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

.full-list-link {
  align-self: flex-start;
  margin-bottom: 20px; 
  margin-left: 55px;  
}
.highlight {
  color: #846046; /* 특별한 색상 적용 */
}
.rankings {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.ranking-title {
  color: #000; /* 기본 색상 */
}


.ranking-row {
  display: flex;
  justify-content: center;
  width: 100%;
  margin-bottom: 10px;
}

.shading {
  display: flex; 
  flex-direction: column; 
  flex-wrap: wrap; 
  align-items: left; 
  justify-content: flex-start;
  border-radius: 45px; 
  border: 1px solid #EFEFEF;
  background: var(--Color, #FFF);
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  padding: 20px; 
  margin-bottom: 20px; 
  width: 100%;
}


</style>

