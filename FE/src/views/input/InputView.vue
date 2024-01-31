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
    <select name="cafeSelect" id="cafeSelect"
    v-model="cafeId" @change="changeCafeDrinkList" class="button_select">
      <option v-for="cafe in drinkStore.getCafeList"
      :key="cafe.cafeId"
      :value="cafe.cafeId">
        {{ cafe.cafeName }}
      </option>
    </select>

    <div v-if="cafeId">
      <label for="drinkSelect">음료명: </label>
      <select name="drinkSelect" id="drinkSelect"
      v-model="drinkInfo" @change="changeDrinkInfo" class="button_select">
      <option v-for="drink in drinkStore.getCafeDrinkList"
      :key="drink.drinkId"
      :value="drink">
        {{ drink.drinkName }}
      </option>
    </select>
    </div>

    <div v-else>
      <label for="drinkSelect">음료명: </label>
      <select name="drinkSelect" id="drinkSelect" class="button_select">
        <option value="" disabled selected>카페를 먼저 선택해주세요</option>
      </select>
    </div>
    
    <div>
      <p>샷: 1샷 - 카페인 75mg</p>
      <button :disabled="!getminusCaffeineButton" @click="minusCaffeine">-</button>
      {{ plusShot }}
      <button :disabled="!getplusCaffeineButton" @click="plusCaffeine">+</button>
    </div>

    <div>
      <p>시럽: 1시럽 - 당 6g</p>
      <button :disabled="!getminusSugarButton" @click="minusSugar">-</button>
      {{ plusSyrup }}
      <button :disabled="!getplusSugarButton" @click="plusSugar">+</button>
    </div>

    <!-- {{ drinkInfo }}

    {{ drinkCaffeine }}
    {{ drinkSugar }}

    {{ minusCaffeineButton }}
    {{ plusCaffeineButton }}
    {{ minusSugarButton }}
    {{ plusSugarButton }}

    {{ plusShot }}
    {{ plusSyrup }} -->

  </div>

  <div>
    <button @click="goInputNothing" class="button_input_color">여기없어용</button>
    <span @mouseover="showToolTip = true" @mouseleave="showToolTip = false"><font-awesome-icon :icon="['fas', 'circle-question']" /></span>
    <div v-if="showToolTip" class="tip">
      <p>여기에 여기없어용에 대한 자세한 설명을 작성합니다</p>
    </div>
    <button @click="drinkSubmit">입력완료</button>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue';
import { onMounted } from 'vue';

import { useDrinksStore } from "@/stores/drinks";
import { useRecordsStore } from "@/stores/records"
import router from '@/router';

const drinkStore = useDrinksStore()
const recordsStore = useRecordsStore()

// 음료 전체 정보
const drinkInfo = ref({})

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

const minusCaffeineButton = ref(false)
const minusSugarButton = ref(false)
const plusCaffeineButton = ref(false)
const plusSugarButton = ref(false)

// 도움말
const showToolTip = ref(false)

// 데이터를 가져오기 위한 함수
onMounted(() => {
  drinkStore.researchCafe()
})

// 이전 선택값 초기화해주는 함수
const reset = () => {
  drinkInfo.value = null
  cafeName.value = null
  drinkName.value = null
  plusShot.value = 0
  plusSyrup.value = 0

  drinkId.value = 0
  drinkCaffeine.value = 0
  drinkSugar.value = 0

  minusCaffeineButton.value = false
  minusSugarButton.value = false
  plusCaffeineButton.value = false
  plusSugarButton.value = false
}

// 카페를 선택하면 해당 카페 음료를 가져오기 위한 함수
const changeCafeDrinkList = () => {
  if (cafeId.value) {
    reset()
    drinkStore.researchCafeDrinks(cafeId.value)
  }
}

// 샷, 시럽 버튼 활성화를 위한 computed => getter
const getminusCaffeineButton = computed(() => {
  return minusCaffeineButton.value
})

const getminusSugarButton = computed(() => {
  return minusSugarButton.value
})

const getplusCaffeineButton = computed(() => {
  return plusCaffeineButton.value
})

const getplusSugarButton = computed(() => {
  return plusSugarButton.value
})

// 샷, 시럽 버튼 활성화를 위한 함수 => Action
const activeminusCaffeineButton = () => {
  if (cafeName.value && drinkName.value && drinkCaffeine.value + 75 * (plusShot.value - 1) >= 0) {
    return minusCaffeineButton.value = true
  } else {
    return minusCaffeineButton.value = false
  }
}

const activminusSugarButton = () => {
  if (cafeName.value && drinkName.value && drinkSugar.value + 6 * (plusSyrup.value - 1) >= 0) {
    return minusSugarButton.value = true
  } else {
    return minusSugarButton.value = false
  }
}

const activeplusCaffeineButton = () => {
  if (cafeName.value && drinkName.value) {
    return plusCaffeineButton.value = true
  } else {
    return plusCaffeineButton.value = false
  }
}

const activeplusSugarButton = () => {
  if (cafeName.value && drinkName.value) {
    return plusSugarButton.value = true
  } else {
    return plusSugarButton.value = false
  }
}

// 카페 음료를 선택하면 해당 카페 음료 정보를 가져오기 위한 함수
const changeDrinkInfo = () => {
  if (drinkInfo.value) {
    cafeName.value = drinkInfo.value.cafeName
    drinkId.value = drinkInfo.value.drinkId
    drinkName.value = drinkInfo.value.drinkName
    drinkCaffeine.value = drinkInfo.value.drinkCaffeine
    drinkSugar.value = drinkInfo.value.drinkSugar

    activeminusCaffeineButton()
    activminusSugarButton()
    activeplusCaffeineButton()
    activeplusSugarButton()
  }
}

const minusCaffeine = () => {
  plusShot.value -= 1
  activeminusCaffeineButton()
  activminusSugarButton()
  activeplusCaffeineButton()
  activeplusSugarButton()
}
const plusCaffeine = () => {
  plusShot.value += 1
  activeminusCaffeineButton()
  activminusSugarButton()
  activeplusCaffeineButton()
  activeplusSugarButton()
}
const minusSugar = () => {
  plusSyrup.value -= 1
  activeminusCaffeineButton()
  activminusSugarButton()
  activeplusCaffeineButton()
  activeplusSugarButton()
}
const plusSugar = () => {
  plusSyrup.value += 1
  activeminusCaffeineButton()
  activminusSugarButton()
  activeplusCaffeineButton()
  activeplusSugarButton()
}

// 생성된 음료 데이터 전송
const drinkSubmit = () => {
  if (cafeName.value && drinkName.value) {
    console.log('입력값이 올바릅니다. 데이터를 전송합니다.')

    // 음료 생성을 위해 보내줄 데이터
    const drink = {
      drinkId: drinkId.value,
      cafeName: cafeName.value,
      drinkName: drinkName.value,
      plusShot: plusShot.value,
      plusSyrup: plusSyrup.value,
    }

    console.log(drink)

    // 데이터 전송
    recordsStore.createCafeDrink(drink)

    } else {
      console.log('입력값이 올바르지 않습니다')
    }
  }

// 여기없어용 버튼
const goInputNothing = () => {
  router.push({name: 'inputNothing'})
}
</script>

<style scoped>
@import "../../components/font/font.css";
@import "../../components/color/color.css";

h2 {
  color: #562B1A;
  font-weight: bold;
  text-align: center;  
}
</style>