<template>
  <div class="board-create-container">
    <div class="input-box">
      <div class="close" @click="closeUpdateModal">
        <font-awesome-icon :icon="['fas', 'circle-xmark']" style="color: #000000;" size="xl"/>
      </div>
  
  <!-- <div class="update-container"> -->
    <div class="user-update-form">
      <div class="form-header">
        <h2 class="update-title">회원 정보 수정</h2>        
      </div>

      <form @submit.prevent="updateUserInfo" class="update-form">        
        <div class="form-row">
          <div class="form-group half-width">
            <label for="userGender" class="label-margin tag-bold">성별</label>
            <button type="button" :class="{ active: userInfo.userGender === 1 }" @click="userInfo.userGender = 1" class="shading2">남성</button>
            <button type="button" :class="{ active: userInfo.userGender === 2 }" @click="userInfo.userGender = 2" class="shading2">여성</button>            
          </div>
          <div class="form-group half-width">
            <label for="userBirth" class="label-margin tag-bold">출생년도</label>
            <input type="number" v-model="userInfo.userBirth" class="input-field2" min="0"/>
          </div>
        </div>

        <div class="form-row">
          <div class="form-group half-width">
            <label for="userHeight" class="label-margin tag-bold">키</label>
            <select v-model="userInfo.userHeight" class="input-field label-margin">
              <option v-for="userHeight in heightOptions" :key="userHeight" :value="userHeight">{{ userHeight }} </option>
            </select>
            <span class="unit">cm</span> 
          </div>
          <div class="form-group half-width">
            <label for="userWeight" class="label-margin tag-bold">몸무게</label>
            <select v-model="userInfo.userWeight" class="input-field label-margin">
              <option v-for="userWeight in weightOptions" :key="userWeight" :value="userWeight">{{ userWeight }} </option>
            </select>
            <span class="unit">kg</span>
          </div>
        </div>

        <div class="form-group">
          <label class="label-margin tag-bold">활동량</label>
          <div class="button-group">
            <button type="button" :class="{ active: userInfo.userHealth === 1 }" @click="userInfo.userHealth = 1" class="shading">비활동적</button>
            <button type="button" :class="{ active: userInfo.userHealth === 2 }" @click="userInfo.userHealth = 2" class="shading">저활동적</button>
            <button type="button" :class="{ active: userInfo.userHealth === 3 }" @click="userInfo.userHealth = 3" class="shading" > 활동적 </button>
            <button type="button" :class="{ active: userInfo.userHealth === 4 }" @click="userInfo.userHealth = 4" class="shading">매우활동적</button>
            <a @click="togglePopup" class="popup-mark"><font-awesome-icon :icon="['fas', 'circle-question']" /></a>
            <div v-if="isPopupVisible" class="popup">
              <p>권장량을 정확하게 계산하기 위해 필요합니다!</p>
              <a @click="togglePopup" class="close">
                <font-awesome-icon :icon="['fas', 'circle-xmark']" style="color: #000000;" size="xl"/>
              </a>
            </div>
          </div>
        </div>
            
        <button type="submit" class="submit-bttn">저장하기</button>
        
      </form>
    </div>
  </div>
</div>

</template>

<script setup>

import { reactive, ref, onMounted } from 'vue';
import { useUserStore } from '../../stores/user'; 
import router from '@/router';
import { isUpdateModal } from '../../stores/util'


const store = useUserStore();
const isLoading = ref(false);
const userInfo = reactive({  
  userGender: null,
  userBirth: null,
  userHeight: null,
  userWeight: null,
  userHealth: null
});
const isPopupVisible = ref(false);
const heightOptions = Array.from({ length: 25 }, (_, i) => 100 + i * 5); 
const weightOptions = Array.from({ length: 25 }, (_, i) => 30 + i * 5); 



const closeUpdateModal = () => {
  isUpdateModal.value = false;
  
  console.log('!', isUpdateModal.value);
}; 

const bringUserInfo = async () => {
  isLoading.value = true;
  try {
    await store.researchUser();    
    userInfo.userGender = store.getUser.userGender || null;
    userInfo.userBirth = store.getUser.userBirth || null; 
    userInfo.userHeight = store.getUser.userHeight || null;
    userInfo.userWeight = store.getUser.userWeight || null;
    userInfo.userHealth = store.getUser.userHealth || null;
  } catch (error) {
    console.error("데이터를 가져오지 못했습니다.", error);
  } finally {
    isLoading.value = false;
  }
};




const updateUserInfo = async () => {
  
  const updateData = {};  
  if (userInfo.userBirth) updateData.userBirth = userInfo.userBirth;
  if (userInfo.userGender !== null) updateData.userGender = userInfo.userGender;
  if (userInfo.userHeight !== null) updateData.userHeight = userInfo.userHeight;
  if (userInfo.userWeight !== null) updateData.userWeight = userInfo.userWeight;
  if (userInfo.userHealth !== null) updateData.userHealth = userInfo.userHealth;

  try {
    await store.updateUser(updateData);    
    isUpdateModal.value = false
    console.log(updateData) 
    window.location.reload();
    router.push('/mypage');
  } catch (error) {
    console.error("업데이트 실패", error);
  }
}



onMounted(() => {
  bringUserInfo();  
  store.researchName();
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
  margin-left: 35px;
  margin-top: 25px;
}

.form-header {
  display: flex;
  justify-content: center; 
  align-items: center; 
  text-align: center; 
  width: 100%;
  margin-bottom: 20px;
}

.update-title {
  color: #562B1A;
  font-weight: bold;
  text-align: center;
  margin-right: 50px;
  margin-bottom: 30px;
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
  flex-direction: row;
  justify-content: flex-start; 
  align-items: center; 
  text-align: center;
}

.shading {
  margin-right: 10px; 
  border-radius: 20px;
  border: 1px solid #EFEFEF;
  background: var(--Color, #FFF);
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  padding: 10px;
  margin: 5px 5px;
  width: 90px;
  cursor: pointer;
  text-align: right;
}

.shading2 {
  margin-right: 10px; 
  border-radius: 20px;
  border: 1px solid #EFEFEF;
  background: var(--Color, #FFF);
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  padding: 10px;
  margin: 5px 5px;
  width: 50px;
  cursor: pointer;
  text-align: center;
}
.shading:last-child {
  margin-right: 0;
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
  margin-top: 70px;
  /* margin: 70px auto;  */
  display: block;
  margin-left: 30%;
}
.close {
  display: flex;
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
}
.board-create-container {
  position: fixed;
  width: 100vw;
  height: 100vh;
  /* max-width:614px;
  max-height: 584px; */
  background-color: rgba(128, 128, 128, 0.863) !important;
  top: 0;
  left: 0;
  margin: 0;
  z-index: 99 !important;
  display: flex;
  justify-content: center;
  align-items: center;
}

.input-box {
  background: #ffffff;
  border-radius: 30px;
  border-style: solid;
  border-color: #d9d9d9;
  border-width: 1px;
  position: absolute;
  width: 500px;
  height: 500px;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
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
  margin-right: 10px;
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
button.active {
  background-color: #B29F91;
  color: #EFEFEF;
  }
.popup-mark{
  cursor: pointer;
}
</style>
