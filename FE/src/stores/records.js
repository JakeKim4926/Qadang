import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const useRecordsStore = defineStore("records", () => {
  // =========== STATE ===============

  const days = ref(['일', '월', '화', '수', '목', '금', '토']);
  const dayDrink = ref([]);

  const cafeDrink = ref({});
  const myDrink = ref({});
  const maxSugar = ref({});
  const maxCaffeine = ref({});

  const recordDate = ref("");
  // =========== GETTER ===============

  const getDayDrink = computed(() => {
    return dayDrink.value;
  });

  const getCafeDrink = computed(() => {
    return cafeDrink.value;
  });

  const getMyDrink = computed(() => {
    return myDrink.value;
  });

  const getMaxSugar = computed(() => {
    return maxSugar.value;
  });

  const getMaxCaffeine = computed(() => {
    return maxCaffeine.value;
  });

  const getRecordDate = computed(() => {
    return recordDate.value;
  });

  // =========== ACTION ===============
  // params는 요청과 함께 전송되는 파라미터 (쿼리스트링)
  // data는 요청 바디로 전송될 데이터 (JSON)
  const createCafeDrink = function (drink) {
    axios({
      url: `${import.meta.env.VITE_REST_RECORDS_API}/drink`,
      method: "POST",
      data: drink,
    })
      .then((res) => {})
      .catch((err) => {
        console.log(err);
      });
  };

  const createMyDrink = function (drink) {
    axios({
      url: `${import.meta.env.VITE_REST_RECORDS_API}/make`,
      method: "POST",
      data: drink,
    })
      .then((res) => {})
      .catch((err) => {
        console.log(err);
      });
  };

  const updateCafeDrink = function (drink) {
    axios({
      url: `${import.meta.env.VITE_REST_RECORDS_API}/drink`,
      method: "",
      data: drink,
    })
      .then((res) => {})
      .catch((err) => {
        console.log(err);
      });
  };

  const updateMyDrink = function (drink) {
    axios({
      url: `${import.meta.env.VITE_REST_RECORDS_API}/make`,
      method: "PUT",
      data: drink,
    })
      .then((res) => {})
      .catch((err) => {
        console.log(err);
      });
  };

  const deleteDrink = function (recordId) {
    axios({
      url: `${import.meta.env.VITE_REST_RECORDS_API}`,
      method: "DELETE",
      params: { recordId: recordId },
    })
      .then((res) => {})
      .catch((err) => {
        console.log(err);
      });
  };

  // date example) 20240124
  const researchDayDrink = function (date) {
    axios({
      url: `${import.meta.env.VITE_REST_RECORDS_API}/day`,
      method: "GET",
      params: { date: date },
    })
      .then((res) => {
        dayDrink.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const researchMaxSugar = function () {
    axios({
      url: `${import.meta.env.VITE_REST_RECORDS_API}/maxsugar`,
      method: "GET",
    })
      .then((res) => {
        maxSugar.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const researchMaxCaffeine = function () {
    axios({
      url: `${import.meta.env.VITE_REST_RECORDS_API}/maxcaffeine`,
      method: "GET",
    })
      .then((res) => {
        maxCaffeine.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return {
    days,
    dayDrink,
    cafeDrink,
    myDrink,
    maxSugar,
    maxCaffeine,
    dayDrink,
    recordDate,
    getDayDrink,
    getCafeDrink,
    getMyDrink,
    getMaxSugar,
    getMaxCaffeine,
    getRecordDate,
    createCafeDrink,
    createMyDrink,
    updateCafeDrink,
    updateMyDrink,
    deleteDrink,
    researchDayDrink,
    researchMaxSugar,
    researchMaxCaffeine,
  };
}, {persist:true});
