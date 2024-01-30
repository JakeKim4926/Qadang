import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const useRankStore = defineStore("rank", () => {
  // =========== STATE ===============

  const rankKeywordList = ref([]);
  const rankRecordList = ref([]);

  // =========== GETTER ===============

  const getRankKeywordList = computed(() => {
    return rankKeywordList.value;
  });

  const getRankRecordList = computed(() => {
    return rankRecordList.value;
  });

  // =========== ACTION ===============
  const researchKeywordRank = function () {
    axios({
      url: `${import.meta.env.VITE_REST_RANK_API}/keywordranking`,
      method: "GET",
    })
      .then((res) => {
        rankKeywordList.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const researchRecordRank = function () {
    axios({
      url: `${import.meta.env.VITE_REST_RANK_API}/recordranking`,
      method: "GET",
    })
      .then((res) => {
        rankRecordList.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return {
    rankKeywordList,
    rankRecordList,
    getRankKeywordList,
    getRankRecordList,
    researchKeywordRank,
    researchRecordRank,
  };
});
