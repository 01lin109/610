<template>
  <div class="dashboard">
    <div class="page-title">
      <h2>首页仪表盘</h2>
      <span class="page-subtitle">今日运营概况</span>
    </div>

    <el-row :gutter="20" class="stat-cards">
      <el-col :xs="12" :sm="12" :md="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-item">
            <div class="stat-icon stat-icon-primary">
              <GoodsFilled />
            </div>
            <div class="stat-info">
              <div class="stat-value primary">{{ data.totalProducts || 0 }}</div>
              <div class="stat-label">商品总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="12" :sm="12" :md="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-item">
            <div class="stat-icon stat-icon-success">
              <UserFilled />
            </div>
            <div class="stat-info">
              <div class="stat-value success">{{ data.totalMembers || 0 }}</div>
              <div class="stat-label">会员总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="12" :sm="12" :md="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-item">
            <div class="stat-icon stat-icon-warning">
              <Clock />
            </div>
            <div class="stat-info">
              <div class="stat-value warning">{{ data.pendingOrders || 0 }}</div>
              <div class="stat-label">待处理订单</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="12" :sm="12" :md="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-item">
            <div class="stat-icon stat-icon-info">
              <Van />
            </div>
            <div class="stat-info">
              <div class="stat-value info">{{ data.deliveredOrders || 0 }}</div>
              <div class="stat-label">已发货</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="dashboard-row">
      <el-col :xs="24" :md="14">
        <el-card shadow="never" class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>每日销售趋势</span>
              <el-tag size="small" type="info" effect="plain">近7天</el-tag>
            </div>
          </template>
          <div ref="chartRef" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="10">
        <el-card shadow="never" class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>待办事项</span>
              <el-tag size="small" type="danger" effect="light" v-if="pendingCount > 0">{{ pendingCount }} 项</el-tag>
            </div>
          </template>
          <div class="todo-list">
            <div class="todo-item">
              <div class="todo-icon todo-icon-danger">
                <Document />
              </div>
              <div class="todo-content">
                <span class="todo-text">待处理退款申请</span>
                <span class="todo-count">{{ data.pendingRefunds || 0 }} 笔</span>
              </div>
              <el-tag size="small" round :type="(data.pendingRefunds || 0) > 0 ? 'danger' : 'info'">
                {{ (data.pendingRefunds || 0) > 0 ? '待处理' : '无' }}
              </el-tag>
            </div>
            <div class="todo-item">
              <div class="todo-icon todo-icon-warning">
                <WarningFilled />
              </div>
              <div class="todo-content">
                <span class="todo-text">库存预警商品</span>
                <span class="todo-count">{{ data.lowStockProducts || 0 }} 件</span>
              </div>
              <el-tag size="small" round :type="(data.lowStockProducts || 0) > 0 ? 'warning' : 'info'">
                {{ (data.lowStockProducts || 0) > 0 ? '需补货' : '正常' }}
              </el-tag>
            </div>
            <div class="todo-item">
              <div class="todo-icon todo-icon-success">
                <UserFilled />
              </div>
              <div class="todo-content">
                <span class="todo-text">今日新增会员</span>
                <span class="todo-count">{{ data.todayMembers || 0 }} 人</span>
              </div>
              <el-tag size="small" round type="success">{{ data.todayMembers || 0 }} 人</el-tag>
            </div>
            <div class="todo-empty" v-if="pendingCount === 0">
              <el-icon><CircleCheckFilled /></el-icon>
              <span>暂无待办事项</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { getDashboard } from '@/api/dashboard'
import { getSalesOverview, getDailySales } from '@/api/statistics'
import { getPendingRefundCount } from '@/api/finance'
import * as echarts from 'echarts'
import { GoodsFilled, UserFilled, Clock, Van, Document, WarningFilled, CircleCheckFilled } from '@element-plus/icons-vue'

const data = ref({})
const chartRef = ref(null)
let chartInstance = null

const pendingCount = computed(() => {
  return (data.value.pendingRefunds || 0) + (data.value.lowStockProducts || 0)
})

onMounted(async () => {
  try {
    const [overview, daily] = await Promise.all([getSalesOverview(), getDailySales(7)])
    data.value = overview.data || {}
    initChart(daily.data || {})
    try {
      const refundRes = await getPendingRefundCount()
      data.value.pendingRefunds = refundRes.data || 0
    } catch {}
  } catch (e) { console.error(e) }
})

function initChart(rawData) {
  if (!chartRef.value) return
  const keys = Object.keys(rawData)
  const vals = Object.values(rawData)
  chartInstance = echarts.init(chartRef.value)
  chartInstance.setOption({
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(255,255,255,0.95)',
      borderColor: '#E8E8EE',
      borderWidth: 1,
      textStyle: { color: '#1A1A2E', fontSize: 13 }
    },
    grid: { left: 50, right: 20, top: 25, bottom: 30 },
    xAxis: {
      type: 'category',
      data: keys,
      axisLine: { lineStyle: { color: '#E8E8EE' } },
      axisTick: { show: false },
      axisLabel: { color: '#8E8E9E', fontSize: 12 }
    },
    yAxis: {
      type: 'value',
      splitLine: { lineStyle: { color: '#F0F0F5', type: 'dashed' } },
      axisLabel: { color: '#8E8E9E', fontSize: 12 }
    },
    series: [{
      data: vals,
      type: 'line',
      smooth: true,
      symbol: 'circle',
      symbolSize: 6,
      lineStyle: { color: '#FF5500', width: 2.5 },
      itemStyle: { color: '#FF5500' },
      areaStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: 'rgba(255, 85, 0, 0.2)' },
          { offset: 1, color: 'rgba(255, 85, 0, 0.02)' }
        ])
      }
    }]
  })
}

onUnmounted(() => {
  chartInstance?.dispose()
})
</script>

<style scoped>
.dashboard {
  max-width: 1400px;
}

/* ---- Page Title ---- */
.page-title {
  margin-bottom: 20px;
}
.page-title h2 {
  margin: 0;
  font-size: 20px;
  font-weight: 600;
  color: var(--el-text-color-primary);
}
.page-subtitle {
  font-size: 13px;
  color: var(--el-text-color-secondary);
  margin-top: 4px;
  display: block;
}

/* ---- Stat Cards ---- */
.stat-cards {
  margin-bottom: 20px;
}

.stat-card {
  --el-card-border-color: var(--el-border-color-light);
  border-radius: 10px;
  transition: box-shadow 0.2s, transform 0.2s;
  margin-bottom: 16px;
}
.stat-card:hover {
  box-shadow: var(--el-box-shadow);
  transform: translateY(-1px);
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 4px 0;
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 22px;
  flex-shrink: 0;
}
.stat-icon-primary {
  background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
}
.stat-icon-success {
  background: var(--el-color-success-light-9);
  color: var(--el-color-success);
}
.stat-icon-warning {
  background: var(--el-color-warning-light-9);
  color: var(--el-color-warning);
}
.stat-icon-info {
  background: var(--el-color-info-light-9);
  color: var(--el-color-info);
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
  line-height: 1.2;
}
.stat-value.primary { color: var(--el-color-primary); }
.stat-value.success { color: var(--el-color-success); }
.stat-value.warning { color: var(--el-color-warning); }
.stat-value.info { color: var(--el-color-info); }

.stat-label {
  margin-top: 4px;
  color: var(--el-text-color-secondary);
  font-size: 13px;
}

/* ---- Dashboard Row ---- */
.dashboard-row {
  margin-top: 4px;
}

.dashboard-card {
  --el-card-border-color: var(--el-border-color-light);
  border-radius: 10px;
  margin-bottom: 16px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
  font-size: 15px;
  color: var(--el-text-color-primary);
}

.chart-container {
  height: 340px;
  width: 100%;
}

/* ---- Todo List ---- */
.todo-list {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.todo-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 14px 12px;
  border-radius: 8px;
  transition: background 0.15s;
  cursor: default;
}
.todo-item:hover {
  background: var(--el-fill-color-light);
}

.todo-icon {
  width: 36px;
  height: 36px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  flex-shrink: 0;
}
.todo-icon-danger {
  background: var(--el-color-error-light-9);
  color: var(--el-color-error);
}
.todo-icon-warning {
  background: var(--el-color-warning-light-9);
  color: var(--el-color-warning);
}
.todo-icon-success {
  background: var(--el-color-success-light-9);
  color: var(--el-color-success);
}

.todo-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.todo-text {
  font-size: 14px;
  color: var(--el-text-color-primary);
  font-weight: 500;
}

.todo-count {
  font-size: 12px;
  color: var(--el-text-color-secondary);
  margin-top: 2px;
}

.todo-empty {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 24px;
  color: var(--el-text-color-placeholder);
  font-size: 14px;
}
.todo-empty .el-icon {
  font-size: 20px;
}
</style>
