import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

/* import the fontawesome core */
import { library } from "@fortawesome/fontawesome-svg-core";

/* import font awesome icon component */
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

/* import specific icons */
import { faUserSecret } from "@fortawesome/free-solid-svg-icons";

import { fas } from "@fortawesome/free-solid-svg-icons";
import { far } from "@fortawesome/free-regular-svg-icons";
import { fab } from "@fortawesome/free-brands-svg-icons";

import { userAccessToken, isFooter } from "@/stores/util";

import axios from "axios";

// axios의 헤더에 토큰을 설정하는 함수
const setTokenInAxiosHeader = () => {
  const token = localStorage.getItem("userAccessToken");
  if (token) {
    axios.defaults.headers.common["Authorization"] = token;
    userAccessToken.value = token;
  }
};

// 애플리케이션을 생성하고
// axios의 헤더에 토큰을 설정합니다.
setTokenInAxiosHeader();

const responseState = {
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

function stateActing(status) {
  if (status == responseState.CONTINUE) {
  } else if (status == responseState.PROCESSING) {
  } else if (status == responseState.SUCCESS) {
  } else if (status == responseState.CREATED) {
  } else if (status == responseState.ACCEPT) {
  } else if (status == responseState.BAD_REQUEST) {
    window.alert("잘못된 요청입니다.");
    router.push("/");
  } else if (status == responseState.UNAUTORIZED) {
    window.alert("로그인 인증 오류입니다.");
    localStorage.removeItem("userAccessToken");
    router.push("/");
  } else if (status == responseState.FORBIDDEN) {
    window.alert("권한이 없습니다");
    router.push("/");
  } else if (status == responseState.NOT_FOUND) {
    window.alert("NOT_FOUND");
    router.push("/");
  } else if (status == responseState.INTERENAL_SERVER_ERROR) {
    window.alert("서버 에러");
    router.push("/error");
  } else if (status == responseState.NOT_IMPLEMENT) {
    router.push("/error");
  }
  //   else {
  //     window.alert("Unknown Error");
  //     localStorage.removeItem("userAccessToken");
  //     router.push("/");
  //   }
}

axios.interceptors.request.use(
  (config) => {
    return config;
  },
  (error) => {
    console.log("Request error:", error.message);
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  (response) => {
    stateActing(response.status);
    return response;
  },
  (error) => {
    if (error.response) {
      stateActing(error.response.status);
    } else {
      stateActing("NETWORK_ERROR");
      window.alert("네트워크 오류 발생");
      isFooter.value= false;
      localStorage.removeItem("userAccessToken");
      router.push("/");
    }
    return Promise.reject(error);
  }
);

/* add icons to the library */
library.add(faUserSecret);
library.add(fas, far, fab);

const app = createApp(App);

window.Kakao.init(import.meta.env.VITE_KAKAO_API_KEY);

app.component("font-awesome-icon", FontAwesomeIcon);
app.use(createPinia());
app.use(router);
app.mount("#app");
