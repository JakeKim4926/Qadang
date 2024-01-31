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
      <input type="number" name="drinkCaffeine" id="drinkCaffeine" v-model="drinkCaffeine">mg
    </div>

    <div>
      <label for="drinkSugar">당: </label>
      <input type="number" name="drinkSugar" id="drinkSugar" v-model="drinkSugar">g
    </div>

  </div>

  <div>
    <RouterLink :to="{ name: 'input' }">음료선택</RouterLink>
    <span class="tip" @mouseover="showToolTip = true" @mouseleave="showToolTip = false">물음표버튼</span>
    <div v-if="showToolTip">여기에 음료선택에 대한 자세한 설명을 작성합니다</div>
    <button @click="makeSubmit">입력완료</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRecordsStore } from "@/stores/records";

const store = useRecordsStore()

const cafeName = ref(null)
const drinkName = ref(null)
const drinkCaffeine = ref(0)
const drinkSugar = ref(0)

const showToolTip = ref(false)

const makeSubmit = () => {
  if (isValid()) {
    console.log('입력값이 올바릅니다. 데이터를 전송합니다.')

    const drink = {
      cafeName: cafeName.value,
      drinkName: drinkName.value,
      drinkCaffeine: drinkCaffeine.value,
      drinkSugar: drinkSugar.value
    }

    console.log(drink)

    // 유효한 데이터를 백엔드로 전송
    store.createMyDrink(drink)

    } else {
      console.log('입력값이 올바르지 않습니다')
    }
  }

const isValid = () => {
  
  // 모든 정보를 입력한 상태에서 카페인과 당이 0을 포함한 양수일 경우
  // 유의미한 정보를 입력했다고 간주하고 유효성 검사 통과
  if (
    cafeName.value && drinkName.value &&
    drinkCaffeine.value !== "" && drinkSugar.value !== "" &&
    drinkCaffeine.value >= 0 && drinkSugar.value >= 0
    ) {
      return true
    } {
    return false
  }
}

</script>

<style scoped>
.tip {
  cursor: help
}

</style>