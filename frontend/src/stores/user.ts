import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  // 用户信息
  const userInfo = ref<{
    id: string
    name: string
    avatar: string
    email: string
  } | null>(null)

  // 登录状态
  const isLoggedIn = ref(false)

  // Token
  const token = ref<string | null>(localStorage.getItem('token'))

  // 设置用户信息
  function setUserInfo(info: typeof userInfo.value) {
    userInfo.value = info
    isLoggedIn.value = !!info
  }

  // 设置 Token
  function setToken(newToken: string) {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  // 清除用户信息
  function clearUserInfo() {
    userInfo.value = null
    isLoggedIn.value = false
    token.value = null
    localStorage.removeItem('token')
  }

  return {
    userInfo,
    isLoggedIn,
    token,
    setUserInfo,
    setToken,
    clearUserInfo
  }
})