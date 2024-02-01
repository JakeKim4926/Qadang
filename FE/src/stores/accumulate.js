import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { responseState } from "./util";
import router from "@/router";
import axios from "axios";

export const useAccumulateStore = defineStore("accumulate", () => {
  // =========== STATE ===============

  const acuumulateList = ref([]);
  const acuumulateMonth = ref([]);

  const acuumulateToday = ref({});
  const acuumulateDay = ref({});

  // =========== GETTER ===============

  const getAcuumulateList = computed(() => {
    return acuumulateList.value;
  });

  const getAcuumulateMonth = computed(() => {
    return acuumulateMonth.value;
  });

  const getAcuumulateToday = computed(() => {
    return acuumulateToday.value;
  });

  const getAcuumulateDay = computed(() => {
    return acuumulateDay.value;
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
    console.log(date.value);
    axios({
      url: `${import.meta.env.VITE_REST_ACCUMULATE_API}/month`,
      method: "GET",
      params: { ym: date.value },
    })
      .then((res) => {
        if(res.status == responseState.SUCCESS) {
          console.log("success ", res.data );
        }
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
      params: { date: date.value },
    })
      .then((res) => {
        console.log('get it : ', parseFloat(res.data.accumulateCaffeine));
        console.log(date.value);
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
