import HomeView from '@/views/HomeView.vue'
import CalendarView from '@/views/calendar/CalendarView.vue'
import ChatView from '@/views/chat/ChatView.vue'
import DrinkView from '@/views/drink/DrinkView.vue'
import MainView from '@/views/main/MainView.vue'
import MypageView from '@/views/mypage/MypageView.vue'
import SearchDetatilView from '@/views/search/SearchDetatilView.vue'
import SearchRank from '@/views/search/SearchRank.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/calendar',
      name: 'calendar',
      component: CalendarView
    },
    {
      path: '/chat',
      name: 'chat',
      component: ChatView
    },
    {
      path: '/drink',
      name: 'drink',
      component: DrinkView
    },
    {
      path: '/main',
      name: 'main',
      component: MainView
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MypageView
    },
    {
      path: '/searchDetail',
      name: 'searchDetail',
      component: SearchDetatilView
    },
    {
      path: '/searchRank',
      name: 'searchRank',
      component: SearchRank
    },
  ]
})

export default router
