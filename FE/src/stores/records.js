import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { responseState } from "./util";
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

  const recordDay = ref(null);
  const recordDate = ref(null);
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

  const getRecordDay = computed(() => {
    return recordDay.value;
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
        
      });
  };

  const updateCafeDrink = function (drink) {
    axios({
      url: `${import.meta.env.VITE_REST_RECORDS_API}/drink`,
      method: "PUT",
      data: drink,
    })
      .then((res) => {})
      .catch((err) => {
        
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
        
      });
  };

  const deleteDrink = async function (recordId) {
    await axios({
      url: `${import.meta.env.VITE_REST_RECORDS_API}/${recordId}`,
      method: "DELETE",
    })
      .then((res) => {
      })
      .catch((err) => {
        
      });
  };

  // date example) 20240124
  const researchDayDrink = function (date) {
    axios({
      url: `${import.meta.env.VITE_REST_RECORDS_API}/${date.value}/day`,
      method: "GET",
    })
      .then((res) => {
        if(res.status == responseState.SUCCESS) {
          
        }

        dayDrink.value = res.data;
      })
      .catch((err) => {
        
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
    recordDay,
    recordDate,
    getDayDrink,
    getCafeDrink,
    getMyDrink,
    getRecordDate,
    getRecordDay,
    createCafeDrink,
    createMyDrink,
    updateCafeDrink,
    updateMyDrink,
    deleteDrink,
    researchDayDrink,
  };
}, {persist:true});
