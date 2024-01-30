import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const useAccumulateStore = defineStore("accumulate", () => {
  // =========== STATE ===============

  const chatRoomList = ref([]);
  const chat = ref([]);

  // =========== GETTER ===============

  const getChatRoomList = computed(() => {
    return chatRoomList.value;
  });

  const getChat = computed(() => {
    return chat.value;
  });

  // =========== ACTION ===============
  const today = function () {
    axios({
      url: `${import.meta.env.VITE_REST_ACCUMULATE_API}/today`,
      method: "GET",
    })
      .then((res) => {
        acuumulateToday.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const duration = function () {
    axios({
      url: `${import.meta.env.VITE_REST_ACCUMULATE_API}/duration`,
      method: "GET",
    })
      .then((res) => {
        acuumulateList.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // date example) 202401 (연도+월)
  const month = function (date) {
    axios({
      url: `${import.meta.env.VITE_REST_ACCUMULATE_API}/month`,
      method: "GET",
      params: { ym: date },
    })
      .then((res) => {
        acuumulateMonth.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // date example) 20240123 (연도+월일)
  const day = function (date) {
    axios({
      url: `${import.meta.env.VITE_REST_ACCUMULATE_API}/day`,
      method: "GET",
      params: { date: date },
    })
      .then((res) => {
        acuumulateDay.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return {
    acuumulateList,
    acuumulateMonth,
    acuumulateToday,
    acuumulateDay,
    getAcuumulateList,
    getAcuumulateMonth,
    getAcuumulateToday,
    getAcuumulateDay,
    today,
    duration,
    month,
    day,
  };
});
