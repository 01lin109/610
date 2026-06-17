import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store/user'

const request = axios.create({
  baseURL: '/api',
  timeout: 15000
})

request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = 'Bearer ' + token
  return config
})

request.interceptors.response.use(
  res => {
    const data = res.data
    if (data.code === 200) return data
    if (data.code === 401) {
      const userStore = useUserStore()
      userStore.logout()
      return Promise.reject(data)
    }
    ElMessage.error(data.message || '请求失败')
    return Promise.reject(data)
  },
  err => {
    ElMessage.error('网络异常')
    return Promise.reject(err)
  }
)
export default request
