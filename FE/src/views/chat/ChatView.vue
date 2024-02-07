<template>
  <div class="container">
    <div class="title">이야기 나누고 싶은 카페명을 선택하세요</div>
    <br />
    <select v-model="cafe" @change="sendOpen" class="select-cafe">
      <option v-for="cafe in testCafeList" :key="cafe.id" :value="cafe">
        {{ cafe.cafe }}
      </option>
    </select>

    <template v-if="cafe.id > 0">
      <div class="chat-container">
        <div v-if="showMessages" class="chat-messages" ref="chatContainer">
          <div v-for="chat in chatStore.getChatList" :key="chat.index">
            <template v-if="chat.userId == userId">
              <!-- <p class="time">{{ extractTimeFromDate(chat.time) }}</p> -->
              <div class="my-chat">
                <p class="time">{{ extractTimeFromDate(chat.time) }}</p>
                <div class="message">{{ chat.message }}</div>
              </div>
            </template>
            <div v-else class="their-chat">
              <!-- <p>{{ extractTimeFromDate(chat.time) }}</p> -->
              <div class="nickname" :class="'nickname-' + ((chat.userId % 7) + 1)">{{ chat.userName }}</div>
              <p class="message">{{ chat.message }}</p>
              <p class="time">{{ extractTimeFromDate(chat.time) }}</p>
            </div>
          </div>
        </div>
        <hr class="chat-line">
        <div class="chat-input-container">
          <a class="chat-icon">
            <font-awesome-icon :icon="['fas', 'paper-plane']" style="color: #000000;" />
          </a>
          <textarea v-model="message" class="chat-input" placeholder="Jake로 대화 시작" @keydown.enter.prevent="sendMessage" />
        </div>
      </div>
    </template>
    <template v-else>
      <div class="chat-container-disable">
        <hr class="chat-line-disable">
        <div class="chat-icon-disable">
          <font-awesome-icon :icon="['fas', 'paper-plane']" style="color: #000000;" />
        </div>
      </div>
    </template>
    <!-- <button @click="sendClose">통신 종료</button> -->
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
const showMessages = ref(false);
// for test
const userId = ref(1);

onMounted(async () => {
  // console.log(import.meta.env.VITE_SOCKET_API)
  // socketStore.socket = new SockJS(import.meta.env.VITE_SOCKET_API); // 웹소켓 서버 URL
  socketStore.socket = new SockJS("http://localhost:8080/ws/chat"); // 웹소켓 서버 URL
  socketStore.stompClient = Stomp.over(socketStore.socket);
  showMessages.value = true;

  const chat = {
    messageType: messageType.ENTER,
    chatRoomId: 1,
    senderId: 2,
    message: "a",
  };

  await socketStore.stompClient.connect(chat,
    (frame) => {
      // After successful connection, send the chat DTO

      console.log("frame :" + frame);
      console.log("hihihihi");
      console.log("Connection :");

      socketStore.stompClient.subscribe("http://localhost:8080/ws/chat", function (message) {
        if (message.body) {
          console.log(message.body);
        } else {
          console.log("nothing on message");
        }
      });
      const chat = {
        messageType: messageType.ENTER,
        chatRoomId: cafe.value.id,
        senderId: userId.value,
        message: "a",
      };
      let messageToSend = JSON.stringify(chat);
      messageToSend = JSON.parse(messageToSend)
      socketStore.stompClient.send("http://localhost:8080/ws/chat", chat);

    },
    (error) => {
      console.log("Connection error: " + error);
      chatStore.getChatList.push({
        index: 1,
        userId: 1,
        userName: 'user01',
        message: 'I love drink',
        time: '2024-02-07 12:13:00' 
      });

      chatStore.getChatList.push({
        index: 1,
        userId: 2,
        userName: 'user02',
        message: 'me tooddddddddddddddddddddddddddddddddddddd',
        time: '2024-02-07 12:16:03' 
      });

      chatStore.getChatList.push({
        index: 1,
        userId: 3,
        userName: 'user03',
        message: 'I love drinkasdsadsadsad',
        time: '2024-02-07 13:23:05' 
      });
 
      chatStore.getChatList.push({
        index: 1,
        userId: 4,
        userName: 'user04',
        message: '안녕하세요~ aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',
        time: '2024-02-07 14:33:07'
      });

      chatStore.getChatList.push({
        index: 1,
        userId: 1,
        userName: 'user01',
        message: '도배하지마',
        time: '2024-02-07 15:34' 
      });

      chatStore.getChatList.push({
        index: 1,
        userId: 5,
        userName: 'user05',
        message: 'There was no other way',
        time: '2024-02-07 16:53:07'
      });

      chatStore.getChatList.push({
        index: 1,
        userId: 6,
        userName: 'user06',
        message: '하이요',
        time: '2024-02-07 17:12:07'
      });

      chatStore.getChatList.push({
        index: 1,
        userId: 7,
        userName: 'user07',
        message: '재밌넹',
        time: '2024-02-07 17:17:07'
      });
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
  const minute = time.split(":")[0] + ":" + time.split(":")[1]
  return minute;
}

async function sendOpen() {
  const chat = {
    messageType: messageType.ENTER,
    chatRoomId: cafe.value.id,
    senderId: userId.value,
    message: "a",
  };
  // let messageToSend = JSON.stringify(chat);
  // messageToSend = JSON.parse(messageToSend)
  socketStore.stompClient.send("http://localhost:8080/ws/chat", chat);
  socketStore.stompClient.subscribe("http://localhost:8080/ws/chat", function (message) {
    if (message.body) {
      console.log(message.body);

    } else {
      console.log("nothing on message");
    }
  });
  // Check if the StompClient is connected before sending the message
  if (socketStore.stompClient && socketStore.stompClient.connected) {
    // socketStore.stompClient.send(`${import.meta.env.VITE_SOCKET_API}`, {}, JSON.stringify(chat));
    // const messageToSend = JSON.stringify(chat);
    //   socketStore.stompClient.send("http://localhost:8080/ws/chat", messageToSend);
  } else {
    console.error("StompClient is not connected.");
  }
  socketStore.stompClient.send(`${import.meta.env.VITE_SOCKET_API}`, {}, JSON.stringify(chat));
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

function adjustTextarea() { }

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
.container {
  width: 80%;
  height: 80%;
  /* 컴포넌트의 높이를 화면의 80%로 지정 */
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.title {
  color: #562b1a;
  text-align: center;
  font-family: "Roboto-Bold", sans-serif;
  font-size: 30px;
  line-height: 140%;
  font-weight: 700;
  position: absolute;
  width: 100.46%;
  bottom: 94.39%;
  top: 0%;
  height: 5.61%;
  margin: 0 auto;
}

.select-cafe {
  background: #ffffff;
  text-align: center;
  border-radius: 90px;
  border-style: solid;
  border-color: #846046;
  border-width: 2px;
  position: relative;
  left: 30%;
  width: 40.89%;
  bottom: 50.68%;
  top: 10.29%;
  height: 5.02%;
  margin: 0 auto;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 20px;
  line-height: 18px;
  font-weight: 700;
}


.chat-container {
  background: #FFFFFF;
  border-radius: 30px;
  border-style: solid;
  border-color: #d9d9d9;
  border-width: 1px;
  position: absolute;
  right: 0%;
  left: 10%;
  width: 80%;
  bottom: 3%;
  top: 23.8%;
  height: 76.2%;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  display: flex;
  flex-direction: column;
  /* 채팅 컨테이너를 열 방향으로 배치합니다. */
  /* align-items: flex-end; */
  bottom: 10%;
  /* 채팅 입력란과 겹치지 않게 조정합니다. */
}

.chat-container-disable {
  background-color: #d9d9d9;
  border-radius: 30px;
  border-style: solid;
  border-color: #d9d9d9;
  border-width: 1px;
  position: absolute;
  right: 0%;
  left: 10%;
  width: 80%;
  bottom: 0%;
  top: 23.8%;
  height: 76.2%;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.chat-line {
  border-style: solid;
  border-color: #f0f0f0;
  border-width: 1px 0 0 0;
  position: absolute;
  right: 0%;
  left: 0%;
  width: 100%;
  bottom: 9.36%;
  top: 90.64%;
  height: 0%;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);

}

.chat-line-disable {
  border-style: solid;
  border-color: #e5e5e5;
  border-width: 1px 0 0 0;
  position: absolute;
  right: 0%;
  left: 0%;
  width: 100%;
  bottom: 9.36%;
  top: 90.64%;
  height: 0%;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);

}


.chat-icon {
  position: absolute;
  right: 4.12%;
  left: 90.84%;
  bottom: 2.54%;
  top: 95.05%;
  cursor: pointer;
  font-size: 20px;
  line-height: 18px;
  font-weight: 400;
  text-align: right;
}

.chat-icon-disable {
  position: absolute;
  right: 4.12%;
  left: 90.84%;
  bottom: 2.54%;
  top: 95.05%;
  font-size: 20px;
  line-height: 18px;
  font-weight: 400;
  text-align: right;
  justify-content: flex-end; 
}

.chat-messages {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: auto;
  margin-bottom: 70px;
  /* 최초에 채팅 메시지 아래에 간격을 둡니다. */
}

.time {
  font-size: 12px;
  color: #999;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}


.my-chat {
  align-self: flex-end;
  text-align: left;
  /* background: linear-gradient(135deg, #4CAF50, #2E7D32); */
  background-color: #f5f5f5;
  border-radius: 20px;
  color: black;
  padding: 10px 15px;
  margin: 0px -41% 0px auto;
  width: fit-content; /* 메시지 내용에 맞게 최대 너비 설정 */
  max-width: 50%;
  right : 43%;
  /* 메시지 최대 너비 설정 */
  position: relative;
  word-wrap: break-word;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-top: 15px;
  margin-bottom: 15px;
}

.my-chat .time {
  left: -40px; /* 왼쪽으로 시간 텍스트를 이동 */
  top: 10%; /* 상단을 기준으로 정렬 */
  transform: translateY(50%); /* 수직으로 중앙으로 이동 */
}


.their-chat {
  position: relative;
  padding: 5px 15px; /* 닉네임을 위한 여백 추가 */
  margin: 4px 15px;
  margin-bottom: 20px;
  margin-top: 20px;
  width: fit-content; /* 메시지 내용에 맞게 최대 너비 설정 */
  max-width: 50%;
  background-color: #f5f5f5;
  border-radius: 20px;
  color: black;
  word-wrap: break-word;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.their-chat .time {
  right: -45px; /* 오른쪽으로 시간 텍스트를 이동 */
  top: 50%; /* 상단을 기준으로 정렬 */
  transform: translateY(35%); /* 수직으로 중앙으로 이동 */
}

.nickname {
  position: absolute;
  top: -25px; /* 닉네임의 상단 여백 조절 */
  left: 10px;
  font-size: 17px;
  line-height: 18px;
  font-weight: 700;
  background-color: transparent; /* 배경색 투명하게 설정 */
}

.nickname-1 {
  color:pink;
}
.nickname-2 {
  color:orange;
}
.nickname-3 {
  color:#846046;
}
.nickname-4 {
  color:green;
}
.nickname-5 {
  color:blue;
}
.nickname-6 {
  color:navy;
}
.nickname-7 {
  color:purple;
}


.chat-input {
  color: black;
  text-align: left;
  font-family: "DmSans-Regular", sans-serif;
  font-size: 20px;
  line-height: 18px;
  font-weight: 400;
  position: absolute;
  right: 68.09%;
  left: 3.73%;
  width: 85.18%;
  bottom: 3.48%;
  top: 94.52%;
  height: 4.41%;
  border: none;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  resize: none;
  overflow: hidden;
}

.chat-input:focus {
  outline: none;
}

.chat-input-container {
  display: flex;
  align-items: center;
}

input[type="text"] {
  flex: 1;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-right: 10px;
}

/* 
button {
  background-color: #0084ff;
  color: #fff;
  border: none;
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
} */</style>
