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

export const isUpdateInputModal = ref(false)
export const isUpdateNothingModal = ref(false)

export const isRecommedModal = ref(false)
export const recommedDrinkInfo = ref({})

export const isDetailModal = ref(false)
export const isCompareModal = ref(false)

export const tempRecord = ref({})

export const userAccessToken = ref(null);

export const isRankModal = ref(false)

export const isFooter = ref(false);
export const updateCalendar = ref(true);

export const isSocketConnected = ref(false);