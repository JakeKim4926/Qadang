import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { useRecordsStore } from './stores/records'

import App from './App.vue'
import router from './router'

/* import the fontawesome core */
import { library } from '@fortawesome/fontawesome-svg-core'

/* import font awesome icon component */
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

/* import specific icons */
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'

import {fas} from '@fortawesome/free-solid-svg-icons'
import {far} from '@fortawesome/free-regular-svg-icons'
import {fab} from '@fortawesome/free-brands-svg-icons'

import { userAccessToken, } from "@/stores/util"

import axios from 'axios';

// axios의 헤더에 토큰을 설정하는 함수
const setTokenInAxiosHeader = () => {
    const token = localStorage.getItem('userAccessToken');
    if (token) {
      axios.defaults.headers.common['Authorization'] = token;
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

axios.interceptors.request.use((config)=> {
    // console.log("[요청 발신]: ", config);
    return config;
}, (error) => {
    // console.log('[요청 실패]: ', error);
    return Promise.reject(error);
})

axios.interceptors.response.use((config)=> {
    if(config.status == responseState.CONTINUE) {

    } else if(config.status == responseState.PROCESSING) {

    } else if(config.status == responseState.SUCCESS) {

    } else if(config.status == responseState.CREATED) {

    } else if(config.status == responseState.ACCEPT) {

    } else if(config.status == responseState.BAD_REQUEST) {
        window.alert("잘못된 요청입니다.");
    } else if(config.status == responseState.UNAUTORIZED) {
        window.alert("클라이언트 인증 오류입니다.");
        localStorage.removeItem("userAccessToken");
        userAccessToken.value = null;
    } else if(config.status == responseState.FORBIDDEN) {
        window.alert("권한이 없습니다");
    } else if(config.status == responseState.NOT_FOUND) {
        window.alert("NOT_FOUND");
    } else if(config.status == responseState.INTERENAL_SERVER_ERROR) {
        window.alert("서버 에러");
    } else if(config.status == responseState.NOT_IMPLEMENT) {
        console.log("NOT_IMPLEMENT");
    }

    return config;
}, (error) => {
    if(config.status == responseState.BAD_REQUEST) {
        window.alert("잘못된 요청입니다.");
    } else if(config.status == responseState.UNAUTORIZED) {
        localStorage.removeItem("userAccessToken");
        userAccessToken.value = null;
        window.alert("클라이언트 인증 오류입니다.");
    } else if(config.status == responseState.FORBIDDEN) {
        window.alert("권한이 없습니다");
        router.push('/');
    } else if(config.status == responseState.NOT_FOUND) {
        window.alert("NOT_FOUND");
    } else if(config.status == responseState.INTERENAL_SERVER_ERROR) {
        window.alert("서버 에러");
    } else if(config.status == responseState.NOT_IMPLEMENT) {
        window.alert("NOT_IMPLEMENT");
    }

    return Promise.reject(error);
})

/* add icons to the library */
library.add(faUserSecret)
library.add(fas, far, fab)

const app = createApp(App)

window.Kakao.init(import.meta.env.VITE_KAKAO_API_KEY)

app.component('font-awesome-icon', FontAwesomeIcon)
app.use(createPinia())
app.use(router)
app.mount("#app");
