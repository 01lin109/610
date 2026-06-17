<template>
  <div class="page-container">
    <el-card>
      <template #header><span>会员管理</span></template>
      <el-form :inline="true" :model="query" class="search-form">
        <el-form-item label="会员名"><el-input v-model="query.keyword" placeholder="用户名/昵称" clearable /></el-form-item>
        <el-form-item label="状态"><el-select v-model="query.status" placeholder="全部" clearable><el-option label="正常" :value="1" /><el-option label="禁用" :value="0" /></el-select></el-form-item>
        <el-form-item><el-button type="primary" @click="search">搜索</el-button><el-button @click="reset">重置</el-button></el-form-item>
      </el-form>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="nickname" label="昵称" width="120" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="levelName" label="等级" width="100"><template #default="{row}"><el-tag>{{ row.levelName || '普通会员' }}</el-tag></template></el-table-column>
        <el-table-column prop="points" label="积分" width="80" />
        <el-table-column prop="balance" label="余额" width="100"><template #default="{row}">¥{{ row.balance }}</template></el-table-column>
        <el-table-column prop="totalConsume" label="累计消费" width="120"><template #default="{row}">¥{{ row.totalConsume }}</template></el-table-column>
        <el-table-column prop="status" label="状态" width="80"><template #default="{row}"><el-tag :type="row.status===1?'success':'danger'">{{ row.status===1?'正常':'禁用' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{row}">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" :type="row.status===1?'danger':'success'" @click="toggleStatus(row)">{{ row.status===1?'禁用':'启用' }}</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:20px; justify-content:flex-end" background layout="total, prev, pager, next" :total="total" v-model:current-page="query.page" @current-change="fetchData" />
    </el-card>
    <el-dialog title="编辑会员" v-model="dialogVisible" width="500px" @close="resetForm">
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="昵称"><el-input v-model="form.nickname" /></el-form-item>
        <el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item>
        <el-form-item label="积分"><el-input-number v-model="form.points" :min="0" /></el-form-item>
        <el-form-item label="余额"><el-input-number v-model="form.balance" :min="0" :precision="2" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getMemberList, getMember, updateMember, updateMemberStatus } from '@/api/member'
import { ElMessage } from 'element-plus'
const loading = ref(false), tableData = ref([]), total = ref(0), dialogVisible = ref(false), formRef = ref(null)
const query = reactive({ page: 1, keyword: '', status: null })
const form = reactive({ id: null, nickname: '', phone: '', email: '', points: 0, balance: 0 })

const fetchData = async () => {
  loading.value = true
  try { const res = await getMemberList(query); tableData.value = res.data.records || res.data || []; total.value = res.data.total || tableData.value.length } catch {}
  loading.value = false
}
const search = () => { query.page = 1; fetchData() }
const reset = () => { query.keyword = ''; query.status = null; search() }
const handleEdit = async row => { Object.assign(form, row); dialogVisible.value = true }
const toggleStatus = async row => {
  try {
    const ns = row.status === 1 ? 0 : 1
    await updateMemberStatus(row.id, ns); ElMessage.success('状态已更新'); fetchData()
  } catch (e) { ElMessage.error('操作失败') }
}
const resetForm = () => { formRef.value?.resetFields(); Object.assign(form, { id: null, nickname: '', phone: '', email: '', points: 0, balance: 0 }) }
const submitForm = async () => {
  try {
    await updateMember(form)
    ElMessage.success('更新成功'); dialogVisible.value = false; fetchData()
  } catch (e) { ElMessage.error('更新失败') }
}
onMounted(fetchData)
</script>
<style scoped>.page-container { padding: 0; } .search-form { margin-bottom: 16px; }</style>