import { defineStore } from 'pinia'
import { login, getUserInfo } from '@/api/auth'
import router from '@/router'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: null,
    menus: []
  }),
  actions: {
    async loginAction(username, password) {
      const res = await login({ username, password })
      this.token = res.data.token
      localStorage.setItem('token', res.data.token)
      await this.getInfo()
    },
    async getInfo() {
      const res = await getUserInfo()
      this.userInfo = res.data.user
      this.menus = res.data.menus || []
    },
    logout() {
      this.token = ''
      this.userInfo = null
      this.menus = []
      localStorage.removeItem('token')
      router.push('/login')
    }
  }
})