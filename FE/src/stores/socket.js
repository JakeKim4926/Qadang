import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { io } from "socket.io-client";
import router from "@/router";
import axios from "axios";

export const messageType = {
    ENTER: 0,
    QUIT: 1,
    TALK: 2,
}

export const useSocketStore = defineStore("socket", () => {
    // =========== STATE ===============

    const chatRoomId = ref(0);
    const senderId = ref(0);
    const sendMessage = ref("");

    const id = ref(0);
    const chatMessages = ref([]);
    const connected = ref(false);
    // const socket = io('ws://localhost:8080/ws/chat');
    const socket = io('http://localhost:3000');

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
        getchatRoomId,
        getSenderId,
        getSendMessage,
        getChatMessages,
        getConnected,
    };
});
