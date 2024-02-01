<script>
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from '@fullcalendar/interaction'

import { onMounted, ref } from "vue";
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
            events: [
                {
                    title: "asd",
                    start: new Date(),
                    display: "background",
                },
            ],
            height: "800px",
            datesSet: function (info) {
                // 월이 바뀔 때 마다
                const year = info.view.currentStart.getFullYear();
                let month = info.view.currentStart.getMonth() + 1; // Adding 1 to adjust for zero-based months
                month = month < 10 ? "0" + month.toString() : month.toString();
                const now = ref(year.toString() + month.toString());
                console.log(now);
                accumulateStore.month(now);
            },

        });

        const addIamge = function (info) {
            // 당은 6, 카페인은 75

            const date = info.date.getDate();
            const month = info.date.getMonth() + 1;
            for (let i = 0; i < accumulateStore.getAcuumulateMonth.length; i++) {
                if (date == accumulateStore.getAcuumulateMonth[i].recordaccumulateDate.split('-')[2]) {
                    const imgContainer = document.createElement('div');
                    // 여기에 src/components/icons/caffeine.png를 출력해야함
                    let dayCaffeine = accumulateStore.getAcuumulateMonth[i].accumulateCaffeine / 75.0;
                    let daySugar = accumulateStore.getAcuumulateMonth[i].accumulateCaffeine / 6.0;
                    console.log(date)
                    if (dayCaffeine > 0.0) {
                        const img = document.createElement('img');
                        img.src = 'src\components\icons\caffeine.png'; // 이미지 URL 설정
                        info.el.appendChild(img); // 일자 셀에 이미지 추가
                    }
                    if (daySugar > 0.0) {
                        const img = document.createElement('img');
                        img.src = 'src\components\icons\sugar.png'; // 이미지 URL 설정
                        info.el.appendChild(img); // 일자 셀에 이미지 추가
                    }

                    // Append the container element with images to the day cell
                    info.el.appendChild(imgContainer);
                }
            }

        }

        const updateCalendarOptions = async () => {
            console.log("hello");

            // if (diaryStore.getList.value) {
            //     calendarOptions.value.events = await diaryStore.getList.value.map(() => ({
            //         title: "blahblah",
            //         start: "2024-01-24",
            //     }));

            // }


            // calendarOptions.value.events = await diaryStore.getList.value.map(() => ({
            //     title: "blahblah",
            //     start: "2024-01-24",
            // }));


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
    font-size: 15px;
}

.fc .fc-button {
    font-size: 10px;
}
</style>