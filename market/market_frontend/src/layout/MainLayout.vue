<template>
  <el-container class="layout-container">
    <el-aside :width="isCollapse ? '64px' : '220px'" class="sidebar">
      <div class="logo" :class="{ collapsed: isCollapse }">
        <span class="logo-icon">M</span>
        <span v-if="!isCollapse" class="logo-text">Market 后台</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        :collapse="isCollapse"
        class="sidebar-menu"
        background-color="#1A1A2E"
        text-color="#A0A0B4"
        active-text-color="#FF5500"
      >
        <el-menu-item index="/dashboard">
          <el-icon><HomeFilled /></el-icon>
          <span>首页仪表盘</span>
        </el-menu-item>
        <el-sub-menu index="/product">
          <template #title>
            <el-icon><GoodsFilled /></el-icon>
            <span>商品管理</span>
          </template>
          <el-menu-item index="/product/list">商品列表</el-menu-item>
          <el-menu-item index="/product/category">分类管理</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/order">
          <el-icon><Document /></el-icon>
          <span>订单管理</span>
        </el-menu-item>
        <el-menu-item index="/member">
          <el-icon><User /></el-icon>
          <span>会员管理</span>
        </el-menu-item>
        <el-sub-menu index="/promotion">
          <template #title>
            <el-icon><Discount /></el-icon>
            <span>促销管理</span>
          </template>
          <el-menu-item index="/promotion/coupon">优惠券</el-menu-item>
          <el-menu-item index="/promotion/activity">活动管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/operation">
          <template #title>
            <el-icon><Monitor /></el-icon>
            <span>运营管理</span>
          </template>
          <el-menu-item index="/operation/banner">轮播图</el-menu-item>
          <el-menu-item index="/operation/ad">广告管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/content">
          <template #title>
            <el-icon><Notebook /></el-icon>
            <span>内容管理</span>
          </template>
          <el-menu-item index="/content/article">文章管理</el-menu-item>
          <el-menu-item index="/content/notice">公告管理</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/statistics">
          <el-icon><TrendCharts /></el-icon>
          <span>统计报表</span>
        </el-menu-item>
        <el-sub-menu index="/finance">
          <template #title>
            <el-icon><Money /></el-icon>
            <span>财务管理</span>
          </template>
          <el-menu-item index="/finance/transaction">交易流水</el-menu-item>
          <el-menu-item index="/finance/refund">退款管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/permission">
          <template #title>
            <el-icon><Lock /></el-icon>
            <span>权限管理</span>
          </template>
          <el-menu-item index="/permission/user">用户管理</el-menu-item>
          <el-menu-item index="/permission/role">角色管理</el-menu-item>
          <el-menu-item index="/permission/menu">菜单管理</el-menu-item>
          <el-menu-item index="/permission/dept">部门管理</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/setting">
          <el-icon><Tools /></el-icon>
          <span>系统设置</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-icon class="collapse-btn" @click="isCollapse = !isCollapse">
            <Fold v-if="!isCollapse" />
            <Expand v-else />
          </el-icon>
          <el-breadcrumb separator="/" class="breadcrumb">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="route.meta.title">{{ route.meta.title }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32" class="user-avatar">
                <UserFilled />
              </el-avatar>
              <span class="username">{{ userStore.userInfo?.nickname || '管理员' }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/store/user'
const route = useRoute()
const userStore = useUserStore()
const isCollapse = ref(false)
const activeMenu = computed(() => route.path)
const handleCommand = cmd => { if (cmd === 'logout') userStore.logout() }
</script>

<style scoped>
/* ---- Layout ---- */
.layout-container {
  height: 100vh;
}

/* ---- Sidebar ---- */
.sidebar {
  background: #1A1A2E;
  overflow-y: auto;
  overflow-x: hidden;
  transition: width 0.25s cubic-bezier(0.4, 0, 0.2, 1);
  border-right: 1px solid rgba(255, 255, 255, 0.04);
  display: flex;
  flex-direction: column;
}

.sidebar::-webkit-scrollbar {
  width: 4px;
}
.sidebar::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 2px;
}

/* ---- Logo ---- */
.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  background: rgba(0, 0, 0, 0.15);
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
  flex-shrink: 0;
  white-space: nowrap;
  overflow: hidden;
}

.logo-icon {
  width: 32px;
  height: 32px;
  background: linear-gradient(135deg, #FF5500, #FF8C4A);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-weight: 700;
  font-size: 16px;
  flex-shrink: 0;
}

.logo-text {
  color: #fff;
  font-size: 17px;
  font-weight: 600;
  letter-spacing: 0.5px;
}

.logo.collapsed {
  padding: 0;
}
.logo.collapsed .logo-icon {
  width: 36px;
  height: 36px;
  border-radius: 8px;
  font-size: 18px;
}

/* ---- Sidebar Menu ---- */
.sidebar-menu {
  border-right: none !important;
  flex: 1;
}

.sidebar-menu .el-menu-item.is-active {
  position: relative;
  background: rgba(255, 85, 0, 0.08) !important;
}
.sidebar-menu .el-menu-item.is-active::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 3px;
  height: 20px;
  background: #FF5500;
  border-radius: 0 2px 2px 0;
}

.sidebar-menu .el-menu-item:hover {
  background: rgba(255, 255, 255, 0.06) !important;
}

.sidebar-menu .el-sub-menu__title:hover {
  background: rgba(255, 255, 255, 0.06) !important;
}

.sidebar-menu .el-menu--inline .el-menu-item {
  background: rgba(0, 0, 0, 0.12) !important;
  padding-left: 56px !important;
}
.sidebar-menu .el-menu--inline .el-menu-item:hover {
  background: rgba(255, 255, 255, 0.04) !important;
}
.sidebar-menu .el-menu--inline .el-menu-item.is-active {
  background: rgba(255, 85, 0, 0.06) !important;
}

/* ---- Header ---- */
.header {
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  height: 60px;
  border-bottom: 1px solid var(--el-border-color-light);
  flex-shrink: 0;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.collapse-btn {
  font-size: 18px;
  cursor: pointer;
  color: var(--el-text-color-secondary);
  transition: color 0.2s;
  padding: 4px;
  border-radius: 4px;
}
.collapse-btn:hover {
  color: #FF5500;
  background: rgba(255, 85, 0, 0.06);
}

.breadcrumb {
  font-size: 14px;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  padding: 4px 12px;
  border-radius: 6px;
  transition: background 0.2s;
}
.user-info:hover {
  background: var(--el-fill-color-light);
}

.user-avatar {
  background: linear-gradient(135deg, #FF5500, #FF8C4A) !important;
  --el-avatar-bg-color: transparent;
}

.username {
  font-size: 14px;
  color: var(--el-text-color-primary);
  font-weight: 500;
}

/* ---- Main Content ---- */
.main-content {
  background: var(--el-bg-color-page);
  padding: 20px;
  min-height: calc(100vh - 60px);
  overflow-y: auto;
}
</style>
