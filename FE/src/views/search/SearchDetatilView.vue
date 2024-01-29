<template>
  <div>
    <div class="full-list">
      <!-- 전체 음료 목록 -->
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useSearchStore } from '@/stores/search';

const searchStore = useSearchStore();

onMounted(() => {
  // 컴포넌트가 마운트될 때 전체 음료 목록을 가져옵니다
  searchStore.bringAllDrinks();
});

// searchStore.allDrinks가 전체 목록을 포함하는 반응형 속성이라고 가정
</script>
