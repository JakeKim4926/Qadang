<template>
    <div class="error-container">
        <p class="error-message">서버 에러입니다. 잠시 후 다시 시도해주세요.</p>
        <router-link :to="{ name: 'mainCaffeine' }" class="back-link">
            <button class="back-button">돌아가기</button>
        </router-link>
    </div>
</template>
  
<script>
import MainCaffeineView from "@/views/main/MainCaffeineView.vue";

</script>
  
<style scoped lang="scss">
.error-container {
    text-align: center;
    margin-top: 50px;
}

.error-message {
    font-size: 80px;
    color: red;
}

.back-link {
    text-decoration: none;
}

.back-button {
    padding: 10px 20px;
    background: #B29F91;
    color: #EFEFEF;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 50px;
    width: 300px;
    height: 150px;
    transition: background-color 0.3s ease;
}

.back-button:hover {
    background-color: #0056b3;
}
</style>
  