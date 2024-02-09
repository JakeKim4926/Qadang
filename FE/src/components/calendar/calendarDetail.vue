<template>
  <div class="board-create-container">
    <div class="detail_background">
      <!-- header -->
      <div class="all_caffeine">
        <span>
          <span class="all_caffeine_title">
            총 섭취한 카페인량
            <br />
          </span>
          <template
            v-if="
              userStore.userRDICaffeine / 2.0 <=
              accumulateStore.getAccumulateDay.accumulateCaffeine
            "
          >
            <span class="all_caffeine_value" style="color: red">{{
              accumulateStore.getAccumulateDay.accumulateCaffeine
            }}</span>
          </template>
          <template v-else>
            <span class="all_caffeine_value" style="color: green">{{
              accumulateStore.getAccumulateDay.accumulateCaffeine
            }}</span>
          </template>
          mg
        </span>
      </div>
      <div class="date_day">{{ getRecordDate }}</div>
      <div class="all_sugar">
        <span>
          <span class="all_sugar_title">
            총 섭취한 당량
            <br />
          </span>
          <template
            v-if="
              userStore.userRDISugar / 2.0 <=
              accumulateStore.getAccumulateDay.accumulateSugar
            "
          >
            <span class="all_sugar_value" style="color: red"
              >{{ accumulateStore.getAccumulateDay.accumulateSugar }}
            </span>
          </template>
          <template v-else>
            <span class="all_sugar_value" style="color: green"
              >{{ accumulateStore.getAccumulateDay.accumulateSugar }}
            </span>
          </template>
          g
        </span>
      </div>
      <font-awesome-icon
        :icon="['fas', 'circle-xmark']"
        class="image-174"
        @click="close"
      />

      <div class="line-76"></div>
      <!-- When it has data -->
      <template v-if="recordStore.getDayDrink.length">
        <template
          v-for="(drink, index) in recordStore.getDayDrink"
          :key="index"
        >
          <div class="record_box">
            <!-- record header -->
            <template v-if="drink.drinkId != 0">
              <img
                class="coffee_image"
                :src="drink.drinkUrl"
                alt="img 로딩 오류"
              />
            </template>
            <template v-else>
              <img
                class="coffee_image"
                src="@/components/icons/caffeine.png"
                alt="나만의 음료"
              />
            </template>

            <div class="no-001">No.00{{ index + 1 }}</div>
            <div class="coffee_name">{{ drink.drinkName }}</div>
            <div class="cafe_name">
              {{ drink.cafeName }}
              <template v-if="drink.drinkId == 0">(나만의 음료)</template>
            </div>

            <template v-if="now == recordDay">
              <a @click="editRecord(drink)" class="update_button">수정</a>
              <a @click="deleteRecord(drink)" class="delete_button">삭제</a>
            </template>
            <temlate v-else-if="index == 0">
              <h4 class="cant_access">오늘의 기록만 수정이 가능해유~</h4>
            </temlate>

            <svg
              class="line-74"
              width="338"
              height="1"
              viewBox="0 0 338 1"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path d="M1 0.5H337" stroke="black" stroke-linecap="round" />
            </svg>
            <!-- record content -->

            <svg
              class="line-75"
              width="338"
              height="1"
              viewBox="0 0 338 1"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path d="M1 0.5H337" stroke="black" stroke-linecap="round" />
            </svg>

            <div class="div5">카페인</div>
            <div class="caffeine_value">{{ drink.drinkCaffeine }} mg</div>

            <div class="div3">당</div>
            <div class="sugar_value">{{ drink.drinkSugar }} g</div>

            <div v-if="drink.drinkId != 0">
              <div class="shot_title">샷</div>
              <div class="shot_count">{{ drink.plusShot }} 회</div>

              <div class="div4">시럽</div>
              <div class="syrup_count">{{ drink.plusSyrup }} 회</div>
            </div>
          </div>
        </template>
      </template>
      <template v-else>
        <!-- when it doesn't have a data -->
        <div
          class="record_box"
          style="display: flex; justify-content: center; align-items: center"
        >
          <h3 style="text-align: center">
            해당 날짜에 마신 음료가 없습니다.
            <br />
            지난 일의 기록은 추가/삭제 등 수정이 불가능합니다.
          </h3>
        </div>
      </template>
    </div>
  </div>
</template>
<script setup>
import { useRecordsStore } from "@/stores/records";
import { useAccumulateStore } from "@/stores/accumulate";
import { ref, onMounted, computed, watch } from "vue";
import {
  isCalendarModal,
  isUpdateInputModal,
  isUpdateNothingModal,
  tempRecord,
} from "@/stores/util";
import { useUserStore } from "@/stores/user";

const recordStore = useRecordsStore();
const accumulateStore = useAccumulateStore();
const userStore = useUserStore();
// const useUserStore = useUserStore();

const recordDate = ref("");
const getRecordDate = computed(() => recordDate);

const recordDay = ref("");
const getRecordDay = computed(() => recordDay);

const now = ref("");

function close() {
  isCalendarModal.value = false;
}

function editRecord(recordDrink) {
  const confirmed = window.confirm("수정하겠습니까 ?");
  if (confirmed) {
    tempRecord.value = recordDrink;
    console.log("수정 할 꺼 ", tempRecord.value);

    console.log("@@@", tempRecord.value.drinkId);

    // 선택해서 입력했는지 직접 입력했는지에 따라
    // 뜨는 모달창을 다르게 변경
    if (tempRecord.value.drinkId) {
      // drinkId가 있다면 선택입력한 것이므로
      // 선택 입력을 수정할 수 있는 모달창 띄움
      close();
      isUpdateInputModal.value = true;
    } else {
      // drinkId가 없으면 직접 입력한 것이므로
      // 직접 입력을 수정할 수 있는 모달창 띄움
      close();
      isUpdateNothingModal.value = true;
    }
  }
}

async function deleteRecord(recordDrink) {
  const confirmed = window.confirm("정말 삭제를 원하십니까?");
  if (confirmed) {
    await recordStore.deleteDrink(recordDrink.recordId);

    // 데이터를 삭제한 후에 Vue의 반응성을 강제로 트리거하여 UI를 다시 렌더링합니다.
    console.log("day", recordDay);
    await recordStore.getDayDrink.splice(
      recordStore.getDayDrink.indexOf(recordDrink),
      1
    );
    await accumulateStore.day(recordDay);
  }
}

onMounted(() => {
  if (!sessionStorage.getItem("recordDate")) {
    sessionStorage.setItem("recordDate", recordStore.getRecordDate);
    recordDate.value = recordStore.getRecordDate;
  } else {
    if (
      sessionStorage.getItem("recordDate") != recordStore.getRecordDate &&
      recordStore.getRecordDate != ""
    ) {
      sessionStorage.removeItem("reocrdDate");
      sessionStorage.setItem("recordDate", recordStore.getRecordDate);
      recordDate.value = recordStore.getRecordDate;
    } else {
      recordDate.value = sessionStorage.getItem("recordDate");
    }
  }

  const date = new Date();
  const year = date.getFullYear();
  let month = date.getMonth() + 1; // Adding 1 to adjust for zero-based months
  month = month < 10 ? "0" + month.toString() : month.toString();
  let day = date.getDate();
  day = day < 10 ? "0" + day.toString() : day.toString();
  now.value = year.toString() + month.toString() + day.toString();

  recordDay.value = recordStore.getRecordDay;
  recordStore.researchDayDrink(recordDay);
  accumulateStore.day(recordDay);
});
</script>
<style scoped>
.board-create-container {
  position: fixed;
  width: 100vw;
  height: 100vh;
  background-color: rgba(128, 128, 128, 0.863) !important;
  top: 0;
  left: 0;
  z-index: 99 !important;
  display: flex;
  justify-content: center;
  align-items: center;
}

.detail_background {
  background: #ffffff;
  border-radius: 30px;
  border-style: solid;
  border-color: #d9d9d9;
  border-width: 1px;
  position: absolute;
  width: 57.05%;
  height: 60.89%;
  max-width: 870px;
  max-height: 780px;
  min-width: 685px;
  min-height: 480px;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  overflow-y: auto;
  /* 세로 스크롤이 내용이 넘칠 때만 생성되도록 설정 */
}

.record_box {
  background: #ffffff;
  border-radius: 30px;
  border-style: solid;
  border-color: #d9d9d9;
  border-width: 1px;
  position: relative;
  margin-bottom: 20px;
  right: 6.39%;
  left: 6.56%;
  width: 87.05%;
  bottom: 44.21%;
  top: 19.9%;
  height: 35.89%;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.all_caffeine {
  text-align: center;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 700;
  position: absolute;
  right: 40.59%;
  left: 33.16%;
  width: 26.25%;
  bottom: 89.4%;
  top: 7.99%;
  height: 2.61%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.all_caffeine_title {
  color: #000000;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 700;
}

.all_caffeine_value {
  color: #000000;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 700;
}

.date_day {
  color: #000000;
  text-align: center;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 25px;
  line-height: 18px;
  font-weight: 700;
  position: absolute;
  right: 66.32%;
  left: 7.43%;
  width: 26.25%;
  bottom: 89.89%;
  top: 7.5%;
  height: 2.61%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.all_sugar {
  text-align: center;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 700;
  position: absolute;
  right: 15.54%;
  left: 58.2%;
  width: 26.25%;
  bottom: 89.4%;
  top: 7.99%;
  height: 2.61%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.all_sugar_title {
  color: #000000;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 700;
}

.all_sugar_value {
  color: #000000;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 700;
}

.line-76 {
  border-style: solid;
  border-color: #846046;
  border-width: 2px 0 0 0;
  position: absolute;
  right: 6.56%;
  left: 6.74%;
  width: 86.7%;
  bottom: 84.67%;
  top: 15.33%;
  height: 0%;
  transform-origin: 0 0;
  transform: rotate(-0.114deg) scale(1, 1);
}

.shot_title {
  color: #000000;
  text-align: left;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 700;
  position: absolute;
  right: 60.89%;
  left: 31%;
  width: 11.57%;
  bottom: 20.53%;
  top: 74.54%;
  height: 2.94%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.cafe_name {
  color: #000000;
  text-align: left;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 11px;
  line-height: 18px;
  font-weight: 700;
  position: absolute;
  right: 58.55%;
  left: 29.71%;
  width: 50.95%;
  bottom: 65.42%;
  top: 28.97%;
  height: 16.61%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.no-001 {
  color: #000000;
  text-align: left;
  font-family: "DmSans-Regular", sans-serif;
  font-size: 11px;
  line-height: 18px;
  font-weight: 400;
  position: absolute;
  right: 58.55%;
  left: 29.71%;
  width: 11.74%;
  bottom: 72.27%;
  top: 14.12%;
  height: 2.61%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.line-74 {
  height: auto;
  position: absolute;
  right: 12.44%;
  left: 29.53%;
  width: 63.03%;
  bottom: 64.19%;
  top: 44.81%;
  height: 1%;
  overflow: visible;
}

.line-75 {
  height: auto;
  position: absolute;
  right: 12.44%;
  left: 29.53%;
  width: 63.03%;
  bottom: 50.65%;
  top: 88.2%;
  height: 1%;
  overflow: visible;
}

.div3 {
  color: #000000;
  text-align: left;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 700;
  position: absolute;
  right: 20.81%;
  left: 66.12%;
  width: 8.12%;
  bottom: 59.22%;
  top: 56.01%;
  height: 2.61%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.div4 {
  color: #000000;
  text-align: left;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 700;
  position: absolute;
  right: 26.08%;
  left: 65.04%;
  width: 10.88%;
  bottom: 52.53%;
  top: 74.54%;
  height: 2.61%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.div5 {
  color: #000000;
  text-align: left;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 700;
  position: absolute;
  right: 58.89%;
  left: 29.53%;
  width: 11.57%;
  bottom: 59.05%;
  top: 56.01%;
  height: 2.61%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.caffeine_value {
  color: #000000;
  text-align: left;
  font-family: "DmSans-Regular", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 400;
  position: absolute;
  right: 42.49%;
  left: 45.94%;
  width: 11.57%;
  bottom: 59.38%;
  top: 55.01%;
  height: 2.61%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.shot_count {
  color: #000000;
  text-align: left;
  font-family: "DmSans-Regular", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 400;
  position: absolute;
  right: 44.73%;
  left: 49.57%;
  width: 5.7%;
  bottom: 52.69%;
  top: 74.54%;
  height: 2.61%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.sugar_value {
  color: #000000;
  text-align: left;
  font-family: "DmSans-Regular", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 400;
  position: absolute;
  right: 15.54%;
  left: 84.41%;
  width: 6.04%;
  bottom: 59.38%;
  top: 55.01%;
  height: 2.61%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.syrup_count {
  color: #000000;
  text-align: left;
  font-family: "DmSans-Regular", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 400;
  position: absolute;
  right: 15.37%;
  left: 84.41%;
  width: 6.22%;
  bottom: 52.53%;
  top: 74.54%;
  height: 2.61%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.coffee_name {
  color: #000000;
  text-align: left;
  font-family: "DmSans-Bold", sans-serif;
  font-size: 15px;
  line-height: 18px;
  font-weight: 700;
  position: absolute;
  right: 20.51%;
  left: 29.53%;
  width: 50.95%;
  bottom: 66.72%;
  top: 21.75%;
  height: 10.53%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.coffee_image {
  position: relative;
  right: 21.16%;
  left: 5.43%;
  width: 18.42%;
  bottom: 49.1%;
  top: 15.26%;
  height: 70.63%;
  object-fit: cover;
}

.update_button {
  background: #8a6d58;
  color: #ffffff;
  font-size: 13px;
  text-align: center;
  font-family: "DmSans-Bold", sans-serif;
  border-radius: 90px;
  position: absolute;
  right: 21.24%;
  left: 77.85%;
  width: 6.91%;
  bottom: 70.64%;
  top: 12.12%;
  height: 12.24%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.delete_button {
  background: #b29f91;
  color: #ffffff;
  font-size: 13px;
  text-align: center;
  font-family: "DmSans-Bold", sans-serif;
  border-radius: 90px;
  position: absolute;
  right: 12.78%;
  left: 85.31%;
  width: 6.91%;
  bottom: 70.64%;
  top: 12.12%;
  height: 12.24%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.cant_access {
  color: gray;
  font-size: 13px;
  text-align: center;
  font-family: "DmSans-Bold", sans-serif;
  border-radius: 90px;
  position: absolute;
  right: 21.24%;
  left: 63.85%;
  width: 32.91%;
  bottom: 70.64%;
  top: 0.12%;
  height: 12.24%;
}

.image-174 {
  position: absolute;
  right: 6.04%;
  left: 86.36%;
  width: 7.6%;
  bottom: 88.74%;
  top: 4.08%;
  height: 7.18%;
  object-fit: cover;
  cursor: pointer;
}
</style>
