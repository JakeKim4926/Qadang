import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const useDiaryStore = defineStore(
  "diary",
  () => {
    // =========== STATE ===============

    const user = ref({});
    const userId = ref('');
    const userName = ref('');

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
        .then(() => {
        })
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
            // res 활용하여 id, nickname 받기
            // 아마 login, logout 으로 나눌 듯
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
        .then(() => {
        })
        .catch((err) => {
          console.log(err);
        });
    };

    const deleteUser = function () {
        axios({
          url: import.meta.env.REST_USER_API,
          method: "DELETE",
        })
          .then(() => {
          })
          .catch((err) => {
            console.log(err);
          });
      };

    return {
      recentDiary,
      getRecentDairy,
      getAxiosRecent,
      diaryList,
      getCurrentPage,
      getTotalPage,
      getPaginatedDiarylist,
      getDiaryList,
      goToPage,
      getList,
    };
  },
  { persist: true }
);
