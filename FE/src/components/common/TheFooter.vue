<template>
  <div>
    <v-footer class="text-center d-flex flex-column" app>
      <v-layout>
        <nav class="footer-nav">
          <RouterLink to="/"><font-awesome-icon :icon="['fas', 'house']" class="footer-icon" /></RouterLink>

          <RouterLink to="/calendar"><font-awesome-icon :icon="['fas', 'calendar']" class="footer-icon" />
          </RouterLink>

          <RouterLink to="/input"><font-awesome-icon :icon="['fas', 'plus']" class="footer-icon" /></RouterLink>

          <RouterLink to="/searchRank"><font-awesome-icon :icon="['fas', 'magnifying-glass']" class="footer-icon" />
          </RouterLink>

          <RouterLink to="/mypage"><font-awesome-icon :icon="['fas', 'user']" class="footer-icon" />
          </RouterLink>
        </nav>
      </v-layout>
    </v-footer>
    <!-- </v-app> -->
    <!-- home icon -->
    <!-- <font-awesome-icon :icon="['fas', 'house']" style="color: #b3b3b3;" /> -->

  </div>
</template>
<script setup>

</script>

<style>
.footer-bottom {
  bottom: 0;
  position: fixed;
  width: 100%;
}

.footer-icon {
  width: 75px;
  height: 75px;
  flex-shrink: 0;
  color: #768189;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.footer-icon:hover {
  color: #000000; /* Change to desired color */
}

.footer-nav {
  display: flex;
  justify-content: center;
  /* Center the icons horizontally */
  align-items: center;
  background-color: #fff;
  /* Footer 배경색 */
  border-top: 1px solid #dcdcdc;
  /* 윗면 테두리 */
  padding: 10px;
  /* Add padding as needed */
}


@media (max-width: 600px) {
  .footer-icon {
    /* Adjust the width and height for smaller screens */
    width: 50px;
    height: 50px;
  }
}

@media (min-width: 601px) and (max-width: 900px) {
  .footer-icon:nth-child(1) {
    /* Adjust margin for the first icon on medium-sized screens */
    margin-right: 30px;
  }

  .footer-icon:nth-child(2) {
    /* Adjust margin for the second icon on medium-sized screens */
    margin-left: 30px;
  }
}

@media (min-width: 901px) {
  .footer-icon:nth-child(1) {
    /* Adjust margin for the first icon on large screens */
    margin-right: 50px;
  }

  .footer-icon:nth-child(2) {
    /* Adjust margin for the second icon on large screens */
    margin-left: 50px;
  }
}
</style>
