import calendarDetail from "@/components/calendar/calendarDetail.vue";
import HomeView from "@/views/HomeView.vue";
import CalendarView from "@/views/calendar/CalendarView.vue";
import ChatView from "@/views/chat/ChatView.vue";
import DrinkView from "@/views/drink/DrinkView.vue";
import MainCaffeineView from "@/views/main/MainCaffeineView.vue";
import MainSugarView from "@/views/main/MainSugarView.vue";
import MypageView from "@/views/mypage/MypageView.vue";
import SearchDetatilView from "@/views/search/SearchDetatilView.vue";
import SearchRankView from "@/views/search/SearchRankView.vue";
import SearchTopView from "@/views/search/SearchTopView.vue";
import drinkDetailView from "@/views/search/drinkDetailView.vue";
import CompareDrinkView from "@/views/search/CompareDrinkView.vue";
import SurveyView from "@/views/survey/SurveyView.vue";
import SurveyCaffeineView from "@/views/survey/SurveyCaffeineView.vue";
import SurveySugarView from "@/views/survey/SurveySugarView.vue";
import SurveyHealthView from "@/views/survey/SurveyHealthView.vue";
import SurveyUnHealthView from "@/views/survey/SurveyUnHealthView.vue";

import UserUpdateView from "@/components/user/UserUpdateView.vue";
import { createRouter, createWebHistory } from "vue-router";
import { userAccessToken } from "@/stores/util";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/calendar",
      name: "calendar",
      component: CalendarView,
    },
    {
      path: "/calendarDetail",
      name: "calendarDetail",
      component: calendarDetail,
    },
    {
      path: "/chat",
      name: "chat",
      component: ChatView,
    },
    {
      path: "/drink",
      name: "drink",
      component: DrinkView,
    },
    {
      path: "/mainCaffeine",
      name: "mainCaffeine",
      component: MainCaffeineView,
    },
    {
      path: "/mainSugar",
      name: "mainSugar",
      component: MainSugarView,
    },
    {
      path: "/mypage",
      name: "mypage",
      component: MypageView,
    },
    {
      path: "/userupdate",
      name: "userupdate",
      component: UserUpdateView,
    },
    {
      path: "/searchDetail",
      name: "searchDetail",
      component: SearchDetatilView,
    },
    {
      path: "/searchRank",
      name: "searchRank",
      component: SearchRankView,
    },
    {
      path: "/searchTop",
      name: "searchTop",
      component: SearchTopView,
    },

    {
      path: "/drinkDetail/:drinkId",
      name: "drinkDetail",
      component: drinkDetailView,
    },
    {
      path: "/compareDrink",
      name: "compareDrink",
      component: CompareDrinkView,
    },
    {
      path: "/survey",
      name: "survey",
      component: SurveyView,
    },
    {
      path: "/survey/caffeine",
      name: "surveyCaffeine",
      component: SurveyCaffeineView,
    },
    {
      path: "/survey/sugar",
      name: "surveySugar",
      component: SurveySugarView,
    },
    {
      path: "/survey/health",
      name: "surveyHealth",
      component: SurveyHealthView,
    },
    {
      path: "/survey/unhealth",
      name: "surveyUnhealth",
      component: SurveyUnHealthView,
    },
    {
      path: "/api/kakao-login",
      name: "kakaoLogin",
      component: MainCaffeineView,
    },
  ],
});

// == navigationguard
// router.beforeEach((to, from, next) => {
//   if (userAccessToken.value === null) {
//     if (to.name !== "home" ) {
//       next({ name: "home" });
//     } else if (to.name == "kakaoLogin" ) {
//       next({ name: "kakaoLogin" });
//     } else {
//       next();
//     }


//   } else {
//     if (to.name === "home") {
//       window.alert("로그인이 필요합니다");
//       next(false); // 접근 금지
//     } else {
//       next(); // 허용
//     }
//   }
// });

export default router;
