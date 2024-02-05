import { ref, computed } from "vue";

export const responseState = {
  CONTINUE: 100,
  PROCESSING: 102,
  SUCCESS: 200,
  CREATED: 201,
  ACCEPT: 202,
  BAD_REQUEST: 400,
  UNAUTORIZED: 401,
  FORBIDDEN: 402,
  NOT_FOUND: 404,
  INTERENAL_SERVER_ERROR: 500,
  NOT_IMPLEMENT: 501,
};

export const isCalendarModal = ref(false);

export const isInputModal = ref(false)
export const isInputNothingModal = ref(false)

export const isUpdateModal = ref(false)
export const isUpdateNothingModal = ref(false)

export const isDetailModal = ref(false)
export const isCompareModal = ref(false)

export const tempRecord = ref({})