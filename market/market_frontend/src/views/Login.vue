<template>
  <div class="login-container">
    <div class="login-bg">
      <div class="bg-shape bg-shape-1"></div>
      <div class="bg-shape bg-shape-2"></div>
      <div class="bg-shape bg-shape-3"></div>
    </div>
    <div class="login-card">
      <div class="login-header">
        <div class="login-logo">
          <span class="logo-icon">M</span>
        </div>
        <h2 class="login-title">Market 商城后台管理</h2>
        <p class="login-desc">欢迎回来，请登录您的账户</p>
      </div>
      <el-form :model="form" :rules="rules" ref="formRef" class="login-form">
        <el-form-item prop="username">
          <el-input
            v-model="form.username"
            placeholder="用户名"
            :prefix-icon="User"
            size="large"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            placeholder="密码"
            type="password"
            :prefix-icon="Lock"
            size="large"
            @keyup.enter="handleLogin"
            show-password
          />
        </el-form-item>
        <el-button
          type="primary"
          size="large"
          :loading="loading"
          class="login-btn"
          @click="handleLogin"
        >
          {{ loading ? '登录中...' : '登 录' }}
        </el-button>
      </el-form>
      <div class="login-footer">
        <span>Market 管理系统 v1.0</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref(null)
const loading = ref(false)
const form = reactive({ username: 'admin', password: '123456' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}
const handleLogin = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  loading.value = true
  try {
    await userStore.loginAction(form.username, form.password)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } catch (e) {
    ElMessage.error(e?.message || '登录失败')
  } finally { loading.value = false }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #F4F5F7;
  position: relative;
  overflow: hidden;
}

/* ---- Animated Background Shapes ---- */
.login-bg {
  position: absolute;
  inset: 0;
  pointer-events: none;
}

.bg-shape {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.35;
}

.bg-shape-1 {
  width: 500px;
  height: 500px;
  background: radial-gradient(circle, #FF5500, #FF8C4A);
  top: -150px;
  right: -100px;
  animation: float 8s ease-in-out infinite;
}

.bg-shape-2 {
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, #E2231A, #FF5500);
  bottom: -120px;
  left: -80px;
  animation: float 10s ease-in-out infinite reverse;
}

.bg-shape-3 {
  width: 300px;
  height: 300px;
  background: radial-gradient(circle, #FF8C4A, #FFAA66);
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation: float 12s ease-in-out infinite;
}

@keyframes float {
  0%, 100% { transform: translate(0, 0) scale(1); }
  33% { transform: translate(30px, -30px) scale(1.05); }
  66% { transform: translate(-20px, 20px) scale(0.95); }
}

/* ---- Login Card ---- */
.login-card {
  width: 420px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border-radius: 16px;
  padding: 40px;
  box-shadow:
    0 4px 24px rgba(26, 26, 46, 0.06),
    0 1px 4px rgba(26, 26, 46, 0.04);
  position: relative;
  z-index: 1;
  border: 1px solid rgba(255, 255, 255, 0.5);
}

/* ---- Header ---- */
.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.login-logo {
  margin-bottom: 16px;
}

.logo-icon {
  display: inline-flex;
  width: 56px;
  height: 56px;
  background: linear-gradient(135deg, #FF5500, #FF8C4A);
  border-radius: 14px;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-weight: 700;
  font-size: 26px;
  box-shadow: 0 4px 12px rgba(255, 85, 0, 0.25);
}

.login-title {
  margin: 0 0 6px 0;
  font-size: 22px;
  font-weight: 600;
  color: var(--el-text-color-primary);
  letter-spacing: 0.3px;
}

.login-desc {
  margin: 0;
  font-size: 14px;
  color: var(--el-text-color-secondary);
}

/* ---- Form ---- */
.login-form {
  margin-bottom: 24px;
}

.login-form :deep(.el-input__wrapper) {
  padding: 4px 16px;
  border-radius: 10px;
  box-shadow: 0 0 0 1px var(--el-border-color) inset;
  transition: box-shadow 0.2s;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px var(--el-color-primary-light-3) inset;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 2px var(--el-color-primary) inset;
}

.login-form :deep(.el-input__inner) {
  height: 44px;
}

.login-form :deep(.el-input__prefix) {
  margin-right: 8px;
}

.login-form :deep(.el-input__prefix-inner) {
  color: var(--el-text-color-secondary);
}

.login-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  border-radius: 10px;
  margin-top: 4px;
  letter-spacing: 2px;
  transition: transform 0.15s, box-shadow 0.2s;
}
.login-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 16px rgba(255, 85, 0, 0.3);
}
.login-btn:active {
  transform: translateY(0);
}

/* ---- Footer ---- */
.login-footer {
  text-align: center;
  font-size: 12px;
  color: var(--el-text-color-placeholder);
}
</style>
