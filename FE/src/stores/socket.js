import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { io } from "socket.io-client";
import router from "@/router";
import axios from "axios";
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export const messageType = {
  ENTER: "ENTER",
  QUIT: "QUIT",
  TALK: "TALK",
  CONNECT: "CONNECT",
};

export const testCafeList = ref([
  { id: 1, cafe: "스타벅스" },
  { id: 2, cafe: "메가커피" },
  { id: 3, cafe: "투썸플레이스" },
  { id: 4, cafe: "컴포즈" },
  { id: 5, cafe: "빽다방" },
  { id: 6, cafe: "이디야" },
  { id: 7, cafe: "할리스" },
  { id: 8, cafe: "커피빈" },
  { id: 9, cafe: "파스꾸찌" },
  { id: 10, cafe: "폴바셋" },
  { id: 11, cafe: "더벤티" },
  { id: 12, cafe: "카페베네" },
  { id: 13, cafe: "탐앤탐스" },
  { id: 14, cafe: "매머드커피" },
  { id: 15, cafe: "커피베이" },
]);

export const useSocketStore = defineStore("socket", () => {
  // =========== STATE ===============

  const chatRoomId = ref(0);
  const senderId = ref(0);
  const sendMessage = ref("");

  const id = ref(0);
  const chatMessages = ref([]);
  const connected = ref(false);
  // const socket = io({
  //   transports: ["websocket"],
  //   path: "/ws/chat",
  // });
  const socket = null; 
  const stompClient = null;
  //   const socket = io(`${import.meta.env.VITE_SOCKET_API}`);
  // const socket = io('http://localhost:3000');

  // =========== GETTER ===============

  const getchatRoomId = computed(() => {
    return chatRoomId.value;
  });

  const getSenderId = computed(() => {
    return senderId.value;
  });

  const getSendMessage = computed(() => {
    return sendMessage.value;
  });

  const getChatMessages = computed(() => {
    return chatMessages.value;
  });

  const getConnected = computed(() => {
    return connected.value;
  });

  return {
    id,
    chatRoomId,
    senderId,
    sendMessage,
    chatMessages,
    connected,
    socket,
    stompClient,
    getchatRoomId,
    getSenderId,
    getSendMessage,
    getChatMessages,
    getConnected,
  };
});