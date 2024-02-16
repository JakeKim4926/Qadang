import { ref, computed } from "vue";
import { responseState } from "./util";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const useDrinksStore = defineStore("drinks", () => {
  // =========== STATE ===============

  const cafeList = ref([]);
  const cafeDrinkList = ref([]);
  const allDrinkList = ref([]);
  const selectDrinkList = ref([]);
  const selectedDrink = ref(null);
  // =========== GETTER ===============

  const getCafeList = computed(() => {
    return cafeList.value; 
  });

  const getCafeDrinkList = computed(() => {
    return cafeDrinkList.value;
  });

  const getAllDrinkList = computed(() => {
    return allDrinkList.value;
  });

  const getSelectDrinkList = computed(() => {
    return selectDrinkList.value;
  });

  // =========== ACTION ===============
  const researchCafe = function () {
    axios({
      url: `${import.meta.env.VITE_REST_DRINKS_API}/cafe`,
      method: "GET",
    })
      .then((res) => {
        if (res.status == responseState.SUCCESS) {
          cafeList.value = res.data;
        }
      })
      .catch((err) => {
        
        
      });
  };

  const researchSelectDrink = function (cafeId,keyword) {
    axios({
      url: `${import.meta.env.VITE_REST_DRINKS_API}/${cafeId}/${keyword}`,
      method: "GET",
    })
      .then((res) => {
        if (res.status == responseState.SUCCESS) {
          selectDrinkList.value = res.data;
        }
      })
      .catch((err) => {
        
        
      });
  };

  const researchCafeDrinks = function (cafeId) {
    axios({
      url: `${import.meta.env.VITE_REST_DRINKS_API}/${cafeId}`,
      method: "GET",
    })
      .then((res) => {
        if (res.status == responseState.SUCCESS) {
          cafeDrinkList.value = res.data;
        }
      })
      .catch((err) => {
        
      });
  };

  const researchAllDrink = function () {
    axios({
      url: `${import.meta.env.VITE_REST_DRINKS_API}`,
      method: "GET",
    })
      .then((res) => {
        allDrinkList.value = res.data;
      })
      .catch((err) => {
        
      });
  };

  const setSelectedDrink = (drinkId) => {
    const drink = getAllDrinkList.value.find((d) => d.drinkId === drinkId);
    selectedDrink.value = drink;
  };

  

  return {
    cafeList,
    cafeDrinkList,
    allDrinkList,
    selectDrinkList,
    getCafeList,
    getCafeDrinkList,
    getAllDrinkList,
    getSelectDrinkList,
    selectedDrink,
    setSelectedDrink,
    researchCafe,
    researchSelectDrink,
    researchCafeDrinks,
    researchAllDrink,
  };
});
