<template>
    <div class="board-create-container">
        <div class="rectangle-4333">
            <div class="rectangle-4330"></div>
            <div class="div">샷</div>
            <div class="div2">스타벅스</div>
            <div class="no-001">No.001</div>
            <svg class="line-74" width="338" height="1" viewBox="0 0 338 1" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M1 0.5H337" stroke="black" stroke-linecap="round" />
            </svg>
            <svg class="line-75" width="338" height="1" viewBox="0 0 338 1" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M1 0.5H337" stroke="black" stroke-linecap="round" />
            </svg>
            <div class="div3">당</div>
            <div class="div4">시럽</div>
            <div class="div5">카페인</div>
            <div class="_150-3-mg">150.3 mg</div>
            <div class="_0">0 회</div>
            <div class="_0-0-g">0.0 g</div>
            <div class="_02">0 회</div>
            <div class="_150-3-mg2">
                <span>
                    <span class="_150-3-mg-2-span">
                        총 섭취한 카페인량
                        <br />
                    </span>
                    <span class="_150-3-mg-2-span2">150.3 mg</span>
                </span>
            </div>
            <div class="_17">17 금요일</div>
            <div class="_0-0-g2">
                <span>
                    <span class="_0-0-g-2-span">
                        총 섭취한 당량
                        <br />
                    </span>
                    <span class="_0-0-g-2-span2">0.0 g</span>
                </span>
            </div>
            <div class="line-76"></div>
            <div class="tall">아메리카노 Tall</div>
            <img class="image-removebg-preview-4-10" src="https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[94]_20210430103337157.jpg" />
            <!-- <font-awesome-icon :icon="['fas', 'circle-xmark']" class="image-removebg-preview-4-10" /> -->
            <div class="rectangle-4331"></div>
            <div class="div6">수정</div>
            <div class="rectangle-4332"></div>
            <div class="div7">삭제</div>
            <!-- <div class="rectangle-4333"></div> -->
            <!-- <div class="group-8771">
                <div class="ellipse-2"></div>
                <svg class="group-1" width="32" height="29" viewBox="0 0 32 29" fill="none"
                    xmlns="http://www.w3.org/2000/svg">
                    <path d="M15.7847 0.665527L15.7847 28.8989" stroke="white" stroke-width="12" stroke-linecap="round" />
                    <path d="M0.100098 14.7754H31.4705" stroke="white" stroke-width="12" stroke-linecap="round" />
                </svg>
            </div> -->
            <!-- <img class="image-174" src="" /> -->
            <font-awesome-icon :icon="['fas', 'circle-xmark']" class="image-174" @click="close" />
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

.component-1,
.component-1 * {
    box-sizing: border-box;
}

.component-1 {
    height: 613px;
    position: relative;
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
    z-index: 99 !important;
    display: flex;
    justify-content: center;
    align-items: center;
}


.rectangle-4329 {
    background: #ffffff;
    border-radius: 20px;
    border-style: solid;
    border-color: #d9d9d9;
    border-width: 1px;
    position: absolute;
    right: 0%;
    left: 0%;
    width: 100%;
    bottom: 0%;
    top: 0%;
    height: 100%;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.rectangle-4330 {
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

.div {
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

.div2 {
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

._150-3-mg {
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

._0 {
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

._0-0-g {
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

._02 {
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

._150-3-mg2 {
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

._150-3-mg-2-span {
    color: #000000;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 15px;
    line-height: 18px;
    font-weight: 700;
}

._150-3-mg-2-span2 {
    color: #ff0000;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 15px;
    line-height: 18px;
    font-weight: 700;
}

._17 {
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

._0-0-g2 {
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

._0-0-g-2-span {
    color: #000000;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 15px;
    line-height: 18px;
    font-weight: 700;
}

._0-0-g-2-span2 {
    color: #008000;
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

.tall {
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

.image-removebg-preview-4-10 {
    position: absolute;
    right: 71.16%;
    left: 8.43%;
    width: 18.42%;
    bottom: 49.1%;
    top: 26.26%;
    height: 24.63%;
    object-fit: cover;
}

.rectangle-4331 {
    background: #8a6d58;
    border-radius: 90px;
    position: absolute;
    right: 21.24%;
    left: 71.85%;
    width: 6.91%;
    bottom: 70.64%;
    top: 25.12%;
    height: 4.24%;
}

.div6 {
    color: #ffffff;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 11px;
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

.rectangle-4332 {
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

.div7 {
    color: #000000;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 11px;
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

.rectangle-4333 {
    background: #ffffff;
    border-radius: 30px;
    border-style: solid;
    border-color: #d9d9d9;
    border-width: 1px;
    position: absolute;
    right: 25.39%;
    left: 23.56%;
    width: 57.05%;
    bottom: 3.92%;
    top: 10.2%;
    height: 60.89%;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.group-8771 {
    position: absolute;
    inset: 0;
    z-index: 1;
}

.ellipse-2 {
    background: #efefef;
    border-radius: 50%;
    position: absolute;
    right: 43.18%;
    left: 43.52%;
    width: 13.3%;
    bottom: 16.48%;
    top: 70.96%;
    height: 12.56%;
}

.group-1 {
    width: 31.37px;
    height: 28.23px;
    position: absolute;
    right: 47.07%;
    left: 47.51%;
    width: 5.42%;
    bottom: 20.24%;
    top: 75.15%;
    height: 4.61%;
    overflow: visible;
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
  