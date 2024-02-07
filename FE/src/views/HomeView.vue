<template>
  <div class="container">
    <img src="@/components/icons/logo.png" alt="logo" class="logo" style="color" />
    <a id="custom-login-btn" @click="userStore.loginKakao">
      <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="222" alt="카카오 로그인 버튼" />
    </a>
    <div class="content">
      <div>
        <RouterView />
      </div>
    </div>
  </div>
</template>
  
<script setup>
import '../components/font/font.css';
import { onMounted, ref } from "vue";
import { useUserStore } from '@/stores/user';
import { useAccumulateStore } from '@/stores/accumulate';

const userStore = useUserStore();
const accumulateStore = useAccumulateStore();


onMounted(async () => {

  // const date = new Date();
  // const year = date.getFullYear();
  // let month = date.getMonth() + 1; // Adding 1 to adjust for zero-based months
  // month = month < 10 ? "0" + month.toString() : month.toString();
  // const now = ref(year.toString() + month.toString());


  // await accumulateStore.month(now);
})



</script>
  
<style scoped>
.logo{
  /* width: 50%;
  height: 60%; */
}
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh; /* 화면 전체 높이를 차지하기 위해 */
}

#custom-login-btn {
  margin-top: 5%; /* 버튼과 이미지 사이의 간격 조정 */
}


.content {
  margin-top: 20px; /* 내용과의 간격 조정 */
}
</style>
