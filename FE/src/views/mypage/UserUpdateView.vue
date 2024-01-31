<template>
  <div class="update-container">
    <div class="user-update-form">
      <div class="form-header">
        <h1 class="update-title">사용자 정보를 입력해주세요</h1>
        <button @click="closeForm" class="close-button"><font-awesome-icon :icon="['fas', 'times']"/></button>
      </div>

      <form @submit.prevent="updateUserInfo" class="update-form">
        <div class="form-row">
          <div class="form-group half-width">
            <label for="gender" class="label-margin align tag-bold">성별</label>
            <div class="button-group align">
              <button type="button" :class="{ active: userInfo.gender === 1 }" @click="userInfo.gender = 1" class="shading">남성</button>
              <button type="button" :class="{ active: userInfo.gender === 2 }" @click="userInfo.gender = 2" class="shading">여성</button>
            </div>
          </div>
          <div class="form-group half-width">
            <label for="birthYear" class="label-margin tag-bold">나이</label>
            <input type="number" v-model="userInfo.birthYear" class="input-field2"/>
          </div>
        </div>

        <div class="form-row">
          <div class="form-group half-width">
            <label for="height" class="label-margin tag-bold">키</label>
            <select v-model="userInfo.height" class="input-field label-margin">
              <option v-for="height in heightOptions" :key="height" :value="height">{{ height }} </option>
            </select>
            <span class="unit">cm</span> 
          </div>
          <div class="form-group half-width">
            <label for="weight" class="label-margin tag-bold">몸무게</label>
            <select v-model="userInfo.weight" class="input-field label-margin">
              <option v-for="weight in weightOptions" :key="weight" :value="weight">{{ weight }} </option>
            </select>
            <span class="unit">cm</span>
          </div>
        </div>

        <div class="form-group">
          <label class="label-margin align tag-bold">활동량</label>
          <div class="button-group align">
            <button type="button" :class="{ active: userInfo.activityLevel === 1 }" @click="userInfo.activityLevel = 1" class="shading">비활동적</button>
            <button type="button" :class="{ active: userInfo.activityLevel === 2 }" @click="userInfo.activityLevel = 2" class="shading">저활동적</button>
            <button type="button" :class="{ active: userInfo.activityLevel === 3 }" @click="userInfo.activityLevel = 3" class="shading" > 활동적 </button>
            <button type="button" :class="{ active: userInfo.activityLevel === 4 }" @click="userInfo.activityLevel = 4" class="shading">매우활동적</button>
            <a @click="togglePopup"><font-awesome-icon :icon="['fas', 'circle-question']" /></a>
            <div v-if="isPopupVisible" class="popup">
              <p>여기에 특정 메시지를 입력하세요.</p>
              <button @click="togglePopup">닫기</button>
            </div>
          </div>
        </div>
            
        <button type="submit" class="submit-bttn">입력완료</button>
        
      </form>
    </div>
  </div>
</template>

<script setup>

import { reactive, ref, onMounted,computed,toRaw } from 'vue';
import { useUserStore } from '../../stores/user'; 
import router from '@/router';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faCircleQuestion } from '@fortawesome/free-solid-svg-icons'

library.add(faCircleQuestion);

const store = useUserStore();
const isLoading = ref(false);
const userInfo = reactive({
  gender: null,
  birthYear: null,
  height: null,
  weight: null,
  activityLevel: null
});
const isPopupVisible = ref(false);
const heightOptions = Array.from({ length: 25 }, (_, i) => 100 + i * 5); 
const weightOptions = Array.from({ length: 25 }, (_, i) => 30 + i * 5); 

const bringUserInfo = async () => {
  isLoading.value = true;
  try {
    await store.researchUser();
    userInfo.gender = store.user.value?.userGender || null;
    userInfo.birthYear = store.user.value?.userBirthYear || null;
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
  
  const updateData = {};
  if (userInfo.gender !== null) updateData.gender = userInfo.gender;
  if (userInfo.birthYear) updateData.birthYear = userInfo.birthYear;
  if (userInfo.height !== null) updateData.height = userInfo.height;
  if (userInfo.weight !== null) updateData.weight = userInfo.weight;
  if (userInfo.activityLevel !== null) updateData.activityLevel = userInfo.activityLevel;

  try {
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
const togglePopup = () => {
  isPopupVisible.value = !isPopupVisible.value;
};
</script>

<style scoped>
.update-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 600px;
  margin: auto;
  margin-top: 40px;
  background: #FFF;
  padding: 20px;
  
  
}

.user-update-form {
  width: 100%;
}

.form-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.update-title {
  color: #562B1A;
  font-weight: bold;
  text-align: center;
}

.close-button {
  background: none;
  border: none;
  cursor: pointer;
}

.form-group {
  margin-bottom: 1em;
  width: 100%;
}

.form-group.half-width {
  width: 48%; /* 양쪽에 여유 공간을 두기 위해 100%보다 작은 값을 설정 */
}

.form-row {
  display: flex;
  justify-content: space-between; /* 요소들을 좌우로 정렬 */
}

.input-field {
  border-radius: 22px;
  border: 2px solid #846046;
  padding: 10px 20px;
  width: 42%;
}
.input-field2 {
  border-radius: 22px;
  border: 2px solid #846046;
  padding: 10px 20px;
  width: 30%;
}
.margin-top{
  margin-top: 30px;
}
.button-group {
  display: flex;
  /* justify-content: space-between; */
}

.shading {
  border-radius: 20px;
  border: 1px solid #EFEFEF;
  background: var(--Color, #FFF);
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  padding: 10px;
  margin: 10px 0;
  cursor: pointer;
}

button.active {
  background-color: #B29F91;
  color: #EFEFEF;
}

.submit-bttn {
  border-radius: 90px;
  background: #846046;
  color: #EFEFEF;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  width: 30%;
  /* margin-top: 50px; */
  margin: 70px auto; 
  display: block;
}
.popup {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  z-index: 100;
}
.tag-bold {
  font-weight: bold;
}
.label-margin{
  margin-right: 30px;
}
.align{
  display: inline-block;
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
</style>
