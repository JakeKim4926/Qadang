<template>
  <div>
    <h1>Hello InputNothing</h1>
  </div>

  <div>
    x버튼
  </div>

  <div>
    <h2>오늘 마신 카페 음료를 직접 입력해주세요</h2>

    <div>
      <label for="cafeName">카페명: </label>
      <input type="text" name="cafeName" id="cafeName"
      v-model="cafeName" placeholder="카페명을 입력해주세요"/>
    </div>

    <div>
      <label for="drinkName">음료명: </label>
      <input type="text" name="drinkName" id="drinkName"
      v-model="drinkName" placeholder="음료명을 입력해주세요"/>
    </div>

    <div>
      <label for="drinkCaffeine">카페인: </label>
      <input type="number" name="drinkCaffeine" id="drinkCaffeine"
      v-model="drinkCaffeine" placeholder="없다면 0을 입력해주세요"/>mg
    </div>

    <div>
      <label for="drinkSugar">당: </label>
      <input type="number" name="drinkSugar" id="drinkSugar"
      v-model="drinkSugar" placeholder="없다면 0을 입력해주세요"/>g
    </div>

  </div>

  <div>
    <RouterLink :to="{ name: 'input' }">음료선택</RouterLink>
    물음표버튼
    입력완료
  </div>
</template>

<script setup>
import { ref } from 'vue';

const cafeName = ref(null)
const drinkName = ref(null)
const drinkCaffeine = ref(null)
const drinkSugar = ref(null)

</script>

<style lang="scss" scoped></style>