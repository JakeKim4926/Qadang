<template>
  <div class="container">
    <div class="title">이야기 나누고 싶은 카페명을 선택하세요</div>
    <br />
    <select v-model="cafe" @change="sendOpen" class="select-cafe" :disabled="selectCafe">
      <option v-for="cafe in drinkStore.getCafeList" :key="cafe.id" :value="cafe">
        {{ cafe.cafeName }}
      </option>
    </select>

    <template v-if="cafe.cafeId > 0">
      <div class="chat-container">
        <div class="chat-messages" ref="chatContainer">
          <div v-for="(chat, index) in chatStore.getChatList.value" :key="chat.index">
            <template v-if="chat.userName == userStore.getUserName">
              <div class="my-chat">
                <p class="time">{{ extractTimeFromDate(chat.time) }}</p>
                <div class="message">{{ chat.message }}</div>
              </div>
            </template>
            <div v-else class="their-chat">
              <div class="nickname" :class="'nickname-' + getNicknameIndex(chat.userName)">
                {{ chat.userName }}
              </div>
              <p class="message">{{ chat.message }}</p>
              <p class="time">{{ extractTimeFromDate(chat.time) }}</p>
            </div>
          </div>
        </div>
        <hr class="chat-line" />
        <div class="chat-input-container">
          <a @click="sendMessage" class="chat-icon">
            <font-awesome-icon :icon="['fas', 'paper-plane']" style="color: #000000" />
          </a>
          <textarea v-model="message" class="chat-input" :placeholder="`${userStore.getUserName}로 대화 시작`"
            @keydown.enter.prevent="sendMessage" />
        </div>
      </div>
    </template>
    <template v-else>
      <div class="chat-container-disable">
        <hr class="chat-line-disable" />
        <div class="chat-icon-disable">
          <font-awesome-icon :icon="['fas', 'paper-plane']" style="color: #000000" />
        </div>
      </div>
    </template>
    <RouterLink to="/mainCaffeine" @click="sendClose()" class="leave-chat-button">나가기</RouterLink>
    <!-- <button @click="sendClose">통신 종료</button> -->
  </div>
</template>

<script setup>
import { nextTick, ref, watchEffect, onMounted, computed, watch } from "vue";
import { useSocketStore, messageType, testCafeList } from "@/stores/socket";
import { useChatStore } from "@/stores/chat";
import { useUserStore } from "@/stores/user";
import { isSocketConnected } from "@/stores/util";

import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { useDrinksStore } from "@/stores/drinks";

const socketStore = useSocketStore();
const chatStore = useChatStore();
const userStore = useUserStore();
const drinkStore = useDrinksStore();

const selectCafe = ref(false);
const message = ref("");
const chatContainer = ref(null);
const cafe = ref({});
const receivedMessages = ref([]);
const Msgcnt = ref(0);
const showMessages = ref(false);
let socket = null;
let colorIndex = 0;
// for test
const userId = ref(1);

const userNameIndexMap = {
  "username1": 1,
  "username2": 2,
};

// 페이지가 언로드될 때 연결을 닫습니다.
window.addEventListener('beforeunload', () => {
  if (socket && socket.readyState === WebSocket.OPEN) {
    sendClose();
  }
});

function getNicknameIndex(userName) {
  if (userName in userNameIndexMap) {
    // 사용자 이름이 맵 안에 있으면 해당 인덱스 반환
    return userNameIndexMap[userName];
  } else {
    // 사용자 이름이 맵 안에 없으면 랜덤 인덱스 생성 후 맵에 추가
    colorIndex += 1;
    if (colorIndex > 7)
      colorIndex = 1;
    userNameIndexMap[userName] = colorIndex; // 새로운 사용자 이름과 해당 인덱스를 맵에 추가
    return colorIndex;
  }
}

// watch(chatStore.chatList, () => {
//   chatStore.researchChatList(cafe.value.cafeId);
// });

onMounted(async () => {
  await drinkStore.researchCafe();
  await userStore.researchName();

});

function extractTimeFromDate(dateTimeString) {
  // dateTimeString에서 공백을 기준으로 분할하여 시간 부분만 추출
  if (dateTimeString == undefined || dateTimeString.length < 5) return "";

  const minute =
    dateTimeString.split(":")[0].slice(-2) + ":" + dateTimeString.split(":")[1];

  return minute;
}

async function sendOpen() {
  // 연결 시 ENTER 메시지 보내기
  isSocketConnected.value = true;
  await chatStore.researchChatList(cafe.value.cafeId);

  // 1. open your url

  // 소켓 연결 생성 및 세션 스토리지에 저장
  socket = new WebSocket(`${import.meta.env.VITE_SOCKET_API}`);

  // sendClose();
  socket.onopen = () => {
    const token = localStorage.getItem('userAccessToken');
    const enterMessage = {
      messageType: "ENTER", // 입장 메시지 타입
      chatRoomId: cafe.value.cafeId, // 채팅 방 ID
      senderId: cafe.value.userId++, // 보낸 사람 ID
      message: token, // 메시지 내용은 비어 있어도 됩니다.
    };
    socket.send(JSON.stringify(enterMessage));
    scrollChatToBottom();
  };

  // 소켓 연결 생성 및 세션 스토리지에 저장
  sessionStorage.setItem('socket-room', cafe.value.cafeId);

  socket.onmessage = (event) => {

    const message = JSON.parse(event.data);
    const getTime = new Date();
    const currentTime = getTime.toTimeString();
    if (message.message == "" || message.message == null)
      return;
    const chat = {
      userId: message.senderId,
      userName: message.userName,
      message: message.message,
      time: currentTime,
    }
    if (message.userName != userStore.getUserName) {
      chatStore.chatList.push(chat);
    }
    scrollChatToBottom();
  };

  socket.onerror = function (event) {
    console.error("WebSocket connection error:", event);
  };

  // 연결이 닫힐 때의 핸들링
  socket.onclose = (event) => {
    console.log("WebSocket connection closed: ", event);
  };


  await nextTick(() => {
    scrollChatToBottom();
  });
  selectCafe.value = true;
}

async function sendMessage() {
  const enterMessage = {
    messageType: messageType.TALK,
    chatRoomId: cafe.value.cafeId,
    senderId: cafe.value.cafeId,
    message: message.value,
  };

  socket.send(JSON.stringify(enterMessage));

  const getTime = new Date();
  const currentTime = getTime.toTimeString();
  const chat = {
    userId: enterMessage.senderId,
    userName: userStore.getUserName,
    message: enterMessage.message,
    time: currentTime,
  }

  chatStore.chatList.push(chat);
  message.value = "";
  // 스크롤을 새 메시지 아래로 이동시킵니다.


  nextTick(() => {
    scrollChatToBottom();
  });
}

async function sendClose() {
  const chat = {
    messageType: messageType.QUIT,
    chatRoomId: cafe.value.id,
    senderId: userId.value,
    message: "QUIT",
  }

  console.log(chat);
  if (cafe.value.cafeId > 0 && socket.readyState === WebSocket.OPEN) {
    socket.send(JSON.stringify(chat));
  }

}

function adjustTextarea() { }

async function scrollChatToBottom() {
  if (chatContainer.value) {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
  }
}

// watch(chatStore.chatList, () => {
//   // chatStore.getChatList의 길이가 변할 때마다 스크롤을 맨 아래로 내리는 함수를 호출합니다.
//   scrollChatToBottom();
// });

watch(cafe, () => {
  if (cafe != undefined)
    scrollChatToBottom();
});
</script>

<style scoped>
.container {
  width: 80%;
  height: 80%;
  /* 컴포넌트의 높이를 화면의 80%로 지정 */
  position: absolute;
  top: 45%;
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
  background: #ffffff;
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
  width: fit-content;
  /* 메시지 내용에 맞게 최대 너비 설정 */
  max-width: 50%;
  right: 43%;
  /* 메시지 최대 너비 설정 */
  position: relative;
  word-wrap: break-word;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-top: 15px;
  margin-bottom: 15px;
}

.my-chat .time {
  left: -40px;
  /* 왼쪽으로 시간 텍스트를 이동 */
  top: 10%;
  /* 상단을 기준으로 정렬 */
  transform: translateY(50%);
  /* 수직으로 중앙으로 이동 */
}

.their-chat {
  position: relative;
  padding: 5px 15px;
  /* 닉네임을 위한 여백 추가 */
  margin: 4px 15px;
  margin-bottom: 20px;
  margin-top: 30px;
  width: fit-content;
  /* 메시지 내용에 맞게 최대 너비 설정 */
  max-width: 50%;
  background-color: #f5f5f5;
  border-radius: 20px;
  color: black;
  word-wrap: break-word;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.their-chat .time {
  right: -45px;
  /* 오른쪽으로 시간 텍스트를 이동 */
  top: 50%;
  /* 상단을 기준으로 정렬 */
  transform: translateY(35%);
  /* 수직으로 중앙으로 이동 */
}

.nickname {
  position: absolute;
  top: -25px;
  /* 닉네임의 상단 여백 조절 */
  left: 10px;
  font-size: 17px;
  line-height: 18px;
  font-weight: 700;
  width: 250px;
  background-color: transparent;
  /* 배경색 투명하게 설정 */
}

.nickname-1 {
  color: pink;
}

.nickname-2 {
  color: orange;
}

.nickname-3 {
  color: #846046;
}

.nickname-4 {
  color: green;
}

.nickname-5 {
  color: blue;
}

.nickname-6 {
  color: navy;
}

.nickname-7 {
  color: purple;
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

.leave-chat-button {
  position: absolute;
  right: 11%;
  bottom: -10%;
  padding: 10px;
  background: #B29F91;
  color: #EFEFEF;
  border: none;
  text-decoration: none;
  border-radius: 30px;
  font-size: 100%;
  font-weight: 700;
  width: 10%;
  text-align: center;
  cursor: pointer;
}

/* 
button {
  background-color: #0084ff;
  color: #fff;
  border: none;
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
} */
</style>
