<template>
  <div>
    <!-- 사이즈를 조절하기 위해서 다음과 같이 사용 : size="00"
    size: 2xs, xs, sm, none, lg, xl, 2xl -->
    
    <!-- home icon -->
    <font-awesome-icon :icon="['fas', 'house']" style="color: #b3b3b3;" />
    <font-awesome-icon :icon="['fas', 'house']" style="color: #000000;" />

    <!-- calendar icon -->
    <font-awesome-icon :icon="['fas', 'calendar']" />
    <font-awesome-icon :icon="['fas', 'calendar']" />

    <!-- search icon -->
    <font-awesome-icon :icon="['fas', 'magnifying-glass']" style="color: #b3b3b3;" />
    <font-awesome-icon :icon="['fas', 'magnifying-glass']" />

    <!-- user icon -->
    <font-awesome-icon :icon="['fas', 'user']" />
    <font-awesome-icon :icon="['fas', 'user']" />

  </div>
</template>

<script>
export default {
  setup () {
    

    return {}
  }
}
</script>

<style lang="scss" scoped>

</style>