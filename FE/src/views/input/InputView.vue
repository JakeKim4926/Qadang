<template>
  <div>
    <h1>Hello Input</h1>
  </div>

  <div>
    x버튼
  </div>

  <div>
    <h2>오늘 마신 카페 음료를 선택해주세요</h2>
    <label for="cafeSelect">카페명: </label>
    <select name="cafeSelect" id="cafeSelect" v-model="cafeId" @change="changeCafeDrinkList">
      <option v-for="cafe in drinkStore.getCafeList"
      :key="cafe.cafeId"
      :value="cafe.cafeId">
        {{ cafe.cafeName }}
      </option>
    </select>

    <div v-if="cafeId">
      <label for="drinkSelect">음료명: </label>
      <select name="drinkSelect" id="drinkSelect" v-model="drinkId">
      <option v-for="drink in drinkStore.getCafeDrinkList"
      :key="drink.drinkId"
      :value="drink.drinkId">
        {{ drink.drinkName }}
      </option>
    </select>
    </div>

    <div v-else>
      <label for="drinkSelect">음료명: </label>
      <select name="drinkSelect" id="drinkSelect">
        <option value="" disabled selected>카페를 먼저 선택해주세요</option>
      </select>
    </div>
    
    <div>
      <p>샷: 1샷 - 카페인 75mg</p>
      <button :disabled="!minusCaffeineButton()" @click="minusCaffeine">-</button>
      {{ plusShot }}
      <button :disabled="!plusCaffeineButton()" @click="plusCaffeine">+</button>
    </div>

    <div>
      <p>시럽: 1시럽 - 당 6g</p>
      <button :disabled="!minusSugarButton()" @click="minusSugar">-</button>
      {{ plusSyrup }}
      <button :disabled="!plusSugarButton()" @click="plusSugar">+</button>
    </div>

  </div>

  <div>
    <RouterLink :to="{name: 'inputNothing'}">여기없어용</RouterLink>
    <span class="tip" @mouseover="showToolTip = true" @mouseleave="showToolTip = false">물음표버튼</span>
    <div v-if="showToolTip">여기에 여기없어용에 대한 자세한 설명을 작성합니다</div>
    <button @click="drinkSubmit">입력완료</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { onMounted } from 'vue';

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

const showToolTip = ref(false)

// 데이터를 가져오기 위한 함수
onMounted(() => {
  drinkStore.researchCafe()
})

// 카페를 선택하면 해당 카페 음료를 가져오기 위한 함수
const changeCafeDrinkList = () => {
  if (cafeId.value) {
    drinkStore.researchCafeDrinks(cafeId.value)
  }
}

// 샷, 시럽 - 버튼 활성화를 위한 함수
const minusCaffeineButton = () => {
  if (cafeName.value && drinkName.value && drinkCaffeine.value + 75 * (plusShot.value - 1) >= 0) {
    return true
  } else {
    return false
  }
}

const minusSugarButton = () => {
  if (cafeName.value && drinkName.value && drinkSugar.value + 6 * (plusSyrup.value - 1) >= 0) {
    return true
  } else {
    return false
  }
}

// 샷, 시럽 + 버튼 활성화를 위한 함수
const plusCaffeineButton = () => {
  if (cafeName.value && drinkName.value) {
    return true
  } else {
    return false
  }
}

const plusSugarButton = () => {
  if (cafeName.value && drinkName.value) {
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
  if (cafeName.value && drinkName.value) {
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

<style scoped>
.tip {
  cursor: help
}
</style>