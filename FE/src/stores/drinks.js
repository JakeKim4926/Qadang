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
        
        console.log(err);
      });
  };

  const researchCafeDrinks = function (id) {
    axios({
      url: `${import.meta.env.VITE_REST_DRINKS_API}/drink`,
      method: "GET",
      params: { cafeId: id },
    })
      .then((res) => {
        if (res.status == responseState.SUCCESS) {
          cafeDrinkList.value = res.data;
        }
      })
      .catch((err) => {
        console.log(err);
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
        console.log(err);
      });
  };

  const setSelectedDrink = (drinkId) => {
    const drink = allDrinkList.value.find((d) => d.drinkId === drinkId);
    selectedDrink.value = drink;
  };

  return {
    cafeList,
    cafeDrinkList,
    allDrinkList,
    getCafeList,
    getCafeDrinkList,
    getAllDrinkList,
    selectedDrink,
    setSelectedDrink,
    researchCafe,
    researchCafeDrinks,
    researchAllDrink,
  };
});
