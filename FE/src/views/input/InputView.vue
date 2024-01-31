<template>
  <div>
    <h1>Hello Input</h1>
  </div>

  <div>
    x버튼
  </div>

  <div>
    <h2>오늘 마신 카페 음료를 선택해주세요</h2>

    <!-- 초기에 카페명을 먼저 선택-->
    <label for="cafeSelect">카페명: </label>

    <!-- 카페명을 선택하기 전 선택할 수 없음 -->
    <!-- 카페 카테고리에 따라 음료를 가지고 와 select문 표시-->
    <div v-if="cafeId">
      <label for="drinkSelect">음료명: 선택할 수 있음</label>
    </div>

    <div v-else>
      <label for="drinkSelect">음료명: 선택할 수 없음</label>
    </div>
    
    <!-- 음료를 선택하기 전 샷, 시럽 선택할 수 없게 설정 -->
    <!-- +, - 미만일 경우 블락 처리, 바로바로 계산되어야 함 -->
    <div>
      <p>샷: 1샷 - 카페인 75mg</p>
      <button :disabled="!minusCaffeineButton()" @click="minusCaffeine">-</button>
      {{ plusShot }}
      <button @click="plusCaffeine">+</button>
    </div>

    <div>
      <p>시럽: 1시럽 - 당 6g</p>
      <button :disabled="!minusSugarButton()" @click="minusSugar">-</button>
      {{ plusSyrup }}
      <button @click="plusSugar">+</button>
    </div>

  </div>

  <div>
    <RouterLink :to="{name: 'inputNothing'}">여기없어용</RouterLink>
    물음표 버튼
    <button @click="drinkSubmit">입력완료</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useDrinksStore } from "@/stores/drinks";
import { useRecordsStore } from "@/stores/records"

const drinkStore = useDrinksStore()
const recordsStore = useRecordsStore()

// 음료 생성을 위해 보내줄 데이터
const cafeName = ref(null)
const drinkName = ref(null)
const plusShot = ref(0)
const plusSyrup = ref(0)

// 카페 id, 음료 id, 각 음료의 카페인 함량, 당 함량
const cafeId = ref(0)
const drinkId = ref(0)
const drinkCaffeine = ref(0)
const drinkSugar = ref(0)

// 샷, 시럽 - 버튼 활성화를 위한 함수
const minusCaffeineButton = () => {
  if (drinkCaffeine.value + 75 * (plusShot.value - 1) >= 0) {
    return true
  } else {
    return false
  }
}

const minusSugarButton = () => {
  if (drinkSugar.value + 6 * (plusSyrup.value - 1) >= 0) {
    return true
  } else {
    return false
  }
}

const minusCaffeine = () => {
  plusShot.value -= 1
}
const plusCaffeine = () => {
  plusShot.value += 1
}
const minusSugar = () => {
  plusSyrup.value -= 1
}
const plusSugar = () => {
  plusSyrup.value += 1
}


const drinkSubmit = () => {
  if (cafeName && drinkName) {
    console.log('입력값이 올바릅니다. 데이터를 전송합니다.')

    // 음료 생성을 위해 보내줄 데이터
    const drink = {
      drinkId: drinkId,
      cafeName: cafeName,
      drinkName: drinkName,
      plusShot: plusShot,
      plusSyrup: plusSyrup,
    }

    // 데이터 전송
    recordsStore.createCafeDrink(drink)

    } else {
      console.log('입력값이 올바르지 않습니다')
    }
  }

</script>

<style lang="scss" scoped></style>