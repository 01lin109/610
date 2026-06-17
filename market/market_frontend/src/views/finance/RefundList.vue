<template>
  <div class="page-container">
    <el-card>
      <template #header><span>退款管理</span></template>
      <el-form :inline="true" :model="query" class="search-form">
        <el-form-item label="退款单号"><el-input v-model="query.refundNo" placeholder="退款单号" clearable /></el-form-item>
        <el-form-item label="状态"><el-select v-model="query.status" placeholder="全部" clearable><el-option label="待处理" :value="0" /><el-option label="已通过" :value="1" /><el-option label="已驳回" :value="2" /><el-option label="已完成" :value="3" /></el-select></el-form-item>
        <el-form-item><el-button type="primary" @click="fetchData">搜索</el-button></el-form-item>
      </el-form>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="refundNo" label="退款单号" width="180" />
        <el-table-column prop="orderNo" label="订单号" width="180" />
        <el-table-column prop="memberName" label="会员" width="100" />
        <el-table-column prop="refundAmount" label="退款金额" width="100"><template #default="{row}">¥{{ row.refundAmount }}</template></el-table-column>
        <el-table-column prop="reason" label="退款原因" min-width="150" />
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}"><el-tag :type="row.status===0?'warning':row.status===1?'success':row.status===2?'danger':'info'">{{ row.status===0?'待处理':row.status===1?'已通过':row.status===2?'已驳回':'已完成' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{row}">
            <el-button v-if="row.status===0" size="small" type="success" @click="handleApprove(row)">通过</el-button>
            <el-button v-if="row.status===0" size="small" type="danger" @click="handleReject(row)">驳回</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getRefundList, approveRefund, rejectRefund } from '@/api/finance'
import { ElMessage } from 'element-plus'
const loading = ref(false), tableData = ref([])
const query = reactive({ refundNo: '', status: null })
const fetchData = async () => { loading.value = true; try { const res = await getRefundList(query); tableData.value = res.data.records || res.data || [] } catch {} loading.value = false }
const handleApprove = async row => { try { await approveRefund(row.id, { note: '审核通过' }); ElMessage.success('已通过'); fetchData() } catch (e) { ElMessage.error('操作失败') } }
const handleReject = async row => { try { await rejectRefund(row.id, { note: '审核驳回' }); ElMessage.success('已驳回'); fetchData() } catch (e) { ElMessage.error('操作失败') } }
onMounted(fetchData)
</script>
<style scoped>.page-container { padding: 0; } .search-form { margin-bottom: 16px; }</style>