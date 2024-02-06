<template>
    <div class="_1">
        <div class="component-5 ">
            <div class="rectangle-4273">
                <div class="title01">카페에서 마시는 최애 음료는?</div>

                <div class="cafe_name">카페명</div>
                <select id="cafeSelect" v-model="cafeId" class="rectangle-4271" @change="selectCafe"
                    style="text-align: center; ">
                    <option v-for="cafe in drinkStore.getCafeList" :key="cafe.cafeId" :value="cafe.cafeId">
                        {{ cafe.cafeName }}
                    </option>
                </select>
                <div class="div5">음료명</div>
                <select id="drinkSelect" v-model="drinkTemp" class="rectangle-4272" @change="selectDrink"
                    style="text-align: center;" :disabled="!cafeId">
                    <option v-for="drink in drinkStore.getCafeDrinkList" :key="drink.drinkId" :value="drink">
                        {{ drink.drinkName }}
                    </option>
                </select>

            </div>

            <div class="rectangle-4274">
                <div class="div2">해당 음료를 하루에 몇 잔 마시나요?</div>
                <template v-if="cafeId && drinkTemp != ''">
                    <a class="minus" @click="clickMinus">
                        <font-awesome-icon :icon="['fas', 'minus']" style="color: #000000;" />
                    </a>
                    <div class="value">{{ drinkCount }}</div>

                    <a class="plus" @click="clickPlus">
                        <font-awesome-icon :icon="['fas', 'plus']" style="color: #000000;" />
                    </a>
                </template>

            </div>

            <div class="rectangle-4275">
                <div class="div3">당신의 하루 총 섭취량은?</div>

                <div v-if="caffeine >= 200.0" class="caffeine">
                    카페인 {{ caffeine }} mg
                </div>
                <div v-else class="caffeine" style="color:blue">
                    카페인 {{ caffeine }} mg
                </div>
                <div class="caffeine_ex">
                    식약청 카페인 최대 섭취 권장량
                    <br />
                    성인 기준 400mg 이하
                </div>

                <div v-if="sugar >= 25.0" class="sugar">
                    당 {{ sugar }} g
                </div>
                <div v-else class="sugar" style="color:blue">
                    당 {{ sugar }} g
                </div>
                <div class="sugar_ex">
                    식약청 당 일일 섭취 권장량
                    <br />
                    남성 37.5g / 여성 25.g
                </div>

            </div>

            <RouterLink to="/mainCaffeine" class="rectangle-4269">건너뛰기</RouterLink>
            <tempalte v-if="caffeine >= 200 && sugar >= 25.0">
                <RouterLink to="/survey/unhealth" class="rectangle-4270">결과보기</RouterLink>
            </tempalte>
            <template v-else-if="caffeine >= 200">
                <RouterLink to="/survey/caffeine" class="rectangle-4270">결과보기</RouterLink>
            </template>
            <template v-else-if="sugar >= 25.0">
                <RouterLink to="/survey/sugar" class="rectangle-4270">결과보기</RouterLink>
            </template>
            <template v-else>
                <RouterLink to="/survey/health" class="rectangle-4270">결과보기</RouterLink>
            </template>
            <!-- <div class="rectangle-4269">건너뛰기</div>
            <div class="rectangle-4270">결과보기</div> -->

            <div class="div8"></div>
            <div class="div9"></div>


        </div>
    </div>
</template>
<script setup>
import { ref, onMounted, watch } from 'vue'
import { useDrinksStore } from "@/stores/drinks";

const drinkStore = useDrinksStore();
const cafeId = ref(0);
const drinkId = ref(0);
const drinkCount = ref(1);
let drinkTemp = ref({})
const caffeine = ref(0);
const sugar = ref(0);

function selectCafe() {
    if (cafeId.value > 0) {
        drinkStore.researchCafeDrinks(cafeId.value);
    }
}

function selectDrink() {
    caffeine.value = drinkTemp.value.drinkCaffeine * drinkCount.value;
    sugar.value = drinkTemp.value.drinkSugar * drinkCount.value;
}

function clickMinus() {
    if (drinkTemp.value.drinkCaffeine == undefined || drinkTemp.value.drinkSugar == undefined) {
        window.alert("음료를 먼저 골라주세요");
        return;
    }
    if (drinkCount.value <= 0.0) {
        window.alert("0개 미만은 입력하실 수 없습니다.")
        return;
    }

    drinkCount.value -= 1;
    caffeine.value = drinkTemp.value.drinkCaffeine * drinkCount.value;
    sugar.value = drinkTemp.value.drinkSugar * drinkCount.value;
}

function clickPlus() {
    if (drinkTemp.value.drinkCaffeine == undefined || drinkTemp.value.drinkSugar == undefined) {
        window.alert("음료를 먼저 골라주세요");
        return;
    }

    if (drinkCount.value >= 9.0) {
        window.alert("10개 이상은 입력하실 수 없습니다.")
        return;
    }

    drinkCount.value += 1;
    caffeine.value = drinkTemp.value.drinkCaffeine * drinkCount.value;
    sugar.value = drinkTemp.value.drinkSugar * drinkCount.value;
}

watch(cafeId, (newValue, oldValue) => {
    drinkTemp.value = ''; // drinkTemp를 초기화합니다.
    drinkCount.value = 1;
    caffeine.value = 0;
    sugar.value = 0;
});


onMounted(() => {
    drinkStore.researchCafe();

})


</script>

<style scoped>
._1,
._1 * {
    box-sizing: border-box;
}

._1 {
    background: #ffffff;
    height: 1080px;
    position: relative;
    overflow: hidden;
}



.component-5 {
    width: 630px;
    height: 901px;
    position: relative;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}

.rectangle-4273 {
    background: #ffffff;
    border-radius: 45px;
    border-style: solid;
    border-color: #efefef;
    border-width: 1px;
    position: relative;
    right: 0%;
    left: 0%;
    width: 100%;
    bottom: 64.59%;
    top: 0%;
    height: 35.41%;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.rectangle-4274 {
    background: #ffffff;
    border-radius: 45px;
    border-style: solid;
    border-color: #efefef;
    border-width: 1px;
    position: relative;
    right: 0%;
    left: 0%;
    width: 100%;
    bottom: 40.07%;
    top: 5.07%;
    height: 19.87%;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.rectangle-4275 {
    background: #ffffff;
    border-radius: 45px;
    border-style: solid;
    border-color: #efefef;
    border-width: 1px;
    position: relative;
    right: 0%;
    left: 0%;
    width: 100%;
    bottom: 12.1%;
    top: 10.48%;
    height: 22.42%;
    box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.title01 {
    color: #562b1a;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 30px;
    line-height: 18px;
    font-weight: 700;
    position: relative;
    right: 21.75%;
    left: 10.68%;
    width: 80.57%;
    bottom: 93.34%;
    top: 17.66%;
    height: 2%;
}

.div2 {
    color: #562b1a;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 30px;
    line-height: 18px;
    font-weight: 700;
    position: relative;
    right: 15.71%;
    left: 10.76%;
    width: 80.52%;
    bottom: 52.61%;
    top: 25.39%;
    height: 2%;
}

.div3 {
    color: #562b1a;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 30px;
    line-height: 18px;
    font-weight: 700;
    position: relative;
    right: 25.08%;
    left: 23.08%;
    width: 57.84%;
    bottom: 28.3%;
    top: 15.7%;
    height: 2%;
}

.caffeine {
    color: #ff0000;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 25px;
    line-height: 18px;
    font-weight: 700;
    position: relative;
    right: 57.62%;
    left: 10.62%;
    width: 40.76%;
    bottom: 20.75%;
    top: 50.25%;
    height: 2%;
}

.sugar {
    color: #ff0000;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 25px;
    line-height: 18px;
    font-weight: 700;
    position: relative;
    right: 20.16%;
    left: 55.16%;
    width: 40.68%;
    bottom: 20.75%;
    top: 46.25%;
    height: 2%;
}

.value {
    color: #000000;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 30px;
    line-height: 18px;
    font-weight: 700;
    position: relative;
    right: 48.57%;
    left: 48.57%;
    width: 2.86%;
    bottom: 45.17%;
    top: 50.83%;
    height: 3%;
}

.cafe_name {
    color: #000000;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 25px;
    line-height: 18px;
    font-weight: 700;
    position: relative;
    right: 75.24%;
    left: 11.81%;
    width: 15.95%;
    bottom: 83.46%;
    top: 44.54%;
    height: 2%;
}

.div5 {
    color: #000000;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 25px;
    line-height: 18px;
    font-weight: 700;
    position: relative;
    right: 75.24%;
    left: 11.81%;
    width: 15.95%;
    bottom: 72.36%;
    top: 55.64%;
    height: 2%;
}

.rectangle-4269 {
    background: #b29f91;
    border-radius: 90px;
    position: relative;
    right: 68.1%;
    left: 8.1%;
    width: 23.81%;
    bottom: 0%;
    top: 15.34%;
    height: 6.66%;
    color: #000000;
    text-align: center;

    color: white;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 25px;
    line-height: 18px;
    font-weight: 700;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
}

.rectangle-4271 {
    background: #ffffff;
    border-radius: 90px;
    border-style: solid;
    border-color: #846046;
    border-width: 2px;
    position: relative;
    right: 14.92%;
    left: 31.05%;
    width: 56.03%;
    bottom: 81.13%;
    top: 40.21%;
    height: 12.66%;
}

.div6 {
    color: #c4c4c4;
    text-align: left;
    font-family: "DmSans-Regular", sans-serif;
    font-size: 20px;
    line-height: 18px;
    font-weight: 400;
    position: absolute;
    right: 38.73%;
    left: 34.13%;
    width: 30.14%;
    bottom: 83.46%;
    top: 14.54%;
    height: 2%;
    display: flex;
    align-items: center;
    justify-content: flex-start;
}

.rectangle-4272 {
    background: #ffffff;
    border-radius: 90px;
    border-style: solid;
    border-color: #846046;
    border-width: 2px;
    position: relative;
    height: auto;
    position: relative;
    right: 14.92%;
    left: 31.05%;
    width: 56.03%;
    bottom: 81.13%;
    top: 50.21%;
    height: 12.66%;
    overflow: visible;
}

.div7 {
    color: #c4c4c4;
    text-align: left;
    font-family: "DmSans-Regular", sans-serif;
    font-size: 20px;
    line-height: 18px;
    font-weight: 400;
    position: absolute;
    right: 38.73%;
    left: 34.13%;
    width: 30.14%;
    bottom: 72.36%;
    top: 25.64%;
    height: 2%;
    display: flex;
    align-items: center;
    justify-content: flex-start;
}

.div8 {
    color: #000000;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 25px;
    line-height: 18px;
    font-weight: 700;
    position: relative;
    right: 72.7%;
    left: 11.7%;
    width: 16.6%;
    bottom: 2.33%;
    top: 95.67%;
    height: 2%;
}

.rectangle-4270 {
    background: #846046;
    border-radius: 90px;
    position: relative;
    right: 11.27%;
    left: 64.92%;
    width: 23.81%;
    bottom: 0%;
    top: 9.00%;
    height: 6.66%;

    color: white;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 25px;
    line-height: 18px;
    font-weight: 700;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    z-index: 2;
}

.div9 {
    color: #ffffff;
    text-align: center;
    font-family: "DmSans-Bold", sans-serif;
    font-size: 25px;
    line-height: 18px;
    font-weight: 700;
    position: absolute;
    right: 15.87%;
    left: 68.52%;
    width: 16.6%;
    bottom: 2.33%;
    top: 95.67%;
    height: 2%;
}

.caffeine_ex {
    color: #000000;
    text-align: center;
    font-family: "DmSans-Regular", sans-serif;
    font-size: 13px;
    line-height: 15px;
    font-weight: 400;
    position: relative;
    right: 56.51%;
    left: 15.51%;
    width: 29.98%;
    bottom: 15.65%;
    top: 71.35%;
    height: 3%;
}

.sugar_ex {
    color: #000000;
    text-align: center;
    font-family: "DmSans-Regular", sans-serif;
    font-size: 13px;
    line-height: 15px;
    font-weight: 400;
    position: relative;
    right: 15.4%;
    left: 60.95%;
    width: 27.65%;
    bottom: 15.76%;
    top: 66.24%;
    height: 3%;
}

.minus {
    position: relative;
    right: 53.97%;
    left: 42.89%;
    width: 7.14%;
    bottom: 43.62%;
    top: 62.39%;
    height: 4.99%;
    object-fit: cover;
    cursor: pointer;
}

.image-56 {
    position: absolute;
    right: 16.98%;
    left: 75.87%;
    width: 7.14%;
    bottom: 81.91%;
    top: 13.1%;
    height: 4.99%;
    object-fit: cover;
}

.image-57 {
    position: absolute;
    right: 16.98%;
    left: 75.87%;
    width: 7.14%;
    bottom: 70.92%;
    top: 24.08%;
    height: 4.99%;
    object-fit: cover;
}

.plus {
    position: relative;
    right: 38.73%;
    left: 55.13%;
    width: 7.14%;
    bottom: 43.62%;
    top: 47.39%;
    height: 4.99%;
    object-fit: cover;
    cursor: pointer;
}
</style>
  