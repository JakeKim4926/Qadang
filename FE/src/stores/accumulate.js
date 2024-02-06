import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { responseState } from "./util";
import router from "@/router";
import axios from "axios";

export const useAccumulateStore = defineStore("accumulate", () => {
  // =========== STATE ===============

  const accumulateList = ref([]);
  const accumulateMonth = ref([]);
  const accumulateTemp = ref([]);

  const accumulateToday = ref({});
  const accumulateDay = ref({});

  // =========== GETTER ===============

  const getAccumulateList = computed(() => {
    return accumulateList.value;
  });

  const getAccumulateMonth = computed(() => {
    return accumulateMonth.value;
  });

  const getAccumulateToday = computed(() => {
    return accumulateToday.value;
  });

  const getAccumulateDay = computed(() => {
    return accumulateDay.value;
  });

  const getAccumulateTemp = computed(() => {
    return accumulateTemp.value;
  });

  // =========== ACTION ===============
  // params는 요청과 함께 전송되는 파라미터 (쿼리스트링)
  // data는 요청 바디로 전송될 데이터 (JSON)
  const today = function () {
    axios({
      url: `${import.meta.env.VITE_REST_ACCUMULATE_API}/today`,
      method: "GET",
    })
      .then((res) => {
        accumulateToday.value = res.data;
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
        accumulateList.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // date example) 202401 (연도+월)
  const month = async function (date) {
    await axios({
      url: `${import.meta.env.VITE_REST_ACCUMULATE_API}/month`,
      method: "GET",
      params: { ym: date.value },
    })
      .then((res) => {
        if (res.status == responseState.SUCCESS) {
          console.log("success ", res.data);
          accumulateMonth.value = res.data;
          if(accumulateMonth != undefined)
            sessionStorage.setItem('calendarMonth', JSON.stringify(accumulateMonth.value));
        }
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
      params: { date: date.value },
    })
      .then((res) => {
        if (res.status == responseState.SUCCESS) {
          accumulateDay.value = res.data;
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return {
    accumulateTemp,
    accumulateList: accumulateList,
    accumulateMonth: accumulateMonth,
    accumulateToday: accumulateToday,
    accumulateDay: accumulateDay,
    getAccumulateList: getAccumulateList,
    getAccumulateMonth: getAccumulateMonth,
    getAccumulateToday: getAccumulateToday,
    getAccumulateDay: getAccumulateDay,
    getAccumulateTemp,
    today,
    duration,
    month,
    day,
  };
});
