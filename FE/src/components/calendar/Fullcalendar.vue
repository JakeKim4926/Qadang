<script>
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from '@fullcalendar/interaction'

import { onMounted, ref, watch } from "vue";
import axios from "axios";
import router from "@/router";
import { useRecordsStore } from "@/stores/records";
import { useAccumulateStore } from "@/stores/accumulate";
import { isCalendarModal, isUpdateInputModal, isUpdateNothingModal, isInputModal, isInputNothingModal } from "@/stores/util"

export const callendarMonth = ref([]);
export const calendarKey = ref(0);

export default {
    components: {
        FullCalendar, // make the <FullCalendar> tag available
    },
    setup() {
        const recordStore = useRecordsStore();
        const accumulateStore = useAccumulateStore();


        const calendarOptions = ref({
            plugins: [dayGridPlugin, interactionPlugin,],
            initialView: "dayGridMonth",
            weekends: true,
            selectable: true,
            navLinks: true,
            showNonCurrentDates: false,
            navLinkDayClick: function (date, jsEvent) {
                // 캘린더에서 날짜를 클릭했을 때

                const days = date.getDate() + ' ' + recordStore.days[date.getDay()] + '요일';
                const year = (date.getYear() + 1900).toString();
                let month = date.getMonth() + 1;
                let day = date.getDate();
                month = month < 10 ? "0" + month.toString() : month.toString();
                day = day < 10 ? "0" + day.toString() : day.toString();
                const result = year + month + day;
                recordStore.recordDay = result;
                recordStore.recordDate = days;

                isCalendarModal.value = true;
            },
            dayCellContent: dayCellContentFunction,

            events: [
                {
                    title: "asd",
                    start: new Date(),
                    display: "background",
                },
            ],
            height: "600px",
        });

        function dayCellContentFunction(date) {

            const year = date.date.getFullYear();
            let month = date.date.getMonth() + 1; // Adding 1 to adjust for zero-based months
            month = month < 10 ? "0" + month.toString() : month.toString();
            let day = date.date.getDate();
            day = day < 10 ? "0" + day.toString() : day.toString();
            const currentDate = year.toString() + '-' + month.toString() + '-' + day.toString();
            const sample = JSON.parse(sessionStorage.getItem('calendarMonth'));
            if (sample != undefined) {
                let dataForDate = sample.find(item => item.accumulateDate === currentDate);
                if (dataForDate != undefined) {
                    const caffeine = (dataForDate.accumulateCaffeine / 75.0).toFixed(1);
                    const sugar = (dataForDate.accumulateSugar / 6.0).toFixed(1);

                    let caffeineHTML = `
                            <h5>
                            <img src="/caffeine.png" alt="no" class="icons" /> x ${caffeine}
                            
                            </h5>
                    `
                    let sugarHTML = `
                            <h5>
                            <img src="/sugar.png" alt="no" class="icons" />
                            x ${sugar}
                            </h5>
                    `

                    if (caffeine == 0.0)
                        caffeineHTML = '';
                    if (sugar == 0.0)
                        sugarHTML = ''

                    return {
                        html: `
                        <div class="circle"></div>
                        <div class="day-number-exist" style="text-align: right; font-weight:bold; z-index:1; right:3%"">${date.dayNumberText}</div>
                        <div style="text-align: center;
                                justify-content: center;">` + caffeineHTML + sugarHTML +

                            `</div>
                    `,
                    };
                }
            }
            return {
                html: `
                    <div class="day-number-text">${date.dayNumberText}</div>
                    `,
            };
        }
        const updateCalendarOptions = async () => {

            const handleDateSet = async (arg) => {
                const accumulateStore = useAccumulateStore();

                const year = arg.view.currentStart.getFullYear();
                let month = arg.view.currentStart.getMonth() + 1;
                month = month < 10 ? "0" + month.toString() : month.toString();
                const now = ref(year.toString() + month.toString());
                accumulateStore.month(now);
                const asd = { date: arg.view.currentStart };
                dayCellContentFunction(asd);
            };
            dateSet: handleDateSet;
            dayCellContent: dayCellContentFunction;

        };
        const calendarKey = ref(0); // calendarKey를 선언
        onMounted(async () => {
            const handleDateSet = async (arg) => {
                const accumulateStore = useAccumulateStore();

                const year = arg.view.currentStart.getFullYear();
                let month = arg.view.currentStart.getMonth() + 1;
                month = month < 10 ? "0" + month.toString() : month.toString();
                const now = ref(year.toString() + month.toString());
                accumulateStore.month(now);
                const asd = { date: arg.view.currentStart };
                dayCellContentFunction(asd);
            };
            calendarOptions.value.datesSet = handleDateSet;
            await updateCalendarOptions();

            await rerenderFullCalendar();
        });

        const rerenderFullCalendar = async () => {
            calendarKey.value += 1;
        };

        updateCalendarOptions();

        // FullCalendar의 dateSet 이벤트 핸들러
        const handleDateSet = async (arg) => {
            const accumulateStore = useAccumulateStore();

            const year = arg.view.currentStart.getFullYear();
            let month = arg.view.currentStart.getMonth() + 1; // Adding 1 to adjust for zero-based months
            month = month < 10 ? "0" + month.toString() : month.toString();
            const now = ref(year.toString() + month.toString());
            accumulateStore.month(now);
            const asd = { date: arg.view.currentStart };
            dayCellContentFunction(asd);
        };


        return {
            calendarOptions,
            updateCalendarOptions,
            dayCellContentFunction,
            handleDateSet,
            calendarKey,
        };

    },

};




</script>

<template>
    <FullCalendar :options="calendarOptions" @dateSet="handleDateSet" :key="calendarKey" />
</template>

<style>
/* November 2023 title부분 */
.fc .fc-toolbar-title {
    font-size: 25px;
}

.fc .fc-button {
    font-size: 15px;
}

.fc-daygrid-day {
    width: 10px;
    /* 너비 조정 */
    height: 10px
        /* 높이 조정 */

}

.fc-daygrid-day-bottom {
    width: 10px;
    height: 0px;
}

.fc-daygrid-day-frame {
    height: 150px;
}

.day-number-container {
    position: relative;
    display: inline-block;
    color: white;
    /* Set the color to white */
}

.day-number-exist {
    font-weight: bold;
    color: white;
    /* Set the color to white */
    position: relative;
    /* Add position relative */
    z-index: 1;
    /* Set a higher z-index */
}

.circle {
    position: absolute;
    top: 1%;
    right: 3%;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background-color: rgb(68, 74, 104);
    z-index: 0;
}

.icons {
    width: 15%;
    height: 10%;
    max-width: 120px;
}
</style>