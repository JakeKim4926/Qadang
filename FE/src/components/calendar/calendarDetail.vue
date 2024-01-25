<template>
    <div>
        <h2>are you here</h2>
        <h2>Date : {{ getRecordDate }}</h2>
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

<style scoped></style>