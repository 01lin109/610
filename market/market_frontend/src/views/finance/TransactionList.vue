<template>
  <div class="page-container">
    <el-card>
      <template #header><span>交易流水</span></template>
      <el-form :inline="true" :model="query" class="search-form">
        <el-form-item label="流水号"><el-input v-model="query.transactionNo" placeholder="流水号" clearable /></el-form-item>
        <el-form-item label="类型"><el-select v-model="query.transactionType" placeholder="全部" clearable><el-option label="支付" :value="1" /><el-option label="退款" :value="2" /><el-option label="充值" :value="3" /></el-select></el-form-item>
        <el-form-item><el-button type="primary" @click="fetchData">搜索</el-button></el-form-item>
      </el-form>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="transactionNo" label="流水号" width="180" />
        <el-table-column prop="orderNo" label="订单号" width="180" />
        <el-table-column prop="memberName" label="会员" width="100" />
        <el-table-column prop="amount" label="金额" width="100"><template #default="{row}">¥{{ row.amount }}</template></el-table-column>
        <el-table-column prop="transactionType" label="类型" width="80"><template #default="{row}"><el-tag>{{ row.transactionType===1?'支付':row.transactionType===2?'退款':'充值' }}</el-tag></template></el-table-column>
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}"><el-tag :type="row.status===1?'success':'danger'">{{ row.status===1?'成功':'失败' }}</el-tag></template></el-table-column>
        <el-table-column prop="transactionTime" label="交易时间" width="170" />
      </el-table>
    </el-card>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getTransactionList } from '@/api/finance'
const loading = ref(false), tableData = ref([])
const query = reactive({ transactionNo: '', transactionType: null })
const fetchData = async () => { loading.value = true; try { const res = await getTransactionList(query); tableData.value = res.data.records || res.data || [] } catch {} loading.value = false }
onMounted(fetchData)
</script>
<style scoped>.page-container { padding: 0; } .search-form { margin-bottom: 16px; }</style>