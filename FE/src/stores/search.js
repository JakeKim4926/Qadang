import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const useSearchStore = defineStore("search", () => {
  // =========== STATE ===============

  const searchDrinkList = ref([]);

  // =========== GETTER ===============

  const getSearchDrinkList = computed(() => {
    return searchDrinkList.value;
  });

  // =========== ACTION ===============
  const researchKeywordRank = function (word) {
    axios({
      url: `${import.meta.env.REST_SEARCH_API}`,
      method: "GET",
      params: { keyword: word },
    })
      .then((res) => {
        searchDrinkList.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return {
    searchDrinkList,
    getSearchDrinkList,
    researchKeywordRank,
  };
});
