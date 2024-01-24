import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const useuserStore = defineStore("user", () => {
  // =========== STATE ===============

  const user = ref({});
  const userId = ref("");
  const userName = ref("");

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

  // =========== ACTION ===============

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
      method: "get",
    })
      .then((res) => {
        user.value = res.data;
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

  const isAdditionalInfoFilled = computed(() => {
    return user.value.additionalInfo && user.value.additionalInfo.trim() !== "";
  }); // 추가정보가 입력되어있는지 아닌지 확인하기 위한 함수

  return {
    user,
    userId,
    userName,
    getUser,
    getUserId,
    getUserName,
    createUser,
    researchUser,
    updateUser,
    deleteUser,
    isAdditionalInfoFilled,
  };
});
