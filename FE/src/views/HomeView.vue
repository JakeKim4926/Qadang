<template>
  <div class="container">
    <h2>당신의 카페인과 당을 줄여줄 <br> 인생 콰당 방지 서비스</h2>
    <img src="@/components/icons/logo.png" alt="logo" style="color" />
    <a id="custom-login-btn" @click="userStore.loginKakao()">
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
import { onMounted } from "vue";
import { useUserStore } from '@/stores/user';

onMounted(() => {

})

const userStore = useUserStore();

const loginKakao = function () {
    axios({
      url: 'https://kauth.kakao.com/oauth/authorize',
      method: "GET",
      params : {redirect_uri : 'http://localhost:5173/login'}
    })
      .then((res) => {
      })
      .catch((err) => {
        console.log(err);
      });
  };

</script>
  
<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh; /* 화면 전체 높이를 차지하기 위해 */
}

.content {
  margin-top: 20px; /* 내용과의 간격 조정 */
}
</style>
