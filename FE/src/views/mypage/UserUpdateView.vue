<template>
  <div class="update-container">
    <div class="user-update-form">
      <h2>사용자 정보를 입력해주세요</h2>
      <button @click="closeForm">X</button>

      <form @submit.prevent="updateUserInfo">
        <div class="form-group">
          <label for="gender">성별</label>
          <button type="button" :class="{ active: userInfo.value.gender === 1 }" @click="userInfo.value.gender = 1">남성</button>
          <button type="button" :class="{ active: userInfo.value.gender === 2 }" @click="userInfo.value.gender = 2">여성</button>

        </div>
        <div class="form-group">
          <label for="birthYear">출생년도</label>
          <select v-model="userInfo.value.birthYear">
            <option v-for="year in birthYears" :key="year" :value="year">{{ year }}</option>
          </select>
        </div>


        <div class="form-group">
          <label for="height">키</label>
          <input type="number" v-model="userInfo.value.height" placeholder="cm" />
        </div>

        <div class="form-group">
          <label for="weight">몸무게</label>
          <input type="number" v-model="userInfo.value.weight" placeholder="kg" />
        </div>

        <div class="form-group">
          <label>활동량</label>
          <button type="button" :class="{ active: userInfo.value.activityLevel === 1 }" @click="userInfo.value.activityLevel = 1">비활동적</button>
          <button type="button" :class="{ active: userInfo.value.activityLevel === 2 }" @click="userInfo.value.activityLevel = 2">저활동적</button>
          <button type="button" :class="{ active: userInfo.value.activityLevel === 3 }" @click="userInfo.value.activityLevel = 3">활동적</button>
          <button type="button" :class="{ active: userInfo.value.activityLevel === 4 }" @click="userInfo.value.activityLevel = 4">매우활동적</button>
        </div>


        <button type="submit">정보 업데이트</button>
      </form>
    </div>
  </div>
</template>

<script setup>

import { ref, onMounted,computed } from 'vue';
import { useUserStore } from '../../stores/user'; 
import router from '@/router';


const store = useUserStore();

const userInfo = ref({});

const birthYears = computed(() => Array.from({ length: 2014 - 1945 + 1 }, (_, i) => 1945 + i));


const bringUserInfo = async () => {
  try {
    await store.researchUser();    
    userInfo.value = {
      gender: store.user.value.userGender || null,
      birthYear: store.user.value.userBirth || new Date().getFullYear(),
      height: store.user.value.userHeight || null,
      weight: store.user.value.userWeight || null,
      activityLevel: store.user.value.userHealth || null
    };
  } catch (error) {
    console.error("데이터를 가져오지 못했습니다.", error);
  }
};

const updateUserInfo = async () => {
  try {   
    await store.updateUser(userInfo.value);
    alert('수정완료');
    router.push('/mypage');
  } catch (error) {
    console.error("업데이트 실패", error);
  }
};

const closeForm = () => {
  router.push({ name: 'mypage' });
};

  onMounted(() => {
  bringUserInfo();
});

</script>

<style scoped>
.update-container {
  
}
.user-update-form {
  
}
.form-group {
  margin-bottom: 1em;
}
button.active {
  background-color: #000; 
}

</style>