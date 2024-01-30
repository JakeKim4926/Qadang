<template>
  <div>
    <h2>이야기 나누고 싶은 카페명을 선택하세요</h2>
    <br>
    <select v-model="cafe" @change="sendOpen">
      <option v-for="cafe in testCafeList" :key="cafe.id" :value="cafe">{{ cafe.cafe }}</option>
    </select>

    <template v-if="cafe.id > 0">
      <div class="chat-container">
        <div class="chat-messages" ref="chatContainer">

          <div v-for="chat in chatStore.getChatList" :key="chat.index"
            :class="chat.userId == userId ? 'my-chat' : 'their-chat'">
            <h2> {{ chat.userName }}</h2>
            <div>
              <p class="message">{{ msg.message }}</p>
              <p>{{ extractTimeFromDate(chat.time) }}</p>
            </div>
          </div>

        </div>
        <textarea style="resize: none" v-model="message" class="chat-input" placeholder="메시지 입력"
          @keydown.enter="sendMessage" />
      </div>
    </template>
    <button @click="sendClose">통신 종료</button>
  </div>
</template>
  
<script setup>
import { nextTick, ref, watchEffect, onMounted, computed } from "vue";
import { useSocketStore, messageType, testCafeList } from '@/stores/socket';
import { useChatStore } from '@/stores/chat'

const socketStore = useSocketStore();
const chatStore = useChatStore();

const message = ref('')
const chatContainer = ref(null);
const cafe = ref({})

// for test
const userId = ref(0);


onMounted(() => {

})

// socketStore.socket.on("connect", () => {
//   socketStore.connected = ref(true);
// });

// socketStore.socket.on("disconnect", () => {
//   socketStore.connected = ref(false);
// });

// socketStore.socket.on('chat', (data) => {
//   console.log(data.message)
//   socketStore.chatMessages.push(data)
// })


function extractTimeFromDate(dateTimeString) {
    // dateTimeString에서 공백을 기준으로 분할하여 시간 부분만 추출
    const time = dateTimeString.split(' ')[1];
    return time;
}

async function sendOpen() {
  const chat = {
    messageType: messageType.ENTER,
    chatRoomId: cafe.value.id,
    senderId: userId.value,
    message: "ENTER",
  }

  console.log(chat);
  message.value = "";
  await socketStore.socket.timeout(5000).emit('', chat);
  await chatStore.researchChatList(cafe.value.id);
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
  
  await socketStore.socket.timeout(5000).emit('', chat)
  console.log(chat);
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

  socketStore.socket.timeout(5000).emit('', chat)
}

function adjustTextarea() {
}

function scrollChatToBottom() {
  if (chatContainer.value) {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
  }
}

watchEffect(() => {
  scrollChatToBottom();
  // console.log(socketStore.chatMessages.value)
})

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