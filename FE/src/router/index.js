import calendarDetail from '@/components/calendar/calendarDetail.vue'
import HomeView from '@/views/HomeView.vue'
import CalendarView from '@/views/calendar/CalendarView.vue'
import ChatView from '@/views/chat/ChatView.vue'
import DrinkView from '@/views/drink/DrinkView.vue'
import MainView from '@/views/main/MainView.vue'
import MypageView from '@/views/mypage/MypageView.vue'
import SearchDetatilView from '@/views/search/SearchDetatilView.vue'
import SearchRankView from '@/views/search/SearchRankView.vue'
import DrinkDetailView from '@/views/search/DrinkDetailView.vue'
import InputView from '@/views/input/InputView.vue'
import UserUpdateView from '@/views/mypage/UserUpdateView.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/calendar',
      name: 'calendar',
      component: CalendarView,
    },
    {
      path: '/calendarDetail',
      name: 'calendarDetail',
      component: calendarDetail,
    },
    {
      path: '/chat',
      name: 'chat',
      component: ChatView,
    },
    {
      path: '/drink',
      name: 'drink',
      component: DrinkView,
    },
    {
      path: '/main',
      name: 'main',
      component: MainView,
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MypageView,
    },
    {
      path: '/mypage/editinfo',
      name: 'editinfo',
      component: UserUpdateView
    },
    {
      path: '/searchDetail',
      name: 'searchDetail',
      component: SearchDetatilView,
    },
    {
      path: '/searchRank',
      name: 'searchRank',
      component: SearchRankView,
    },
    {
      path: '/drinkDetail',
      name: 'drinkDetail',
      component: DrinkDetailView
    },
    {
      path: '/input',
      name: 'input',
      component: InputView
    },
  ],
})

export default router
