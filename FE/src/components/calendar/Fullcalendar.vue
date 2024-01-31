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
                // router.push({
                //     name: 'calendarDetail',
                //     // params: { id : diaryStore.getList.value[i].diary_id }
                // });
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
        });

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