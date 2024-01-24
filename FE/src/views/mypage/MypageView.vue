<template>
  <div class="mypage-container">    
    <div class="message">
      {{ message }}
    </div>
    
  </div>
</template>

<script setup>
import { ref, onMounted,computed } from 'vue';
import { useuserStore } from '../../stores/user'; 


const store = useuserStore();

const maxCaffeine = ref(0);
const maxCaffeineDate = ref('');
const maxSugar = ref(0);
const maxSugarDate = ref('');
const userInfo = ref({});
const todayCaffeine = ref(0);
const todaySugar = ref(0);

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



const bringUsertoday = async () => {
  try {
    await store.researchAmount();    
    todayCaffeine.value = store.userRDICaffeine.value
    todaySugar.value = store.userRDISugar.value 
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
  return store.isAdditionalInfoFilled.value
    ? "오늘 하루는 어떠셨나요? 카페인 없는 하루 화이팅"
    : "추가정보를 입력하지 않았어요\n 추가정보를 입력하시면\n개인별 맞춤 정보를 제공받을 수 있어요";
});

onMounted(() => {
  bringMaxintake();
  bringUserInfo();
  bringUsertoday();
});

</script>

<style scoped>
.mypage-container {

}
.message {

}
</style>