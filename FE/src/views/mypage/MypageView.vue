<template>
  <UserUpdateView v-if="isUpdateModal" />
  <div class="mypage-container">    
    <div class="profile-section">
      <div class="profile-image">        
        <h3 class="nickname">{{ store.getUser.userName }}</h3>
      </div>
      <div class="message">{{ message }}</div>
      <div class="user-actions">
        <button @click="openUpdateModal" class="button-edit-info">회원정보수정</button>        
      </div>
      <div v-if="isInfoFilled" class="shading2">
        <!-- <div class="shading2">        -->
        <div class="nutrition-info">
          <div class="nutrition-item">
            <h1> 
              {{ store.getUserRDICaffeine }} mg
            </h1>      
            <h3>하루 권장 카페인 섭취량</h3>
          </div>
          <div class="nutrition-item">
            <h1> 
              {{ store.getUserRDISugar }} g
            </h1>      
            <h3>하루 권장 설탕 섭취량</h3>
          </div>        
        </div>        
      </div>
    </div>
    
    <div class="intake-section">
      <div class="intake-card shading">
        <h1>{{ store.getUserMaxCaffeine }} mg</h1>
        <h3>하루 최고 카페인 섭취량</h3>
        <h4>{{ store.getUserMaxCaffeineDate }}</h4>
      </div>
      <div class="intake-card shading">
        <h1>{{ store.getUserMaxSugar }} g</h1>
        <h3>하루 최고 당 섭취량</h3>
        <h4>{{ store.getUserMaxSugarDate }}</h4>
      </div>
    </div>
    
    <div class="user-actions">
      <button @click="userWithdraw" class="button-withdraw">회원 탈퇴</button>
      <button @click="handleLogout" class="button-logout">로그아웃</button>
    </div>
  </div>

  <!-- <RouterView/> -->
</template>

<script setup>
import { ref, onMounted,computed } from 'vue';
import { useUserStore } from '../../stores/user'; 
import router from '@/router';
import { isUpdateModal } from '../../stores/util'
import UserUpdateView from '@/components/user/UserUpdateView.vue'

const store = useUserStore();
const isInfoFilled = store.isInfoFilled;
const openUpdateModal = () => {
  isUpdateModal.value = true
  console.log('!',isUpdateModal.value)}


const message = computed(() => {
  return store.infoFilled.value
    ? "오늘 하루는 어떠셨나요? \n 카페인 없는 하루 화이팅"
    : "추가정보를 입력하지 않았어요\n 추가정보를 입력하시면\n개인별 맞춤 정보를 제공받을 수 있어요";
});

const userWithdraw = async () => {
  if (confirm('정말로 회원 탈퇴를 하시겠습니까?')) {
    try {
      await store.deleteUser();      
      alert('회원 탈퇴가 완료되었습니다.');
      router.push('/home');
    } catch (error) {
      console.error('회원 탈퇴 중 오류가 발생했습니다.', error);      
    }
  }
};

const handleLogout = () => {
  store.logout();
};

onMounted(() => {  
  store.researchMax();
  store.researchUser();
  store.researchName();
  store.researchAmount();
});



</script>

<style scoped>
.mypage-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 600px; /* 최대 너비 유지 */
  margin: auto;
  margin-top: 20px;
  padding: 20px;
 
 
}

.low-height{
  margin-top: auto;
}
.mid-fontsize{
  font-size: medium;
}
.profile-section {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: flex-start; 
  text-align: left;
  width: 100%;
}

.profile-image {
  /* background-color: #CB8A58; */
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;  
  width: 150px;
  height: 100px;
  color: #CB8A58;
  font-size: x-large;
}



.message {
  position: absolute;
  top: 20px;
  right: 20px;
  background: #FFF;
  padding: 10px 80px; 
  border-radius: 22px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  white-space: pre-wrap;
  font-weight: bold;
  max-width: calc(100% - 40px);
  z-index: 1;
}

.user-actions {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  padding-right: 20px;
}

.button-edit-info {
  text-decoration: none;
  padding: 10px 20px;
  border-radius: 22px;
  background: #846046;
  color: #EFEFEF;
  border: none;
  cursor: pointer;
  font-weight: bold;
  margin-right: 25px; 
  margin-top: 10px;
  
}


.intake-section {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-top: 10px;
}

.intake-card {
  flex-basis: 48%;
  margin: 10px;
  padding: 5px;
  text-align: center;
  border-radius: 22px;
  background: #FFF;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.intake-card h1 {
  color: #562B1A; 
}
.shading {
  border-radius: 22px;
  background: #FFF;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.shading2 {
  border-radius: 22px;
  background: #FFF;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  width: 580px;
  margin: auto;
  height: 150px;
}
.shading h3 {
  color: #562B1A; 
}

.button-withdraw {
  padding: 10px 20px;
  border: none;
  border-radius: 22px;
  cursor: pointer;
  font-weight: bold;
  margin-left: 10px;
  margin-top: 20px;
  background: #B29F91;
  color: #EFEFEF;
}
.button-logout {
  padding: 10px 20px;
  border: none;
  border-radius: 22px;
  cursor: pointer;
  font-weight: bold;
  margin-left: 10px;
  margin-top: 20px;
  background: #846046;
  color: #EFEFEF;
}

h1 {
  font-size: 2.5em;
}

h2 {
  font-size: 1.7em;
}

h3 {
  font-size: 1.15em;
}

h4 {
  font-size: 1em;
}

@media (max-width: 768px) {
  .profile-section {
    align-items: center; 
    text-align: center;
  }

  .message {
    position: static;
    margin: 10px 0;
    text-align: center;
  }

  .user-actions {
    justify-content: center;
    padding-right: 0;
    margin-top: 10px;
  }
}


.nutrition-info {
  margin-bottom: 30px;
  display: flex; 
  justify-content: space-between; 
  align-items: center; 
  padding: 0px 60px; 
  
}

.nutrition-item {
  text-align: center; /* 텍스트 중앙 정렬 */
  /* 필요한 경우 여기에 추가 스타일을 적용하세요. */
}
.nutrition-item h1{
  color: #562B1A; 
}
.nickname{
  
}

</style>
