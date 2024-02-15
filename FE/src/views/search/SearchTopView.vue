<template>
  <div class="search-container">       
    <input v-model="searchQuery" placeholder="Search for..." class="search-input" @keyup.enter="doSearch">
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

const doSearch = async () => {
  const query = searchQuery.value.trim();
  if (query) {
    const hasResults = await searchStore.researchKeywordRank(query);
    if (hasResults) {
      // 검색 결과가 있을 때 검색 세부 페이지로 라우팅합니다.
      router.push({ name: 'searchDetail', params: { keyword: query } });
    } else {
      // 검색 결과가 없을 때 사용자에게 알림을 표시합니다.
      alert('검색 결과가 없습니다.');
    }
  } else {
    // 검색어가 비어있을 경우 페이지 새로고침을 합니다.
    window.location.reload();
  }
};
</script>

<style>
.search-container {
  display: flex;
  align-items: center;
  position: relative;
  width: 100%;
  max-width: 550px; /* 최대 너비 설정, 필요에 따라 조정 */
  margin-bottom: 1%;
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