import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useSearchStore = defineStore("search", () => {
  // state
  const searchDrinkList = ref([]);
  const keywordRanking = ref([]);
  const recordRanking = ref([]);
  const allDrinks = ref([]);
  const todayAccumulate = ref({});
  const selectedrankDrink = ref({});

  // getters
  const getSearchDrinkList = computed(() => {
    return searchDrinkList.value;
  });
  const getKeywordRanking = computed(() => {
    return keywordRanking.value;
  });
  const getRecordRanking = computed(() => {
    return recordRanking.value;
  });
  const getAllDrinks = computed(() => {
    return allDrinks.value;
  });
  const getTodayAccumulate = computed(() => {
    return todayAccumulate.value;
  });

 
  // actions
  const researchKeywordRank = function (word) {
    axios({
      url: `${import.meta.env.VITE_REST_SEARCH_API}/${word}`, 
      method: "GET",
    })
    .then((res) => {
      searchDrinkList.value = res.data;
      console.log(res.data);
    })
    .catch((err) => {
      console.log(err);
    });
  };

  const bringKeywordRanking = function () {
    axios({
      url: `${import.meta.env.VITE_REST_RANK_API}/keywordranking`,
      method: "GET"
    })
    .then((res) => {
      keywordRanking.value = res.data;
    })
    .catch((err) => {
      console.error(err);
    });
  };

  const bringRecordRanking = function () {
    axios({
      url: `${import.meta.env.VITE_REST_RANK_API}/recordranking`,
      method: "GET"
    })
    .then((res) => {
      recordRanking.value = res.data;
    })
    .catch((err) => {
      console.error(err);
    });
  };

  const bringAllDrinks = function () {
    axios({
      url: import.meta.env.VITE_REST_DRINKS_API,
      method: "GET"
    })
    .then((res) => {
      allDrinks.value = res.data;
    })
    .catch((err) => {
      console.error(err);
    });
  };

  const bringTodayAccumulate = function () {
    axios({
      url: `${import.meta.env.VITE_REST_ACCUMULATE_API}/today`,
      method: "GET"
    })
    .then((res) => {
      todayAccumulate.value = res.data;
    })
    .catch((err) => {
      console.error(err);
    });
  };

  const setRankDrink = (drinkId) => {
    const drink = getRecordRanking.value.find((d) => d.drinkId === drinkId);
    selectedrankDrink.value = drink;
  };

  return {
    keywordRanking,
    recordRanking,
    allDrinks,
    todayAccumulate,
    getSearchDrinkList,
    getKeywordRanking,
    getRecordRanking,
    getAllDrinks,
    getTodayAccumulate,    
    selectedrankDrink,
    researchKeywordRank,
    bringKeywordRanking,
    bringRecordRanking,
    bringAllDrinks,
    bringTodayAccumulate,
    setRankDrink,
  };
});
