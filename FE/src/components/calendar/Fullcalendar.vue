<script>
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from '@fullcalendar/interaction'

import { onMounted, ref } from "vue";
import axios from "axios";
import router from "@/router";
import { useRecordsStore } from "@/stores/records";
import { useAccumulateStore } from "@/stores/accumulate";

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
                 
                const days =  date.getDate() + ' ' + recordStore.days[date.getDay()] + '요일';
                // const day = date.getMonth()+1 + '월 ' + date.getDate() + '일'; 
                recordStore.recordDate = days;
                router.push({
                    name: 'calendarDetail',
                    // params: { id : diaryStore.getList.value[i].diary_id }
                });
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
           console.log()
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