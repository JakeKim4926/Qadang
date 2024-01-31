<template>
  <div>
    <h1>Hello Input</h1>
  </div>

  <div>
    x버튼
  </div>

  <div class="input-container">
    <div>
      <h2>오늘 마신 카페 음료를 선택해주세요</h2>
    </div>

    <div>
      <label for="cafeSelect" class="big-font">카페명</label>
      <select name="cafeSelect" id="cafeSelect"
      v-model="cafeId" @change="changeCafeDrinkList" class="button_select select">
        <option v-for="cafe in drinkStore.getCafeList"
        :key="cafe.cafeId"
        :value="cafe.cafeId">
          {{ cafe.cafeName }}
        </option>
      </select>
    </div>
    

    <div v-if="cafeId">
      <label for="drinkSelect" class="big-font">음료명</label>
      <select name="drinkSelect" id="drinkSelect"
      v-model="drinkInfo" @change="changeDrinkInfo" class="button_select select">
      <option v-for="drink in drinkStore.getCafeDrinkList"
      :key="drink.drinkId"
      :value="drink">
        {{ drink.drinkName }}
      </option>
    </select>
    </div>

    <div v-else>
      <label for="drinkSelect" class="big-font">음료명</label>
      <select name="drinkSelect" id="drinkSelect" class="button_select select">
        <option value="" disabled selected>카페를 먼저 선택해주세요</option>
      </select>
    </div>
    
    <div class="item-container">
      <label for="plusShot" class="big-font">샷</label>
      <p class="mid-font">1샷 - 카페인 75mg</p>
      <button :disabled="!getminusCaffeineButton" @click="minusCaffeine" class="pm-button">
        <font-awesome-icon :icon="['fas', 'minus']" style="color: #000000;" />
      </button>
      {{ plusShot }}
      <button :disabled="!getplusCaffeineButton" @click="plusCaffeine" class="pm-button">
        <font-awesome-icon :icon="['fas', 'plus']" style="color: #000000;" />
      </button>
    </div>

    <div class="item-container">
      <label for="plusSyrup" class="big-font">시럽</label>
      <p class="mid-font">1시럽 - 당 6g</p>
      <button :disabled="!getminusSugarButton" @click="minusSugar" class="pm-button">
        <font-awesome-icon :icon="['fas', 'minus']" style="color: #000000;" />
      </button>
      {{ plusSyrup }}
      <button :disabled="!getplusSugarButton" @click="plusSugar" class="pm-button">
        <font-awesome-icon :icon="['fas', 'plus']" style="color: #000000;" />
      </button>
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

    <div class="item-container">
      <button @click="goInputNothing" class="button_input_color buttons">여기없어용</button>
      <span @mouseover="showToolTip = true" @mouseleave="showToolTip = false">
        <font-awesome-icon :icon="['fas', 'circle-question']" size="xl"/>
      </span>
      <div v-if="showToolTip" class="tip">
        <p>여기에 여기없어용에 대한 자세한 설명을 작성합니다</p>
      </div>
      <button @click="drinkSubmit" class="button_caffeine buttons">입력완료</button>
    </div>

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

div {
  margin: 10px;
}

.input-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.item-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pm-button {
  border: none;
}

.big-font {
  margin-right: 10px;
  font-size: 20px;
}

.mid-font {
  font-size: 15px;
  width: 300px;
}

.select {
  border-radius: 22px;
  border: 2px solid #846046;
  padding: 10px 20px;
  width: 353px;
}

.button_input_color {
  margin-right: 5px;
  color: black;
  font-size: 20px;
  font-weight: bold;
}

.button_caffeine {
  margin-left: 80px;
  color: white;
  font-size: 20px;
  font-weight: bold;
}
.buttons {
  width: 150px;
  height: 60px;
  border: none;
}

</style>