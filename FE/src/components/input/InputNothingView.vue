<template>
  <div class="board-create-container">
    <div class="input-box">
      <div class="close" @click="closeInputNothingModal">
        <font-awesome-icon :icon="['fas', 'circle-xmark']" style="color: #000000;" size="xl"/>
      </div>

      <div class="input-container">
        <div>
          <h2>오늘 마신 카페 음료를 직접 입력해주세요</h2>
        </div>

        <div>
          <label for="cafeName" class="big-font">카페명</label>
          <input type="text" name="cafeName" id="cafeName"
          v-model="cafeName" placeholder="카페명을 입력해주세요"
          class="button_select select"/>
        </div>

        <div>
          <label for="drinkName" class="big-font">음료명</label>
          <input type="text" name="drinkName" id="drinkName"
          v-model="drinkName" placeholder="음료명을 입력해주세요"
          class="button_select select"/>
        </div>

        <div>
          <label for="drinkCaffeine" class="big-font">카페인</label>
          <input type="number" name="drinkCaffeine" id="drinkCaffeine"
          v-model="drinkCaffeine" class="button_select select">
        </div>

        <div>
          <label for="drinkSugar" class="big-font">　　당</label>
          <input type="number" name="drinkSugar" id="drinkSugar"
          v-model="drinkSugar" class="button_select select">
        </div>

        <div class="item-container">
          <button @click="goInputModal" class="button_input_color buttons">음료 선택</button>
          <span @mouseover="showToolTip = true" @mouseleave="showToolTip = false">
            <font-awesome-icon :icon="['fas', 'circle-question']" size="xl"/>
          </span>
          <div v-if="showToolTip" class="tip-container">
            <div class="tip">
              <p>여기에 음료 선택에 대한 자세한 설명을 작성합니다</p>
            </div>
          </div>
          <button @click="makeSubmit" class="button_caffeine buttons">입력완료</button>
        </div>
      </div>
    </div>
  </div>


</template>

<script setup>
import { ref } from 'vue';
import { useRecordsStore } from "@/stores/records";
import { isInputModal, isInputNothingModal } from '@/stores/util';

const store = useRecordsStore()

const cafeName = ref(null)
const drinkName = ref(null)
const drinkCaffeine = ref(0)
const drinkSugar = ref(0)

const showToolTip = ref(false)

// 모달창을 열고 닫기 위한 함수
const closeInputNothingModal = () => {
  isInputNothingModal.value = false
}

const openInputModal = () => {
  isInputModal.value = true
}

// 음료선택으로 이동하기 위한 함수
const goInputModal = () => {
  closeInputNothingModal()
  openInputModal()
}

const makeSubmit = () => {
  if (isValid()) {
    console.log('입력값이 올바릅니다. 데이터를 전송합니다.')

    const drink = {
      cafeName: cafeName.value,
      drinkName: drinkName.value,
      drinkCaffeine: drinkCaffeine.value.toFixed(1),
      drinkSugar: drinkSugar.value.toFixed(1)
    }

    console.log(drink)

    // 유효한 데이터를 백엔드로 전송 및 창 닫기
    store.createMyDrink(drink)
    alert('입력값이 올바릅니다. 데이터를 전송합니다.')
    closeInputNothingModal()

    } else {
      console.log('입력값이 올바르지 않습니다')
      alert('입력값이 올바르지 않습니다. 다시 확인해주세요.')
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

.board-create-container {
  position: fixed;
  width: 100vw;
  height: 100vh;
  /* max-width:614px;
  max-height: 584px; */
  background-color: rgba(128, 128, 128, 0.863) !important;
  top: 0;
  left: 0;
  margin: 0;
  z-index: 99 !important;
  display: flex;
  justify-content: center;
  align-items: center;
}

.input-box {
  background: #ffffff;
  border-radius: 30px;
  border-style: solid;
  border-color: #d9d9d9;
  border-width: 1px;
  position: absolute;
  width: 500px;
  height: 450px;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
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

.close {
  display: flex;
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
}

.big-font {
  margin-right: 10px;
  font-size: 20px;
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

.tip-container {
  position: relative;
}

.tip {
  position: fixed;
  top: 63%;
  left: 63%;
  transform: translate(-50%, -50%);
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  z-index: 100;
}
</style>