<template>
    <div class="calendar_background">
        <div class="calendar_day_title">
            {{ getRecordDate }}
        </div>
        <div class="calendar_all_material_font">
            총 섭취한 카페인량
        </div>
        <div class="calendar_drink_value_font">
            150.3 mg
        </div>
        <div class="calendar_all_material_font">
            총 섭취한 당량
        </div>
        <div class="calendar_drink_value_font">
            0.0 g
        </div>
        <hr class="calendar_drink_line_76">
        <div class="calendar_drink_rectangle_4333">
            <div>No.oo1</div>
        </div>

    </div>
</template>

<script setup>
import { useRecordsStore } from '@/stores/records';
import { ref, onMounted, computed } from 'vue';

const recordStore = useRecordsStore();
const recordDate = ref('');
const getRecordDate = computed(() => recordDate);

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
})

</script>

<style scoped>
/* Rectangle 4329 */
.calendar_background {
    box-sizing: border-box;
    position: absolute;
    width: 579px;
    height: 613px;
    background: #FFFFFF;
    border: 1px solid #D9D9D9;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    border-radius: 20px;
}

/* Rectangle 4330 */
.calendar_drink_rectangle {
    box-sizing: border-box;
    position: absolute;
    width: 504px;
    height: 220px;
    background: #D9D9D9;
    background-blend-mode: screen;
    border: 1px solid #D9D9D9;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    border-radius: 30px;
}

/* 샷 */
.calendar_drink_title_font {
    position: absolute;
    width: 67px;
    height: 18px;
    line-height: 18px;
    display: flex;
    align-items: center;
    color: #000000;
}

/* 스타벅스 */
.calendar_cafe_title_font {
    position: absolute;
    width: 68px;
    height: 16px;
    line-height: 18px;
    display: flex;
    align-items: center;
    color: #000000;
}

/* No.001 */
.calendar_drink_No {
    position: absolute;
    width: 68px;
    height: 16px;
    display: flex;
    align-items: center;
    color: #000000;
}

/* Line 74 */
.calendar_drink_line {
    position: absolute;
    width: 336px;
    height: 0px;
    border: 1px solid #000000;
}

/* 150.3 mg */
.calendar_drink_value_font {
    position: absolute;
    width: 67px;
    height: 16px;
    font-family: 'DM Sans';
    font-style: normal;
    font-weight: 400;
    font-size: 15px;
    line-height: 18px;
    display: flex;
    align-items: center;
    color: #000000;
}

/* 총 섭취한 카페인량 150.3 mg */
.calendar_all_material_font {
    position: absolute;
    width: 152px;
    height: 16px;
    line-height: 18px;
    display: flex;
    align-items: center;
    text-align: center;
    color: #000000;
}

/* 17 금요일 */
.calendar_day_title {
    position: absolute;
    width: 152px;
    height: 16px;
    font-family: 'DM Sans';
    font-style: normal;
    font-weight: 700;
    font-size: 25px;
    line-height: 18px;
    display: flex;
    align-items: center;
    text-align: center;
    color: #000000;
}

/* 총 섭취한 당량 0.0 g */
.calendar_drink_sugar_value {
    position: absolute;
    width: 152px;
    height: 16px;
    /* font-family: 'DM Sans';
    font-style: normal;
    font-weight: 700;
    font-size: 15px; */
    line-height: 18px;
    display: flex;
    align-items: center;
    text-align: center;
    color: #000000;
}

/* Line 76 */
.calendar_drink_line_76 {
    position: absolute;
    width: 502px;
    height: 0px;
    border: 2px solid #846046;
    transform: rotate(-0.11deg);
}

/* 아메리카노 Tall */
.calendar_drink_americano {
    position: absolute;
    width: 185px;
    height: 40px;
    font-family: 'DM Sans';
    font-style: normal;
    font-weight: 700;
    font-size: 15px;
    line-height: 18px;
    display: flex;
    align-items: center;
    color: #000000;
}

/* Rectangle 4331 */
.calendar_rect_edit {
    position: absolute;
    width: 40px;
    height: 26px;
    background: #8A6D58;
    border-radius: 90px;
}

/* 수정 */
.calendar_text_edit {
    position: absolute;
    width: 29px;
    height: 17px;
    font-family: 'DM Sans';
    font-style: normal;
    font-weight: 700;
    font-size: 11px;
    line-height: 18px;
    text-align: center;
    color: #FFFFFF;
}

/* Rectangle 4332 */
.calendar_rect_delete {
    position: absolute;
    width: 40px;
    height: 26px;
    background: #B29F91;
    border-radius: 90px;
}

/* 삭제 */
.calendar_text_delete {
    position: absolute;
    width: 83px;
    height: 17px;
    font-family: 'DM Sans';
    font-style: normal;
    font-weight: 700;
    font-size: 11px;
    line-height: 18px;
    text-align: center;
    color: #000000;
}

/* Rectangle 4333 */
.calendar_drink_rectangle_4333 {
    box-sizing: border-box;
    position: absolute;
    width: 504px;
    height: 220px;
    background: #FFFFFF;
    background-blend-mode: screen;
    border: 1px solid #D9D9D9;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
    border-radius: 30px;
}

/* Group 8771 */
.calendar_group_8771 {
    position: absolute;
    width: 77px;
    height: 77px;
    filter: drop-shadow(0px 4px 4px rgba(0, 0, 0, 0.25));
}

/* Ellipse 2 */
.calendar_ellipse_2 {
    position: absolute;
    width: 77px;
    height: 77px;
    background: #EFEFEF;
}

/* Group 1 */
.calendar_group_1 {
    position: absolute;
    width: 31.37px;
    height: 28.23px;
}

/* Line 2 */
.calendar_line_2 {
    position: absolute;
    width: 28.23px;
    height: 0px;

}
</style>