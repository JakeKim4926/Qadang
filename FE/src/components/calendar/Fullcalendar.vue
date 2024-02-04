<script>
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from '@fullcalendar/interaction'

import { onMounted, ref, onBeforeMount } from "vue";
import axios from "axios";
import router from "@/router";
import { useRecordsStore } from "@/stores/records";
import { useAccumulateStore } from "@/stores/accumulate";
import { isCalendarModal } from "@/stores/util"

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
                console.log(result);
                recordStore.recordDay = result;
                recordStore.recordDate = days;

                isCalendarModal.value = true;
            },
            dayCellContent: (date) => {
                const year = date.date.getFullYear();
                let month = date.date.getMonth() + 1; // Adding 1 to adjust for zero-based months
                month = month < 10 ? "0" + month.toString() : month.toString();
                let day = date.date.getDate();
                day = day < 10 ? "0" + day.toString() : day.toString();
                const currentDate = year.toString() + '-' + month.toString() + '-' + day.toString();

                const sample = JSON.parse(sessionStorage.getItem('calendarMonth').trim());
                const dataForDate = sample.find(item => item.accumulateDate === currentDate);
                if (dataForDate != undefined) {
                    const caffeine = (dataForDate.accumulateCaffeine / 75.0).toFixed(1);
                    const sugar = (dataForDate.accumulateSugar / 6.0).toFixed(1);

                    return {
                        html: `
                        <div class="day-number-text">${date.dayNumberText}</div>
                        <div style="text-align: center;
                                justify-content: center;">
                            <h5>
                            <img src="src/components/icons/caffeine.png" alt="no" style="width:20%; heigth:10%" />
                            x ${caffeine}
                            </h5>
                            <h5>
                            <img src="src/components/icons/sugar.png" alt="no" style="width:20%; heigth:10%" />
                            x ${sugar}
                            </h5>
                        </div>
                    `,
                    };
                }
                return {
                    html: `
                    <div class="day-number-text">${date.dayNumberText}</div>
                    `,
                };
            },

            events: [
                {
                    title: "asd",
                    start: new Date(),
                    display: "background",
                },
            ],
            height: "800px",
            datesSet: async function (info) {
                // 월이 바뀔 때 마다
                const year = info.view.currentStart.getFullYear();
                let month = info.view.currentStart.getMonth() + 1; // Adding 1 to adjust for zero-based months
                month = month < 10 ? "0" + month.toString() : month.toString();
                const now = ref(year.toString() + month.toString());
                // console.log(now);
                await accumulateStore.month(now);

            },

        });

        const updateCalendarOptions = async () => {
            console.log("hello");
        };

        updateCalendarOptions();

        return {
            calendarOptions,
            updateCalendarOptions,
        };
    },

};
</script>

<template>
    <FullCalendar :options="calendarOptions" />
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
</style>