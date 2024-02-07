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

import axios from 'axios';

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

// axios.interceptors.request.use((config)=> {
//     console.log("[요청 발신]: ", config);
    
//     return config;
// }, (error) => {
//     console.log('[요청 실패]: ', error);
//     return Promise.reject(error);
// })

axios.interceptors.response.use((config)=> {
    console.log("[응답 수신] : ", config);
    // console.log("[응답 헤더] : ", config.headers);
    // console.log("[응답 상태] : ", config.status);

    if(config.status == responseState.CONTINUE) {

    } else if(config.status == responseState.PROCESSING) {

    } else if(config.status == responseState.SUCCESS) {

    } else if(config.status == responseState.CREATED) {

    } else if(config.status == responseState.ACCEPT) {

    } else if(config.status == responseState.BAD_REQUEST) {
        window.alert("잘못된 요청입니다.");
    } else if(config.status == responseState.UNAUTORIZED) {
        window.alert("클라이언트 인증 오류입니다.");
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
    console.log('[오류 수신]: ', error);

    if(config.status == responseState.BAD_REQUEST) {
        window.alert("잘못된 요청입니다.");
    } else if(config.status == responseState.UNAUTORIZED) {
        window.alert("클라이언트 인증 오류입니다.");
    } else if(config.status == responseState.FORBIDDEN) {
        window.alert("권한이 없습니다");
        router.push('/');
    } else if(config.status == responseState.NOT_FOUND) {
        window.alert("NOT_FOUND");
    } else if(config.status == responseState.INTERENAL_SERVER_ERROR) {
        window.alert("서버 에러");
    } else if(config.status == responseState.NOT_IMPLEMENT) {
        console.log("NOT_IMPLEMENT");
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
