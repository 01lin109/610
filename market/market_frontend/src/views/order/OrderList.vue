<template>
  <div class="page-container">
    <el-card>
      <template #header><span>订单管理</span></template>
      <el-form :inline="true" :model="query" class="search-form">
        <el-form-item label="订单号"><el-input v-model="query.orderNo" placeholder="订单号" clearable /></el-form-item>
        <el-form-item label="状态"><el-select v-model="query.status" placeholder="全部" clearable><el-option label="待付款" :value="0" /><el-option label="已支付" :value="1" /><el-option label="已发货" :value="2" /><el-option label="已完成" :value="3" /><el-option label="已关闭" :value="5" /></el-select></el-form-item>
        <el-form-item><el-button type="primary" @click="search">搜索</el-button><el-button @click="reset">重置</el-button></el-form-item>
      </el-form>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="orderNo" label="订单号" width="180" />
        <el-table-column prop="memberName" label="会员" width="100" />
        <el-table-column prop="totalAmount" label="总金额" width="100"><template #default="{row}">¥{{ row.totalAmount }}</template></el-table-column>
        <el-table-column prop="payAmount" label="实付" width="100"><template #default="{row}">¥{{ row.payAmount }}</template></el-table-column>
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}">
          <el-tag v-if="row.status===0" type="info">待付款</el-tag>
          <el-tag v-else-if="row.status===1" type="warning">已支付</el-tag>
          <el-tag v-else-if="row.status===2" type="primary">已发货</el-tag>
          <el-tag v-else-if="row.status===3" type="success">已完成</el-tag>
          <el-tag v-else type="danger">已关闭</el-tag>
        </template></el-table-column>
        <el-table-column prop="receiverName" label="收货人" width="100" />
        <el-table-column prop="createTime" label="创建时间" width="170" />
        <el-table-column label="操作" width="200">
          <template #default="{row}">
            <el-button size="small" type="primary" @click="handleDetail(row)">详情</el-button>
            <el-button v-if="row.status===1" size="small" type="success" @click="updateStatus(row,2)">发货</el-button>
            <el-button v-if="row.status===0" size="small" type="danger" @click="updateStatus(row,5)">关闭</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:20px; justify-content:flex-end" background layout="total, prev, pager, next" :total="total" v-model:current-page="query.page" @current-change="fetchData" />
    </el-card>
    <el-dialog title="订单详情" v-model="detailVisible" width="700px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="订单号">{{ detail.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="会员">{{ detail.memberName }}</el-descriptions-item>
        <el-descriptions-item label="总金额">¥{{ detail.totalAmount }}</el-descriptions-item>
        <el-descriptions-item label="实付金额">¥{{ detail.payAmount }}</el-descriptions-item>
        <el-descriptions-item label="收货人">{{ detail.receiverName }}</el-descriptions-item>
        <el-descriptions-item label="电话">{{ detail.receiverPhone }}</el-descriptions-item>
        <el-descriptions-item label="地址" :span="2">{{ detail.receiverAddress }}</el-descriptions-item>
      </el-descriptions>
      <el-table :data="detail.items || []" style="margin-top:16px" border><el-table-column prop="productName" label="商品" /><el-table-column prop="productPrice" label="单价" /><el-table-column prop="quantity" label="数量" /><el-table-column prop="totalAmount" label="小计" /></el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getOrderList, getOrder, updateOrderStatus } from '@/api/order'
import { ElMessage } from 'element-plus'
const loading = ref(false), tableData = ref([]), total = ref(0), detailVisible = ref(false), detail = ref({})
const query = reactive({ page: 1, orderNo: '', status: null })
const fetchData = async () => {
  loading.value = true
  try { const res = await getOrderList(query); tableData.value = res.data.records || res.data || []; total.value = res.data.total || tableData.value.length } catch {}
  loading.value = false
}
const search = () => { query.page = 1; fetchData() }
const reset = () => { query.orderNo = ''; query.status = null; search() }
const handleDetail = async row => {
  try { const res = await getOrder(row.id); detail.value = res.data || row } catch { detail.value = row }
  detailVisible.value = true
}
const updateStatus = async (row, status) => {
  try {
    await updateOrderStatus(row.id, status)
    ElMessage.success('操作成功'); fetchData()
  } catch (e) { ElMessage.error('操作失败') }
}
onMounted(fetchData)
</script>
<style scoped>.page-container { padding: 0; } .search-form { margin-bottom: 16px; }</style>