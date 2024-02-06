import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { responseState } from "./util";
import router from "@/router";
import axios from "axios";

export const useChatStore = defineStore("chat", () => {
  // =========== STATE ===============

  const chatRoomList = ref([]);
  const chatList = ref([]);

  // =========== GETTER ===============

  const getChatRoomList = computed(() => {
    return chatRoomList.value;
  });

  const getChatList = computed(() => {
    return chatList.value;
  });

  // =========== ACTION ===============
  const researchChatRoomList = function () {
    axios({
      url: `${import.meta.env.VITE_REST_CHAT_API}/chatroom`,
      method: "GET",
    })
      .then((res) => {
        if (res.status == responseState.SUCCESS) {
          chatRoomList.value = res.data;
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const researchChatList = function (chatId) {
    axios({
      url: `${import.meta.env.VITE_REST_CHAT_API}/${chatId}`,
      method: "GET",
    })
      .then((res) => {
        if (res.status == responseState.SUCCESS) {
            chatList.value = res.data;
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return {
    chatRoomList,
    chatList,
    getChatRoomList,
    getChatList,
    researchChatRoomList,
    researchChatList,
  };
});
