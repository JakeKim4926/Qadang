import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const useUserStore = defineStore("user", () => {
  // =========== STATE ===============

  const user = ref({});
  const userId = ref("");
  const userName = ref("");

  const userRDI = ref({}); // RDI - Recommended Daily Intake (당일 권장량)
  const userRDICaffeine = ref(0.0);
  const userRDISugar = ref(0.0);

  const userMax = ref({});
  const userMaxCaffeine = ref(0.0);
  const userMaxCaffeineDate = ref("");
  const userMaxSugar = ref(0.0);
  const userMaxSugarDate = ref("");

  const recommendedCaffeine = ref({});
  const recommendedSugar = ref({});

  // =========== GETTER ===============

  const getUser = computed(() => {
    return user.value;
  });

  const getUserId = computed(() => {
    return userId.value;
  });

  const getUserName = computed(() => {
    return userName.value;
  });

  const getUserRDI = computed(() => {
    return userRDI.value;
  });

  const getUserRDICaffeine = computed(() => {
    return userRDICaffeine.value;
  });

  const getUserRDISugar = computed(() => {
    return userRDISugar.value;
  });

  const getUserMax = computed(() => {
    return userMax.value;
  });

  const getUserMaxCaffeine = computed(() => {
    return userMaxCaffeine.value;
  });

  const getUserMaxCaffeineDate = computed(() => {
    return userMaxCaffeineDate.value;
  });

  const getUserMaxSugar = computed(() => {
    return userMaxSugar.value;
  });

  const getUserMaxSugarDate = computed(() => {
    return userMaxSugarDate.value;
  });

  const getRecommendedCaffeine = computed(() => {
    return recommendedCaffeine.value;
  });

  const getRecommendedSugar = computed(() => {
    return recommendedSugar.value;
  });

  // =========== ACTION ===============

  const sendKakaoToken = function (token) {
    axios({
      url: `${import.meta.env.REST_USER_API}/social-login`,
      method: "POST",
      data: user,
    })
      .then(() => {
        // 카카오 에서 받아온 토큰을 백으로 전달
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const createUser = function (user) {
    axios({
      url: import.meta.env.REST_USER_API,
      method: "POST",
      data: user,
    })
      .then(() => {})
      .catch((err) => {
        console.log(err);
      });
  };

  const researchUser = function () {
    axios({
      url: import.meta.env.REST_USER_API,
      method: "GET",
    })
      .then((res) => {
        user.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const researchAmount = function () {
    axios({
      url: `${import.meta.env.REST_USER_API}/amount`,
      method: "GET",
    })
      .then((res) => {
        userRDI.value = res.data;
        userRDICaffeine.value = userRDI.value.userCaffeine;
        userRDISugar.value = userRDI.value.userSugar;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const researchMax = function () {
    axios({
      url: `${import.meta.env.REST_USER_API}/max`,
      method: "GET",
    })
      .then((res) => {
        userMax.value = res.data;
        userMaxCaffeine.value = userMax.value.maxCaffeineValue;
        userMaxCaffeineDate.value = userMax.value.maxCaffeineDate;
        userMaxSugar.value = userMax.value.maxSugarValue;
        userMaxSugarDate.value = userMax.value.maxSugarDate;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const updateUser = function () {
    axios({
      url: import.meta.env.REST_USER_API,
      method: "PUT",
      data: user.value,
    })
      .then(() => {})
      .catch((err) => {
        console.log(err);
      });
  };

  const deleteUser = function () {
    axios({
      url: import.meta.env.REST_USER_API,
      method: "DELETE",
    })
      .then(() => {})
      .catch((err) => {
        console.log(err);
      });
  };

  const researchRecommendSugar = function () {
    axios({
      url: `${import.meta.env.REST_USER_API}/recommendsugar`,
      method: "GET",
    })
      .then((res) => {
        recommendedSugar.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const researchRecommendCaffeine = function () {
    axios({
      url: `${import.meta.env.REST_USER_API}/recommendcaffeine`,
      method: "GET",
    })
      .then((res) => {
        recommendedCaffeine.value = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const infoFilled = computed(() => {
    return user.value.userHeight && user.value.userHeight !== 0 &&
         user.value.userWeight && user.value.userWeight !== 0 &&
         user.value.userHealth && user.value.userHealth !== 0
})

  return {
    user,
    userId,
    userName,
    userRDI,
    userRDICaffeine,
    userRDISugar,
    userMax,
    userMaxCaffeine,
    userMaxCaffeineDate,
    userMaxSugar,
    userMaxSugarDate,
    recommendedCaffeine,
    recommendedSugar,
    getUser,
    getUserId,
    getUserName,
    getUserRDI,
    getUserRDICaffeine,
    getUserRDISugar,
    getUserMax,
    getUserMaxCaffeine,
    getUserMaxCaffeineDate,
    getUserMaxSugar,
    getUserMaxSugarDate,
    getRecommendedCaffeine,
    getRecommendedSugar,
    sendKakaoToken,
    createUser,
    researchUser,
    researchAmount,
    researchMax,
    updateUser,
    deleteUser,
    researchRecommendSugar,
    researchRecommendCaffeine,
    infoFilled,
  };
});
