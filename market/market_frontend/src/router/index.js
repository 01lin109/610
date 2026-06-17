import { createRouter, createWebHashHistory } from 'vue-router'
import { useUserStore } from '@/store/user'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

const routes = [
  { path: '/login', name: 'Login', component: () => import('@/views/Login.vue'), meta: { noAuth: true } },
  {
    path: '/', component: () => import('@/layout/MainLayout.vue'), redirect: '/dashboard',
    children: [
      { path: '/dashboard', name: 'Dashboard', component: () => import('@/views/Dashboard.vue'), meta: { title: '首页仪表盘', icon: 'HomeFilled' } },
      { path: '/product/list', name: 'ProductList', component: () => import('@/views/product/ProductList.vue'), meta: { title: '商品列表', icon: 'GoodsFilled' } },
      { path: '/product/category', name: 'ProductCategory', component: () => import('@/views/product/CategoryList.vue'), meta: { title: '分类管理', icon: 'Menu' } },
      { path: '/order', name: 'OrderList', component: () => import('@/views/order/OrderList.vue'), meta: { title: '订单管理', icon: 'Document' } },
      { path: '/member', name: 'MemberList', component: () => import('@/views/member/MemberList.vue'), meta: { title: '会员管理', icon: 'User' } },
      { path: '/promotion/coupon', name: 'CouponList', component: () => import('@/views/promotion/CouponList.vue'), meta: { title: '优惠券管理', icon: 'Ticket' } },
      { path: '/promotion/activity', name: 'PromotionList', component: () => import('@/views/promotion/PromotionList.vue'), meta: { title: '活动管理', icon: 'Gift' } },
      { path: '/operation/banner', name: 'BannerList', component: () => import('@/views/operation/BannerList.vue'), meta: { title: '轮播图', icon: 'Picture' } },
      { path: '/operation/ad', name: 'AdList', component: () => import('@/views/operation/AdList.vue'), meta: { title: '广告管理', icon: 'Postcard' } },
      { path: '/content/article', name: 'ArticleList', component: () => import('@/views/content/ArticleList.vue'), meta: { title: '文章管理', icon: 'Edit' } },
      { path: '/content/notice', name: 'NoticeList', component: () => import('@/views/content/NoticeList.vue'), meta: { title: '公告管理', icon: 'Bell' } },
      { path: '/statistics', name: 'Statistics', component: () => import('@/views/statistics/Statistics.vue'), meta: { title: '统计报表', icon: 'TrendCharts' } },
      { path: '/finance/transaction', name: 'TransactionList', component: () => import('@/views/finance/TransactionList.vue'), meta: { title: '交易流水', icon: 'CreditCard' } },
      { path: '/finance/refund', name: 'RefundList', component: () => import('@/views/finance/RefundList.vue'), meta: { title: '退款管理', icon: 'Wallet' } },
      { path: '/permission/user', name: 'SysUserList', component: () => import('@/views/permission/UserList.vue'), meta: { title: '用户管理', icon: 'User' } },
      { path: '/permission/role', name: 'SysRoleList', component: () => import('@/views/permission/RoleList.vue'), meta: { title: '角色管理', icon: 'UserFilled' } },
      { path: '/permission/menu', name: 'SysMenuList', component: () => import('@/views/permission/MenuList.vue'), meta: { title: '菜单管理', icon: 'Menu' } },
      { path: '/permission/dept', name: 'SysDeptList', component: () => import('@/views/permission/DeptList.vue'), meta: { title: '部门管理', icon: 'OfficeBuilding' } },
      { path: '/setting', name: 'Setting', component: () => import('@/views/setting/Setting.vue'), meta: { title: '系统设置', icon: 'Tools' } }
    ]
  }
]

const router = createRouter({ history: createWebHashHistory(), routes })

router.beforeEach((to, from, next) => {
  NProgress.start()
  const userStore = useUserStore()
  if (to.meta.noAuth) { next(); return }
  if (!userStore.token) { next('/login'); return }
  next()
})
router.afterEach(() => NProgress.done())

export default router
