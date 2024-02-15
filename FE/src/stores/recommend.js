import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const useRecommendStore = defineStore("recommend", () => {
  // =========== STATE ===============

  const recommendedCaffeine = ref({});
  const recommendedSugar = ref({});
  const recentDrinkName = ref(null)

  // =========== GETTER ===============

  const getRecommendedCaffeine = computed(() => {
    return recommendedCaffeine.value;
  });

  const getRecommendedSugar = computed(() => {
    return recommendedSugar.value;
  });

  const getRecentDrinkName = computed(() => {
    return recentDrinkName.value
  })

  // =========== ACTION ===============

  const researchRecommendSugar = function () {
    axios({
      url: `${import.meta.env.VITE_REST_RECOMMEND_API}/sugar`,
      method: "GET",
    })
      .then((res) => {
        recommendedSugar.value = res.data;
      })
      .catch((err) => {
        
      });
  };

  const researchRecommendCaffeine = function () {
    axios({
      url: `${import.meta.env.VITE_REST_RECOMMEND_API}/caffeine`,
      method: "GET",
    })
      .then((res) => {
        recommendedCaffeine.value = res.data;
      })
      .catch((err) => {
        
      });
  };

  const researchRecentDrinkName = function () {
    axios({
      url: `${import.meta.env.VITE_REST_RECOMMEND_API}/record`,
      method: "GET",
    })
    .then((res) => {
      recentDrinkName.value = res.data;
    })
    .catch((err) => {
    })
  }
  
  return {
    recommendedCaffeine,
    recommendedSugar,
    recentDrinkName,
    getRecommendedCaffeine,
    getRecommendedSugar,
    getRecentDrinkName,
    researchRecommendCaffeine,
    researchRecommendSugar,
    researchRecentDrinkName,
  };
});
