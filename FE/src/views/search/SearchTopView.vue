<template>
  <div class="search-container">        
    <input v-model="searchQuery" placeholder="Search for..." class="search-input">
    <button @click="doSearch" class="search-button">
      <font-awesome-icon :icon="['fas', 'search']" />
    </button>
  </div>        
</template>

<script setup>
import { ref } from 'vue';
import { useSearchStore } from '../../stores/search';
import router from '@/router';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

const searchStore = useSearchStore();
const searchQuery = ref('');

const doSearch = () => {
  if (searchQuery.value.trim()) {
    searchStore.researchKeywordRank(searchQuery.value.trim());
    router.push({ name: 'searchDetail', query: { keyword: searchQuery.value.trim() } });
  }
};
</script>

<style>
.search-container {
  display: flex;
  align-items: center;
  position: relative;
  width: 100%;
  max-width: 500px; /* 최대 너비 설정, 필요에 따라 조정 */
  margin-bottom: 5%;
}

.search-input {
  width: 100%;
  border-radius: 45px;
  border: 1px solid #EFEFEF;
  padding: 10px 40px 10px 20px; /* 오른쪽 패딩을 늘려서 버튼의 공간 확보 */
  font-size: 1rem;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  outline: none; /* 입력 필드 선택 시 테두리 제거 */
}

.search-button {
  background: none;
  border: none;
  position: absolute;
  right: 0; /* 입력 필드의 오른쪽 끝에 맞춤 */
  padding: 10px; /* 클릭 가능 영역 확장 */
  border-radius: 0 45px 45px 0; /* 오른쪽 상단과 하단 모서리 둥글게 */
  cursor: pointer;
}

.font-awesome-icon {
  color: #b3b3b3;
}
</style>