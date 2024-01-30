<template>
  <div>
    <h2>이야기 나누고 싶은 카페명을 선택하세요</h2>
    <br />
    <select v-model="cafe" @change="sendOpen">
      <option v-for="cafe in testCafeList" :key="cafe.id" :value="cafe">
        {{ cafe.cafe }}
      </option>
    </select>

    <template v-if="cafe.id > 0">
      <div class="chat-container">
        <div class="chat-messages" ref="chatContainer">
          <div
            v-for="chat in chatStore.getChatList"
            :key="chat.index"
            :class="chat.userId == userId ? 'my-chat' : 'their-chat'"
          >
            <h2>{{ chat.userName }}</h2>
            <div>
              <p class="message">{{ chat.message }}</p>
              <p>{{ extractTimeFromDate(chat.time) }}</p>
            </div>
          </div>
        </div>
        <textarea
          style="resize: none"
          v-model="message"
          class="chat-input"
          placeholder="메시지 입력"
          @keydown.enter="sendMessage"
        />
      </div>
    </template>
    <button @click="sendClose">통신 종료</button>
  </div>
</template>

<script setup>
import { nextTick, ref, watchEffect, onMounted, computed } from "vue";
import { useSocketStore, messageType, testCafeList } from "@/stores/socket";
import { useChatStore } from "@/stores/chat";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

const socketStore = useSocketStore();
const chatStore = useChatStore();

const message = ref("");
const chatContainer = ref(null);
const cafe = ref({});
const receivedMessages = ref([]);
const Msgcnt = ref(0);
// for test
const userId = ref(0);

onMounted(async () => {
  // socketStore.socket = new SockJS(`${import.meta.env.VITE_SOCKET_API}`); // 웹소켓 서버 URL
  socketStore.socket = new SockJS("http://localhost:8080/ws/chat"); // 웹소켓 서버 URL
  socketStore.stompClient = Stomp.over(socketStore.socket);

  socketStore.stompClient.connect(
    {},
    (frame) => {
      console.log("Connected: " + frame);
    },
    (error) => {
      console.log("Connection error: " + error);
    }
  );

  // socketStore.stompClient.debug = function (message) {
  //   if (message.includes("<<<")) {
  //     const index = message.indexOf("<<<");
  //     if (index !== -1) {
  //       message = message.substring(index + 3).trim();
  //     } else {
  //       message = "";
  //     }
  //     receivedMessages.value.push(message);
  //     Msgcnt.value += 1;
  //     console.log(message);
  //   }
  // };
});

function extractTimeFromDate(dateTimeString) {
  // dateTimeString에서 공백을 기준으로 분할하여 시간 부분만 추출
  if (dateTimeString == undefined || dateTimeString.length < 5) return "";
  const time = dateTimeString.split(" ")[1];
  return time;
}

async function sendOpen() {
  const chat = {
    messageType: messageType.ENTER,
    chatRoomId: cafe.value.id,
    senderId: userId.value,
    message: "",
  };

  // Check if the StompClient is connected before sending the message
  if (socketStore.stompClient && socketStore.stompClient.connected) {
    // socketStore.stompClient.send(`${import.meta.env.VITE_SOCKET_API}`, {}, JSON.stringify(chat));
    socketStore.stompClient.send(`${import.meta.env.VITE_SOCKET_API}`, {}, chat);
  } else {
    console.error("StompClient is not connected.");
  }

  console.log(chat);
  message.value = "";
  // await chatStore.researchChatList(cafe.value.id);
  // 스크롤을 새 메시지 아래로 이동시킵니다.
  nextTick(() => {
    scrollChatToBottom();
  });
}

async function sendMessage() {
  const chat = {
    messageType: messageType.TALK,
    chatRoomId: cafe.value.id,
    senderId: userId.value,
    message: message.value,
  }

  // Check if the StompClient is connected before sending the message
  if (socketStore.stompClient && socketStore.stompClient.connected) {
    // socketStore.stompClient.send(`${import.meta.env.VITE_SOCKET_API}`, {}, JSON.stringify(chat));
    socketStore.stompClient.send(`${import.meta.env.VITE_SOCKET_API}`, {}, chat);
  } else {
    console.error("StompClient is not connected.");
  }

  socketStore.stompClient.subscribe(`${import.meta.env.VITE_SOCKET_API}`, (message) => {
    // Handle the incoming message
    console.log("Received message:", JSON.parse(message.body));
    console.log("Received message:", message.body);
    
    // Here, you can update your UI or perform any other actions based on the received message
  });

  console.log(chat);
  message.value = "";
  // 스크롤을 새 메시지 아래로 이동시킵니다.
  nextTick(() => {
    scrollChatToBottom();
  });
}

// async function sendClose() {
//   const chat = {
//     messageType: messageType.QUIT,
//     chatRoomId: cafe.value.id,
//     senderId: userId.value,
//     message: "QUIT",
//   }

//   console.log(chat);

//   socketStore.socket.timeout(5000).emit('', chat)
// }

function adjustTextarea() {}

function scrollChatToBottom() {
  if (chatContainer.value) {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
  }
}

watchEffect(() => {
  scrollChatToBottom();
  // console.log(socketStore.chatMessages.value)
});
</script>

<style scoped>
.chat-container {
  border: 1px solid cornflowerblue;
  display: flex;
  flex-direction: column;
  height: 80vh;
  padding: 20px;
}

.chat-messages {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: auto;
}

.my-chat {
  align-self: flex-end;
  background-color: #0084ff;
  color: #fff;
  max-width: 70%;
  padding: 8px;
  margin: 8px 8px 8px 0;
  border-radius: 6px;
  text-align: right;
  position: relative;
}

.their-chat {
  align-self: flex-start;
  background-color: #f0f0f0;
  max-width: 70%;
  padding: 8px;
  margin: 8px 0;
  border-radius: 6px;
  text-align: left;
  color: #282828;
  position: relative;
}

.chat-input {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

input[type="text"] {
  flex: 1;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-right: 10px;
}

button {
  background-color: #0084ff;
  color: #fff;
  border: none;
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
}
</style>
