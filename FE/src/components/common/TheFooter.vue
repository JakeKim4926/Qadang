<template>
  <footer class="footer">
    <div class="footer-content">
      <InputView v-if="isInputModal" />
      <InputNothingView v-if="isInputNothingModal" />
      <div class="empty-space"></div> <!-- 왼쪽 끝의 빈 공간 -->
      <RouterLink to="/" class="footer-link">
        <div class="footer-item">
          <font-awesome-icon :icon="['fas', 'home']" class="footer-icon" />
        </div>
      </RouterLink>
      <RouterLink to="/calendar" class="footer-link">
        <div class="footer-item">
          <font-awesome-icon :icon="['fas', 'calendar-alt']" class="footer-icon" />
        </div>
      </RouterLink>
      <button class="footer-link" @click="openInputModal">
        <div class="footer-item">
          <font-awesome-icon :icon="['fas', 'plus']" class="footer-icon plus-icon" />
        </div>
      </button>
      <RouterLink to="/searchRank" class="footer-link">
        <div class="footer-item">
          <font-awesome-icon :icon="['fas', 'search']" class="footer-icon" />
        </div>
      </RouterLink>
      <RouterLink to="/mypage" class="footer-link">
        <div class="footer-item">
          <font-awesome-icon :icon="['fas', 'user']" class="footer-icon" />
        </div>
      </RouterLink>
      <div class="empty-space"></div> <!-- 오른쪽 끝의 빈 공간 -->
    </div>
  </footer>
</template>

<script setup>
  import InputView from '@/components/input/InputView.vue';
  import InputNothingView from '@/components/input/InputNothingView.vue';

  import { isInputModal } from '@/stores/input';
  import { isInputNothingModal } from '@/stores/input';

</script>

<style>
.footer {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background-color: #f0f0f0;
  box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.1);
  border-top: 1px solid #ddd;
  z-index: 95;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
}

.footer-link {
  text-decoration: none;
  color: #666;
}

.footer-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.footer-icon {
  font-size: 24px;
  color: #666; /* 기본 색상 */
  transition: color 0.3s ease; /* 색상 전환 애니메이션 */
}

.plus-icon {
  font-size: 32px;
  color: #666; /* 플러스 아이콘의 색상 */
}

.empty-space {
  width: calc(25% - 24px); /* 4/1 공간을 계산하여 설정 */
}

.footer-icon:hover {
  color: #000; /* 마우스 호버 시 검정색으로 변경 */
}
</style>
