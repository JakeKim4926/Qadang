<template>
  <div class="mypage-container">    
    <div class="message">
      {{ message }}
    </div>
    
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useuserStore } from '@/stores/user'; 


const store = useuserStore();

const message = computed(() => {
  return store.isAdditionalInfoFilled.value
    ? "오늘 하루는 어떠셨나요? 카페인 없는 하루 화이팅"
    : "추가정보를 입력하지 않았어요 추가정보를 입력하시고\n개인별 맞춤 정보를 제공받을 수 있어요";
});

</script>

<style scoped>
.mypage-container {

}
.message {

}
</style>