<template>
    <div>        
        <input v-model="searchQuery" placeholder="Search for...">
        <button @click="doSearch">검색</button>
    </div>        
    
</template>

<script setup>
import { ref } from 'vue';
import { useSearchStore } from '../../stores/search';
import router from '@/router';
import '../../components/color/color.css'

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

</style>