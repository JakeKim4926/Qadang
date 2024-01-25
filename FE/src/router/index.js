import HomeViewVue from '@/views/HomeView.vue'
import CalendarViewVue from '@/views/calendar/CalendarView.vue'
import ChatViewVue from '@/views/chat/ChatView.vue'
import DrinkViewVue from '@/views/drink/DrinkView.vue'
import MainViewVue from '@/views/main/MainView.vue'
import MypageViewVue from '@/views/mypage/MypageView.vue'
import SearchDetatilViewVue from '@/views/search/SearchDetatilView.vue'
import SearchRankVue from '@/views/search/SearchRank.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeViewVue
    },
    {
      path: '/calendar',
      name: 'calendar',
      component: CalendarViewVue
    },
    {
      path: '/chat',
      name: 'chat',
      component: ChatViewVue
    },
    {
      path: '/drink',
      name: 'drink',
      component: DrinkViewVue
    },
    {
      path: '/main',
      name: 'main',
      component: MainViewVue
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MypageViewVue
    },
    {
      path: '/searchDetail',
      name: 'searchDetail',
      component: SearchDetatilViewVue
    },
    {
      path: '/searchRank',
      name: 'searchRank',
      component: SearchRankVue
    },
  ]
})

export default router
