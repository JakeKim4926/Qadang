<template>
  <div>
    <h2>이야기 나누고 싶은 카페명을 선택하세요</h2>
    <br>
    <v-select label="Select" :items="['California', 'Colorado', 'Florida', 'Georgia', 'Texas', 'Wyoming']"
      variant="outlined"></v-select>
    <div class="chat-container">
      <div class="chat-messages" ref="chatContainer">
        <div v-for="(msg, index) in socketStore.getChatMessages" :key="index"
          :class="msg.owner == id ? 'my-chat' : 'their-chat'">
          <div class="message">{{ msg.message }}</div>
        </div>
      </div>
      <textarea style="resize: none" v-model="message" class="chat-input" placeholder="메시지 입력"
        @keydown.enter="sendMessage" />
    </div>

  </div>
</template>
  
<script setup>
import { nextTick, ref, watchEffect, onMounted } from "vue";
import { useSocketStore, messageType } from '@/stores/socket';

const socketStore = useSocketStore();
const message = ref('')
const chatContainer = ref(null);

onMounted(() => {
  const randomInteger = Math.ceil(Math.random() * 10);
  socketStore.id = randomInteger;

})

socketStore.socket.on("connect", () => {
  socketStore.connected = ref(true);
});

socketStore.socket.on("disconnect", () => {
  socketStore.connected = ref(false);
});

socketStore.socket.on('chat', (data) => {
  console.log(data.message)
  socketStore.chatMessages.push(data)
})

function sendMessage() {
  const chat = {
    owner: socketStore.id,
    message: message.value
  }
  socketStore.chatMessages.push(chat)
  socketStore.socket.timeout(5000).emit('chat', chat)

  message.value = "";
  // 스크롤을 새 메시지 아래로 이동시킵니다.
  nextTick(() => {
    scrollChatToBottom();
  });
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
  console.log(socketStore.chatMessages.value)
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