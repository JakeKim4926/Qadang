<template>
  <div>
    <h2>Hello Main Sugar</h2>
  </div>

  <p>닉네임 님</p>
  <div>
    <RouterLink :to="{name:'mainCaffeine'}">카페인 섭취량으로 가는 버튼</RouterLink>
  </div>

  <p>당 섭취량</p>
  <div>
    <p>이미지</p>
    <div>
      <p>방금 마신 음료</p>
      <p>당 g</p>
      <p>하루 총합 섭취량 / 권장량</p>
      <p>000.g/400g</p>
    </div>
    <div>
      <p>피가 끈적거려요</p>
    </div>
  </div>

  <div>
    최근에 마신 당을 한눈에 보아요
    chart.js 활용
  </div>

  <div>
    오늘은 이 음료 어때요?
    추천 음료 상세보기
  </div>

  <div @click="goChat">
    <font-awesome-icon :icon="['fas', 'comments']" style="color: #000000;" />
  </div>
</template>

<script setup>
import router from '@/router';

// 채팅으로 이동
const goChat = () => {
  router.push({name : 'chat'})
}
</script>

<style scoped>
</style>