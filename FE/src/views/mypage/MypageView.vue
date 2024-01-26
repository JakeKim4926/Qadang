<template>
  <div class="mypage-container">    
    <div class="profile-section">
      <div class="profile-image">{{ userInfo.userName }}</div>
      <div class="profile-message">{{ message }}</div>
      <RouterLink :to="{name: 'editinfo'}" class="edit-info-link">추가정보입력</RouterLink>
      <div v-if="isInfoFilled">
        <div>하루 권장 카페인 섭취량: {{ rdiCaffeine }}</div>
        <div>하루 권장 설탕 섭취량: {{ rdiSugar }}</div>
      </div>
    </div>
    
    <div class="intake-section">
      <div class="intake-card">
        <div class="intake-amount">{{maxCaffeine}}</div>
        <div class="intake-type">하루 최고 카페인 섭취량</div>
        <div class="intake-date">{{maxCaffeineDate}}</div>
      </div>
      <div class="intake-card">
        <div class="intake-amount">{{maxSugar}}</div>
        <div class="intake-type">하루 최고 당 섭취량</div>
        <div class="intake-date">{{maxSugarDate}}</div>
      </div>
    </div>
    
    <div class="user-actions">
      <button @click="userWithdraw" class="delete-account-button">회원 탈퇴</button>
    </div>
  </div>
  <RouterView/>
</template>

<script setup>
import { ref, onMounted,computed } from 'vue';
import { useUserStore } from '../../stores/user'; 
import router from '@/router';
import { RouterLink, RouterView } from 'vue-router'

const store = useUserStore();
const isInfoFilled = store.isInfoFilled;

const maxCaffeine = ref(0);
const maxCaffeineDate = ref('');
const maxSugar = ref(0);
const maxSugarDate = ref('');
const userInfo = ref({});
const rdiCaffeine = ref(0);
const rdiSugar = ref(0);

const bringMaxintake = async () => {
  try {
    await store.researchMax();
    maxCaffeine.value = store.userMaxCaffeine.value;
    maxCaffeineDate.value = store.userMaxCaffeineDate.value;
    maxSugar.value = store.userMaxSugar.value;
    maxSugarDate.value = store.userMaxSugarDate.value;
  } catch (error) {
    console.error("데이터를 가져오지 못했습니다.", error);
  }
};



const bringRDI = async () => {
  try {
    await store.researchAmount();    
    rdiCaffeine.value = store.userRDICaffeine.value
    rdiSugar.value = store.userRDISugar.value 
  } catch (error) {
    console.error("데이터를 가져오지 못했습니다.", error);
  }
};

const bringUserInfo = async () => {
  try { 
    await store.researchUser();    
    userInfo.value = store.user.value;
  } catch (error) {    
    console.error("데이터를 가져오지 못했습니다.", error);
  }
};

const message = computed(() => {
  return store.infoFilled.value
    ? "오늘 하루는 어떠셨나요? 카페인 없는 하루 화이팅"
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

onMounted(() => {
  bringMaxintake();
  bringUserInfo();
  bringRDI();
});

</script>

<style scoped>
.mypage-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.profile-section {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.profile-image {
  width: 80px;
  height: 80px;
  background-color: #ccc;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
}

.profile-message {
  text-align: center;
  margin-bottom: 10px;
}

.edit-info-link {
  margin-bottom: 20px;
}

.intake-section {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.intake-card {
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 15px;
  margin-bottom: 10px;
  width: 80%;
}

.user-actions {
  margin-top: 20px;
}

.delete-account-button {

}
</style>