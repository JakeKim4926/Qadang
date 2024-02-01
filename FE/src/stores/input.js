import { defineStore } from "pinia";
import { ref } from "vue";

export const useInputStore = defineStore("input", () => {
  const isInputModal = ref(false)
  const inInputNothingModal = ref(false)

  // input modal
  const openInputModal = () => {
    isInputModal.value = true
  }

  const closeInputModal = function() {
    isInputModal.value = false
  }

  // inputNothing modal
  const openInputNothingModal = () => {
    inInputNothingModal.value = true
  }

  const closeInputNothingModal = () => {
    inInputNothingModal.value = false
  }

  // move modal
  const goInputModal = () => {
    closeInputNothingModal()
    openInputModal()
  }

  const goInputNothingModal = () => {
  closeInputModal()
  openInputNothingModal()
  }
  
  return {
    isInputModal,
    inInputNothingModal,
    openInputModal,
    closeInputModal,
    openInputNothingModal,
    closeInputNothingModal,
    goInputModal,
    goInputNothingModal,
  }
})