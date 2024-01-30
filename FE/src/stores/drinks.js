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
        if (res.status == responseState.ACCEPT) {
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
        cafeDrinkList.value = res.data;
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

  return {
    cafeList,
    cafeDrinkList,
    allDrinkList,
    getCafeList,
    getCafeDrinkList,
    getAllDrinkList,
    researchCafe,
    researchCafeDrinks,
    researchAllDrink,
  };
});
