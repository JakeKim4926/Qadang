<template>
  <div class="component-2">
    <div class="container">
      <div class="content-wrapper">
        <div class="image-container">
          <img src="@/components/icons/su_bad.png" alt="나쁨 이미지" class="image-21">
        </div>
        <div class="text-block">
          <span class="div-2-span">당신은 슈가 러버</span>
          <br>
          <span class="div-2-span2">혈액도 달달해질 것 같아요</span>
        </div>
      </div>

      <div class="div-2-span">처방전</div>
      <div class="_1">달달이 사랑을 줄여주세요</div>
      <div class="button-wrapper">
        <img class="image-24" src="@/components/icons/logo_4.png" alt="doctor" />
        <router-link to="/mainCaffeine" class="rectangle-4270">시작하기</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>

import { ref, onMounted, watch } from 'vue'

</script>

<style scoped>
/* 추가된 스타일 */
.text-block {
  margin-top: 10px;
  text-align: center;
}

.component-2 {
  width: 80%;
  height: 80%; /* 컴포넌트의 높이를 화면의 80%로 지정 */
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.content-wrapper {
  display: flex;
  justify-content: center; /* 수평 가운데 정렬 */
  align-items: center;
  left:100%;
}

.image-container {
  margin-right: 20px; /* Adjust margin as needed */
}

.rectangle-4270 {
  display: flex;
  justify-content: center;
  align-items: center;
  background: #846046;
  border-radius: 90px;
  width: 20%;
  height: 50px;
  margin-top: 20px;
  color: white;
  font-weight: bold;
  font-size: 20px;
  border: none;
  cursor: pointer;
  text-decoration: none; /* 텍스트에 대한 밑줄 제거 */
}

.div2 {
  text-align: center;
  margin-top: 30px;
}

.div-2-span {
  color: #562b1a;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 40px;
}

.div-2-span2 {
  color: #000000;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 25px;
}

.div3, ._1 {
  color: #000000;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 25px;
  margin-top: 20px;
}

.image-21 {
  width: auto; /* Adjust width as needed */
  height: auto; /* Adjust height as needed */
  /* Additional styling as needed */
}
.image-24 {
  width: auto; /* Adjust width as needed */
  height: auto; /* Adjust height as needed */
  /* Additional styling as needed */
}

.button-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px; /* 조정 가능한 마진 값 */
}
</style>
