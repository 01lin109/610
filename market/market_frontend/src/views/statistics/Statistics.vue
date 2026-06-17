<template>
  <div class="page-container">
    <div class="page-title">
      <h2>统计报表</h2>
      <span class="page-subtitle">销售数据概览</span>
    </div>
    <el-row :gutter="20" class="stat-cards">
      <el-col :xs="12" :sm="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-item">
            <div class="stat-icon stat-icon-primary">
              <GoodsFilled />
            </div>
            <div class="stat-info">
              <div class="stat-value primary">{{ overview.totalProducts || 0 }}</div>
              <div class="stat-label">商品总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="12" :sm="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-item">
            <div class="stat-icon stat-icon-success">
              <UserFilled />
            </div>
            <div class="stat-info">
              <div class="stat-value success">{{ overview.totalMembers || 0 }}</div>
              <div class="stat-label">会员总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="12" :sm="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-item">
            <div class="stat-icon stat-icon-warning">
              <Clock />
            </div>
            <div class="stat-info">
              <div class="stat-value warning">{{ overview.pendingOrders || 0 }}</div>
              <div class="stat-label">待处理订单</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="12" :sm="6">
        <el-card shadow="never" class="stat-card">
          <div class="stat-item">
            <div class="stat-icon stat-icon-info">
              <Van />
            </div>
            <div class="stat-info">
              <div class="stat-value info">{{ overview.deliveredOrders || 0 }}</div>
              <div class="stat-label">已发货</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="chart-row">
      <el-col :xs="24" :md="12">
        <el-card shadow="never" class="chart-card">
          <template #header>
            <div class="card-header">
              <span>销售趋势</span>
              <el-tag size="small" type="info" effect="plain">近7天</el-tag>
            </div>
          </template>
          <div ref="salesChart" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="12">
        <el-card shadow="never" class="chart-card">
          <template #header>
            <div class="card-header">
              <span>商品销量排行 Top10</span>
            </div>
          </template>
          <el-table :data="rankData" class="rank-table">
            <el-table-column type="index" label="排名" width="60" />
            <el-table-column prop="productName" label="商品" min-width="160" />
            <el-table-column prop="sales" label="销量" width="100" sortable />
          </el-table>
          <div v-if="!rankData.length" class="table-empty">
            <el-icon><WarningFilled /></el-icon>
            <span>暂无数据</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getSalesOverview, getDailySales, getProductRank } from '@/api/statistics'
import * as echarts from 'echarts'
import { GoodsFilled, UserFilled, Clock, Van, WarningFilled } from '@element-plus/icons-vue'

const overview = ref({})
const rankData = ref([])
const salesChart = ref(null)

onMounted(async () => {
  try {
    const [ov, daily, rank] = await Promise.all([getSalesOverview(), getDailySales(7), getProductRank(10)])
    overview.value = ov.data || {}
    rankData.value = rank.data || []
    const chart = echarts.init(salesChart.value)
    const keys = Object.keys(daily.data || {})
    const vals = Object.values(daily.data || {})
    chart.setOption({
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
  } catch (e) { console.error(e) }
})
</script>

<style scoped>
.page-container {
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

/* ---- Chart Row ---- */
.chart-row {
  margin-top: 4px;
}

.chart-card {
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
  height: 300px;
  width: 100%;
}

.rank-table {
  --el-table-border-color: transparent;
}

.table-empty {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 32px;
  color: var(--el-text-color-placeholder);
  font-size: 14px;
}
</style>
