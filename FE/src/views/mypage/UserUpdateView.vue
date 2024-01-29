<template>
  <div class="update-container">
    <div class="user-update-form">
      <h2>사용자 정보를 입력해주세요</h2>
      <button @click="closeForm">X</button>

      <form @submit.prevent="updateUserInfo">
        <div class="form-group">
          <label for="gender">성별</label>
          <button type="button" :class="{ active: userInfo.gender === 1 }" @click="userInfo.gender = 1">남성</button>
          <button type="button" :class="{ active: userInfo.gender === 2 }" @click="userInfo.gender = 2">여성</button>
        </div>
        <div class="form-group">
          <label for="birthYear">출생년도</label>
          <select v-model="userInfo.birthYear">
            <option v-for="year in birthYears" :key="year" :value="year">{{ year }}</option>
          </select>
        </div>
        <div class="form-group">
          <label for="height">키</label>
          <input type="number" v-model="userInfo.height" placeholder="cm" />
        </div>
        <div class="form-group">
          <label for="weight">몸무게</label>
          <input type="number" v-model="userInfo.weight" placeholder="kg" />
        </div>
        <div class="form-group">
          <label>활동량</label>
          <button type="button" :class="{ active: userInfo.activityLevel === 1 }" @click="userInfo.activityLevel = 1">비활동적</button>
          <button type="button" :class="{ active: userInfo.activityLevel === 2 }" @click="userInfo.activityLevel = 2">저활동적</button>
          <button type="button" :class="{ active: userInfo.activityLevel === 3 }" @click="userInfo.activityLevel = 3">활동적</button>
          <button type="button" :class="{ active: userInfo.activityLevel === 4 }" @click="userInfo.activityLevel = 4">매우활동적</button>
        </div>
        <button type="submit">정보 업데이트</button>
      </form>
    </div>
  </div>
</template>

<script setup>

import { reactive, ref, onMounted,computed,toRaw } from 'vue';
import { useUserStore } from '../../stores/user'; 
import router from '@/router';


const store = useUserStore();
const isLoading = ref(false);
const userInfo = reactive({
  gender: null,
  birthYear: new Date().getFullYear(),
  height: null,
  weight: null,
  activityLevel: null
});

const birthYears = computed(() => Array.from({ length: 2014 - 1945 + 1 }, (_, i) => 1945 + i));


const bringUserInfo = async () => {
  isLoading.value = true;
  try {
    await store.researchUser();
    userInfo.gender = store.user.value?.userGender || null;
    userInfo.birthYear = store.user.value?.userBirthYear || new Date().getFullYear();
    userInfo.height = store.user.value?.userHeight || null;
    userInfo.weight = store.user.value?.userWeight || null;
    userInfo.activityLevel = store.user.value?.userActivityLevel || null;
  } catch (error) {
    console.error("데이터를 가져오지 못했습니다.", error);
  } finally {
    isLoading.value = false;
  }
};


const updateUserInfo = async () => {
  // 업데이트할 정보만 담은 객체를 생성
  const updateData = {};
  if (userInfo.gender !== null) updateData.gender = userInfo.gender;
  if (userInfo.birthYear) updateData.birthYear = userInfo.birthYear;
  if (userInfo.height !== null) updateData.height = userInfo.height;
  if (userInfo.weight !== null) updateData.weight = userInfo.weight;
  if (userInfo.activityLevel !== null) updateData.activityLevel = userInfo.activityLevel;

  try {
    // 생성한 업데이트 데이터 객체를 사용하여 업데이트 요청을 보냅니다.
    await store.updateUser(updateData);
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