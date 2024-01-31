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
                    <span class="all_caffeine_value">150.3 mg</span>
                </span>
            </div>
            <div class="date_day">17 금요일</div>
            <div class="all_sugar">
                <span>
                    <span class="all_sugar_title">
                        총 섭취한 당량
                        <br />
                    </span>
                    <span class="all_sugar_value">0.0 g</span>
                </span>
            </div>
            <font-awesome-icon :icon="['fas', 'circle-xmark']" class="image-174" @click="close" />
            
            <div class="line-76"></div>
            
            <div class="record_box"></div>
            <!-- record header -->
            <img class="coffee_image" src="https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[94]_20210430103337157.jpg" />
            <div class="no-001">No.001</div>
            <div class="coffee_name">아메리카노 Tall</div>
            <div class="cafe_name">스타벅스</div>
            <div class="update_button"></div>
            <div class="update_font">수정</div>
            <div class="delete_button"></div>
            <div class="delete_font">삭제</div>

            <svg class="line-74" width="338" height="1" viewBox="0 0 338 1" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M1 0.5H337" stroke="black" stroke-linecap="round" />
            </svg>
            <!-- record content -->
            
            <svg class="line-75" width="338" height="1" viewBox="0 0 338 1" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M1 0.5H337" stroke="black" stroke-linecap="round" />
            </svg>
            
            <div class="div5">카페인</div>
            <div class="caffeine_value">150.3 mg</div>

            <div class="div3">당</div>
            <div class="sugar_value">0.0 g</div>

            <div class="shot_title">샷</div>
            <div class="shot_count">0 회</div>

            <div class="div4">시럽</div>
            <div class="syrup_count">0 회</div>
        </div>
    </div>
</template>
<script setup>
import { useRecordsStore } from '@/stores/records';
import { useAccumulateStore } from '@/stores/accumulate';
import { ref, onMounted, computed } from 'vue';
import { isCalendarModal } from "@/stores/util"


const recordStore = useRecordsStore();
const accumulateStore = useAccumulateStore();

const recordDate = ref('');
const getRecordDate = computed(() => recordDate);

const recordDay = ref('');
const getRecordDay = computed(() => recordDay);

function close() {
    isCalendarModal.value = false;
}

onMounted(() => {
    if (!sessionStorage.getItem('recordDate')) {
        sessionStorage.setItem('recordDate', recordStore.getRecordDate);
        recordDate.value = recordStore.getRecordDate;
    } else {
        if (sessionStorage.getItem('recordDate') != recordStore.getRecordDate && recordStore.getRecordDate != "") {
            sessionStorage.removeItem('reocrdDate');
            sessionStorage.setItem('recordDate', recordStore.getRecordDate);
            recordDate.value = recordStore.getRecordDate;
        } else {
            recordDate.value = sessionStorage.getItem('recordDate');
        }
    }

    recordDay.value = recordStore.getRecordDay;

    recordStore.researchDayDrink(recordDay);

})


</script>
<style scoped>

.board-create-container {
    position: fixed;
    width: 100vw;
    height: 100vh;
    /* max-width:614px;
    max-height: 584px; */
    background-color: rgba(128, 128, 128, 0.863) !important;
    top: 0;
    left: 0;
    z-index: 99 !important;
    display: flex;
    justify-content: center;
    align-items: center;
}

.record_box {
    background: #ffffff;
    border-radius: 30px;
    border-style: solid;
    border-color: #d9d9d9;
    border-width: 1px;
    position: absolute;
    right: 6.39%;
    left: 6.56%;
    width: 87.05%;
    bottom: 44.21%;
    top: 19.9%;
    height: 35.89%;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.shot_title {
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
    bottom: 52.53%;
    top: 44.54%;
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
    width: 11.74%;
    bottom: 65.42%;
    top: 31.97%;
    height: 2.61%;
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
    top: 25.12%;
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
    width: 58.03%;
    bottom: 64.19%;
    top: 35.81%;
    height: 1%;
    overflow: visible;
}

.line-75 {
    height: auto;
    position: absolute;
    right: 12.44%;
    left: 29.53%;
    width: 58.03%;
    bottom: 50.65%;
    top: 49.35%;
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
    right: 27.81%;
    left: 64.08%;
    width: 8.12%;
    bottom: 59.22%;
    top: 38.17%;
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
    left: 63.04%;
    width: 10.88%;
    bottom: 52.53%;
    top: 44.86%;
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
    top: 38.34%;
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
    top: 38.01%;
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
    top: 44.7%;
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
    left: 78.41%;
    width: 6.04%;
    bottom: 59.38%;
    top: 38.01%;
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
    left: 78.41%;
    width: 6.22%;
    bottom: 52.53%;
    top: 44.86%;
    height: 2.61%;
    display: flex;
    align-items: center;
    justify-content: flex-start;
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

.coffee_name {
    color: #000000;
    text-align: left;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 15px;
    line-height: 18px;
    font-weight: 700;
    position: absolute;
    right: 38.51%;
    left: 29.53%;
    width: 31.95%;
    bottom: 66.72%;
    top: 26.75%;
    height: 6.53%;
    display: flex;
    align-items: center;
    justify-content: flex-start;
}

.coffee_image {
    position: absolute;
    right: 71.16%;
    left: 8.43%;
    width: 18.42%;
    bottom: 49.1%;
    top: 26.26%;
    height: 24.63%;
    object-fit: cover;
}

.update_button {
    background: #8a6d58;
    border-radius: 90px;
    position: absolute;
    right: 21.24%;
    left: 71.85%;
    width: 6.91%;
    bottom: 70.64%;
    top: 25.12%;
    height: 4.24%;
    cursor: pointer;
}

.update_font {
    color: #ffffff;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 13px;
    line-height: 18px;
    font-weight: 700;
    position: absolute;
    right: 22.11%;
    left: 72.88%;
    width: 5.01%;
    bottom: 71.45%;
    top: 25.77%;
    height: 2.77%;
}

.delete_button {
    background: #b29f91;
    border-radius: 90px;
    position: absolute;
    right: 12.78%;
    left: 80.31%;
    width: 6.91%;
    bottom: 70.64%;
    top: 25.12%;
    height: 4.24%;
}

.delete_font {
    color: #000000;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 13px;
    line-height: 18px;
    font-weight: 700;
    position: absolute;
    right: 8.98%;
    left: 76.68%;
    width: 14.34%;
    bottom: 71.45%;
    top: 25.77%;
    height: 2.77%;
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
    min-width: 575px;
    min-height: 480px;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
    overflow-y: auto; /* 세로 스크롤이 내용이 넘칠 때만 생성되도록 설정 */
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
  