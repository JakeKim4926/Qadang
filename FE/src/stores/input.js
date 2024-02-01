import { defineStore } from "pinia";
import { isInputModal, isInputNothingModal } from '@/stores/util'

export const useInputStore = defineStore("input", () => {
  // input modal
  const openInputModal = () => {
    isInputModal.value = true
  }

  const closeInputModal = () => {
    isInputModal.value = false
  }

  // inputNothing modal
  const openInputNothingModal = () => {
    isInputNothingModal.value = true

  }

  const closeInputNothingModal = () => {
    isInputNothingModal.value = false
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
    openInputModal,
    closeInputModal,
    openInputNothingModal,
    closeInputNothingModal,
    goInputModal,
    goInputNothingModal,
  }
})